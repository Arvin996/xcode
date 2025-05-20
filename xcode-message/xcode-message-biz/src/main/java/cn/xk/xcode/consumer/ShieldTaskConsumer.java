package cn.xk.xcode.consumer;

import cn.xk.xcode.config.RabbitMqConfiguration;
import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.entity.po.MessageTaskPo;
import cn.xk.xcode.enums.MessageTaskStatusEnum;
import cn.xk.xcode.handler.MessageHandlerHolder;
import cn.xk.xcode.handler.message.response.SendMessageResponse;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.MessageTaskService;
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

/**
 * @Author xuk
 * @Date 2025/3/14 10:45
 * @Version 1.0.0
 * @Description ShieldTaskConsumer
 **/
@Component
@Slf4j
public class ShieldTaskConsumer{

    @Resource
    private MessageHandlerHolder messageHandlerHolder;

    @Resource
    private MessageTaskService messageTaskService;

    @RabbitListener(queues = RabbitMqConfiguration.SHIELD_MESSAGE_QUEUE_NAME)
    public void consumeShieldMessage(Message message, Channel channel) throws IOException {
        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("receive shield message:{}", body);
        MessageTask messageTask = JSON.parseObject(body, MessageTask.class);
        MessageTaskPo messageTaskPo = messageTaskService.getById(messageTask.getId());
        if (ObjectUtil.isNull(messageTaskPo)){
            log.info("消息任务不存在，任务id: {}", messageTask.getId());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }
        if (MessageTaskStatusEnum.CANCEL.getStatus().equals(messageTaskPo.getStatus())){
            log.info("延时任务已被取消，任务id: {}", messageTaskPo.getId());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }
        CommonResult<SendMessageResponse> commonResult = messageHandlerHolder.routeHandler(messageTask.getMsgChannel()).sendMessage(messageTask);
        log.info("屏蔽消息返回结果: {}", commonResult);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
