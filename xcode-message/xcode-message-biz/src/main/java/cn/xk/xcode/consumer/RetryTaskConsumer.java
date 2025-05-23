package cn.xk.xcode.consumer;

import cn.xk.xcode.config.RabbitMqConfiguration;
import cn.xk.xcode.config.XcodeMessageProperties;
import cn.xk.xcode.entity.po.MessageChannelPo;
import cn.xk.xcode.entity.po.MessageTaskDetailPo;
import cn.xk.xcode.entity.po.MessageTaskPo;
import cn.xk.xcode.enums.MessageTaskStatusEnum;
import cn.xk.xcode.handler.MessageHandlerHolder;
import cn.xk.xcode.handler.message.response.SendMessageResponse;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.MessageChannelService;
import cn.xk.xcode.service.MessageTaskDetailService;
import cn.xk.xcode.service.MessageTaskService;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author xuk
 * @Date 2025/5/20 8:40
 * @Version 1.0.0
 * @Description RetryTaskConsumer
 **/
@Component
@Slf4j
public class RetryTaskConsumer {

    @Resource
    private MessageTaskService messageTaskService;

    @Resource
    private MessageHandlerHolder messageHandlerHolder;

    @Resource
    private XcodeMessageProperties xcodeMessageProperties;

    @Resource
    private MessageChannelService messageChannelService;

    @Resource
    private MessageTaskDetailService messageTaskDetailService;

    @RabbitListener(queues = RabbitMqConfiguration.RETRY_MESSAGE_QUEUE_NAME)
    public void consumeRetryMessage(Message message, Channel channel) throws IOException {
        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        List<MessageTaskDetailPo> list = JSON.parseArray(body, MessageTaskDetailPo.class);
        log.info("收到 retry message:{}", body);
        if (CollectionUtil.isEmpty(list)) {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }
        List<MessageTaskDetailPo> list1 = list.stream().filter(messageTaskDetailPo -> messageTaskDetailPo.getRetryTimes() < xcodeMessageProperties.getFailRetryTimes()).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(list1)) {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }
        MessageTaskPo messageTaskPo = messageTaskService.getById(list1.get(0).getTaskId());
        if (ObjectUtil.isNull(messageTaskPo)) {
            log.info("消息任务不存在，任务id: {}", list.get(0).getTaskId());
            for (MessageTaskDetailPo messageTaskDetailPo : list) {
                messageTaskDetailPo.setRetryTimes(messageTaskDetailPo.getRetryTimes() + 1);
                messageTaskDetailPo.setStatus("1");
                messageTaskDetailPo.setFailMsg("消息任务不存在");
                messageTaskDetailPo.setExecTime(LocalDateTime.now());
            }
            messageTaskDetailService.updateBatch(list);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }
        if (MessageTaskStatusEnum.CANCEL.getStatus().equals(messageTaskPo.getStatus())) {
            log.info("消息任务已被取消，任务id: {}", messageTaskPo.getId());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }
        MessageChannelPo messageChannelPo = messageChannelService.getById(messageTaskPo.getChannelId());
        if (ObjectUtil.isNull(messageChannelPo)) {
            log.info("消息渠道不存在，渠道id: {}", messageTaskPo.getChannelId());
            for (MessageTaskDetailPo messageTaskDetailPo : list) {
                messageTaskDetailPo.setRetryTimes(messageTaskDetailPo.getRetryTimes() + 1);
                messageTaskDetailPo.setStatus("1");
                messageTaskDetailPo.setFailMsg("消息渠道不存在");
                messageTaskDetailPo.setExecTime(LocalDateTime.now());
            }
            messageTaskDetailService.updateBatch(list);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }
        CommonResult<SendMessageResponse> commonResult = messageHandlerHolder.routeHandler(messageChannelPo.getCode()).retrySendMessage(list);
        log.info("重试消息返回结果: {}", commonResult);
    }

}
