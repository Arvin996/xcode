package cn.xk.xcode.controller.api;

import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.MessageTaskService;
import cn.xk.xcode.service.message.SendMessageServiceHolder;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2025/5/19 9:23
 * @Version 1.0.0
 * @Description SendMessageController
 **/
@RestController
@RequestMapping("/api/message")
public class SendMessageController {

    @Resource
    private SendMessageServiceHolder sendMessageServiceHolder;

    @Resource
    private MessageTaskService messageTaskService;

    @Operation(summary = "发送消息")
    @PostMapping("/sendMessage")
    public CommonResult<?> sendMessage(@Validated @RequestBody MessageTask messageTask) {
        return sendMessageServiceHolder.routeSendMessageService(messageTask.getSendType()).sendMessage(messageTask);
    }

    @Operation(summary = "取消消息发送 仅针对延时消息")
    @GetMapping("/cancelMessage/{taskId}")
    public CommonResult<Boolean> cancelMessage(@PathVariable("taskId") Integer taskId) {
        return CommonResult.success(messageTaskService.cancelMessage(taskId));
    }

    @Operation(summary = "暂停消息发送 仅针对定时消息")
    @GetMapping("/pauseMessage/{taskId}")
    public CommonResult<Boolean> pauseMessage(@PathVariable("taskId") Integer taskId) {
        return CommonResult.success(messageTaskService.pauseMessage(taskId));
    }

    @Operation(summary = "恢复消息发送 仅针对定时以及延时消息")
    @GetMapping("/resumeMessage/{taskId}")
    public CommonResult<Boolean> resumeMessage(@PathVariable("taskId") Integer taskId) {
        return CommonResult.success(messageTaskService.resumeMessage(taskId));
    }

}
