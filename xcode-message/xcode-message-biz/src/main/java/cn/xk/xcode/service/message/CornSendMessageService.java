package cn.xk.xcode.service.message;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.entity.po.MessageTaskPo;
import cn.xk.xcode.enums.MessageSendType;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.*;
import cn.xk.xcode.service.task.RegisterCornTaskService;
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

    private final RegisterCornTaskService registerCornTaskService;
    private final MessageTaskService messageTaskService;

    public CornSendMessageService(RegisterCornTaskService registerCornTaskService,
                                  MessageTaskService messageTaskService) {
        this.registerCornTaskService = registerCornTaskService;
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
            return CommonResult.error(CORN_MESSAGE_TASK_NOT_DEFINE_CORN, null);
        }
        // 验证corn表达式的合法性
        if (!CronExpression.isValidExpression(corn)) {
            return CommonResult.error(CORN_MESSAGE_TASK_EXPRESSION_INVALID, corn);
        }
        MessageTaskPo messageTaskPo = messageTaskService.getById(messageTask.getId());
        try {
            return registerCornTaskService.registerReceiversCronTask(messageTaskPo);
        } catch (NoSuchMethodException e) {
            return CommonResult.error(CORN_TASK_SUBMIT_FAILED, e.getMessage());
        }
    }

}
