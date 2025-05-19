package cn.xk.xcode.service.message;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.entity.po.MessageChannelPo;
import cn.xk.xcode.entity.po.MessageTaskPo;
import cn.xk.xcode.enums.MessageSendType;
import cn.xk.xcode.handler.MessageHandlerHolder;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.*;
import cn.xk.xcode.service.task.RegisterCsvCornTaskService;
import cn.xk.xcode.utils.object.BeanUtil;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;

import static cn.xk.xcode.config.GlobalMessageConstants.*;

/**
 * @Author xuk
 * @Date 2025/5/19 9:55
 * @Version 1.0.0
 * @Description CornSendMessageService
 **/
@Service
public class CornSendMessageService extends AbstractSendMessageService {

    private final RegisterCsvCornTaskService registerCsvCornTaskService;
    private final MessageTaskService messageTaskService;

    public CornSendMessageService(MessageHandlerHolder messageHandlerHolder, SensitiveWordBs sensitiveWordBs, RabbitTemplate rabbitTemplate, MessageTemplateService messageTemplateService,
                                  MessageTemplateParamsService messageTemplateParamsService,
                                  MessageChannelService messageChannelService,
                                  RegisterCsvCornTaskService registerCsvCornTaskService,
                                  MessageTaskService messageTaskService,
                                  MessageChannelAccessClientService messageChannelAccessClientService,
                                  MessageChannelAccountService messageChannelAccountService) {
        super(messageHandlerHolder, sensitiveWordBs, rabbitTemplate, messageTemplateService, messageTemplateParamsService, messageChannelService, messageChannelAccessClientService, messageChannelAccountService);
        this.registerCsvCornTaskService = registerCsvCornTaskService;
        this.messageTaskService = messageTaskService;
    }

    @Override
    public String sendType() {
        return MessageSendType.CORN.getCode();
    }

    @Override
    public CommonResult<?> dealMessage(MessageTask messageTask) {
        String corn = messageTask.getTaskCorn();
        if (StrUtil.isBlank(corn)) {
            return CommonResult.error(CORN_MESSAGE_TASK_NOT_DEFINE_CORN);
        }
        // 验证corn表达式的合法性
        if (!CronExpression.isValidExpression(corn)) {
            return CommonResult.error(CORN_MESSAGE_TASK_EXPRESSION_INVALID, corn);
        }
        MessageTaskPo messageTaskPo = BeanUtil.toBean(messageTask, MessageTaskPo.class);
        messageTaskService.save(messageTaskPo);
        try {
            return registerCsvCornTaskService.registerReceiversCronTask(messageTaskPo);
        } catch (NoSuchMethodException e) {
            return CommonResult.error(CORN_TASK_SUBMIT_FAILED, e.getMessage());
        }
    }

}
