package cn.xk.xcode.controller;

import cn.xk.xcode.service.MessageTaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
}
