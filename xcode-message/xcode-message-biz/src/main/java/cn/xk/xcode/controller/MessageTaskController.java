package cn.xk.xcode.controller;

import cn.xk.xcode.entity.dto.task.QueryMessageTaskDetailDto;
import cn.xk.xcode.entity.dto.task.QueryMessageTaskDto;
import cn.xk.xcode.entity.vo.task.MessageTaskDetailVo;
import cn.xk.xcode.entity.vo.task.MessageTaskVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.MessageTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2025/5/16 16:47
 * @Version 1.0.0
 * @Description MessageTaskController
 **/
@RestController
@RequestMapping("/manage/task")
@Tag(name = "消息任务接口")
public class MessageTaskController {

    @Resource
    private MessageTaskService messageTaskService;

    @PostMapping("/queryMessageTasks")
    @Operation(summary = "查询消息任务")
    public CommonResult<PageResult<MessageTaskVo>> queryMessageTasks(@RequestBody QueryMessageTaskDto queryMessageTaskDto) {
        return CommonResult.success(messageTaskService.queryMessageTasks(queryMessageTaskDto));
    }

    @PostMapping("/queryMessageTaskDetail")
    @Operation(summary = "查询消息任务详情")
    public CommonResult<PageResult<MessageTaskDetailVo>> queryMessageTaskDetail(@Validated @RequestBody QueryMessageTaskDetailDto queryMessageTaskDetailDto) {
        return CommonResult.success(messageTaskService.queryMessageTaskDetail(queryMessageTaskDetailDto));
    }
}
