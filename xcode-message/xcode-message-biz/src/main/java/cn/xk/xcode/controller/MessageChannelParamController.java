package cn.xk.xcode.controller;

import cn.xk.xcode.entity.dto.param.AddMessageChannelParamDto;
import cn.xk.xcode.entity.dto.param.UpdateMessageChannelParamDto;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.MessageChannelParamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2025/5/16 10:19
 * @Version 1.0.0
 * @Description MessageChannelParamController
 **/
@RestController
@RequestMapping("/manage/param")
@Tag(name = "消息渠道参数接口")
@Validated
public class MessageChannelParamController {

    @Resource
    private MessageChannelParamService messageChannelParamService;

    @PostMapping("/addMessageChannelParam")
    @Operation(summary = "新增渠道参数")
    public CommonResult<Boolean> addMessageChannelParam(@RequestBody AddMessageChannelParamDto addMessageChannelParamDto) {
        return CommonResult.success(messageChannelParamService.addMessageChannelParam(addMessageChannelParamDto));
    }

    @DeleteMapping("/delMessageChannelParam/{id}")
    @Operation(summary = "删除渠道参数")
    public CommonResult<Boolean> delMessageChannelParam(@PathVariable("id") Integer id) {
        return CommonResult.success(messageChannelParamService.delMessageChannelParam(id));
    }

    @PostMapping("/updateMessageChannelParam")
    @Operation(summary = "修改渠道参数")
    public CommonResult<Boolean> updateMessageChannelParam(@RequestBody UpdateMessageChannelParamDto updateMessageChannelParamDto) {
        return CommonResult.success(messageChannelParamService.updateMessageChannelParam(updateMessageChannelParamDto));
    }

}
