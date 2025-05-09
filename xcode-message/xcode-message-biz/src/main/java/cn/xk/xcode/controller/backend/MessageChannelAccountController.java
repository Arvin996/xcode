package cn.xk.xcode.controller.backend;

import cn.xk.xcode.entity.dto.account.AddMessageChannelAccountDto;
import cn.xk.xcode.entity.dto.account.DelMessageChannelAccountDto;
import cn.xk.xcode.entity.dto.account.UpdateMessageChannelAccountDto;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.MessageChannelAccountService;
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
 * @Date 2025/3/10 9:58
 * @Version 1.0.0
 * @Description MessageChannelAccountController
 **/
@RestController
@RequestMapping("/message/channel/account")
@Tag(name = "消息渠道账户接口")
public class MessageChannelAccountController {

    @Resource
    private MessageChannelAccountService messageChannelAccountService;

    @Operation(description = "新增渠道账户")
    @PostMapping("/add")
    public CommonResult<Boolean>  addMessageChannelAccount(@Validated @RequestBody AddMessageChannelAccountDto addMessageChannelAccountDto) {
        return CommonResult.success(messageChannelAccountService.addMessageChannelAccount(addMessageChannelAccountDto));
    }

    @PostMapping("/del")
    public CommonResult<Boolean> delMessageChannelAccount(@Validated @RequestBody DelMessageChannelAccountDto delMessageChannelAccountDto) {
        return CommonResult.success(messageChannelAccountService.removeById(delMessageChannelAccountDto.getId()));
    }

    @PostMapping("/update")
    public CommonResult<Boolean> updateMessageChannelAccount(@Validated @RequestBody UpdateMessageChannelAccountDto updateMessageChannelAccountDto) {
        return CommonResult.success(messageChannelAccountService.updateMessageChannelAccount(updateMessageChannelAccountDto));
    }
}
