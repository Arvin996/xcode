package cn.xk.xcode.controller;

import cn.xk.xcode.entity.dto.channel.AddMessageChannelDto;
import cn.xk.xcode.entity.dto.channel.QueryMessageChannelDto;
import cn.xk.xcode.entity.dto.channel.UpdateMessageChannelDto;
import cn.xk.xcode.entity.vo.channel.MessageChannelVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.MessageChannelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2025/3/10 9:14
 * @Version 1.0.0
 * @Description MessageChannelController
 **/
@RestController
@RequestMapping("/manage/message/channel")
@Tag(name = "消息渠道接口")
public class MessageChannelController {

    @Resource
    private MessageChannelService messageChannelService;

    @Operation(summary = "新增渠道")
    @PostMapping("/add")
    public CommonResult<Boolean> addMessageChannel(@Validated @RequestBody AddMessageChannelDto addMessageChannelDto) {
        return CommonResult.success(messageChannelService.addMessageChannel(addMessageChannelDto));
    }

    @Operation(summary = "删除渠道")
    @DeleteMapping("/del/{id}")
    public CommonResult<Boolean> delMessageChannel(@PathVariable("id") Integer id) {
        return CommonResult.success(messageChannelService.deleteChannel(id));
    }

    @Operation(summary = "修改渠道")
    @PostMapping("/update")
    public CommonResult<Boolean> updateMessageChannel(@Validated @RequestBody UpdateMessageChannelDto updateMessageChannelDto) {
        return CommonResult.success(messageChannelService.updateMessageChannel(updateMessageChannelDto));
    }

    @Operation(summary = "获取渠道")
    @PostMapping("/list")
    public CommonResult<PageResult<MessageChannelVo>> getMessageChannel(@RequestBody QueryMessageChannelDto queryMessageChannelDto) {
        return CommonResult.success(messageChannelService.getMessageChannel(queryMessageChannelDto));
    }

}
