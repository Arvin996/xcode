package cn.xk.xcode.controller.backend;

import cn.xk.xcode.entity.dto.channel.AddMessageChannelDto;
import cn.xk.xcode.entity.dto.channel.DelMessageChannelDto;
import cn.xk.xcode.entity.dto.channel.QueryMessageChannelDto;
import cn.xk.xcode.entity.dto.channel.UpdateMessageChannelDto;
import cn.xk.xcode.entity.vo.MessageChannelVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.MessageChannelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author xuk
 * @Date 2025/3/10 9:14
 * @Version 1.0.0
 * @Description MessageChannelController
 **/
@RestController
@RequestMapping("/message/channel")
@Tag(name = "消息渠道接口")
public class MessageChannelController {

    @Resource
    private MessageChannelService messageChannelService;

    @Operation(description = "新增渠道")
    @PostMapping("/add")
    public CommonResult<Boolean> addMessageChannel(@Validated @RequestBody AddMessageChannelDto addMessageChannelDto) {
        return CommonResult.success(messageChannelService.addMessageChannel(addMessageChannelDto));
    }

    @Operation(description = "删除渠道")
    @PostMapping("/del")
    public CommonResult<Boolean> delMessageChannel(@Validated @RequestBody DelMessageChannelDto delMessageChannelDto) {
        return CommonResult.success(messageChannelService.deleteChannel(delMessageChannelDto));
    }

    @Operation(description = "修改渠道")
    @PostMapping("/update")
    public CommonResult<Boolean> updateMessageChannel(@Validated @RequestBody UpdateMessageChannelDto updateMessageChannelDto) {
        return CommonResult.success(messageChannelService.updateMessageChannel(updateMessageChannelDto));
    }

    @Operation(description = "获取渠道")
    @PostMapping("/list")
    public CommonResult<List<MessageChannelVo>>  getMessageChannel(@RequestBody QueryMessageChannelDto queryMessageChannelDto) {
        return CommonResult.success(messageChannelService.getMessageChannel(queryMessageChannelDto));
    }

}
