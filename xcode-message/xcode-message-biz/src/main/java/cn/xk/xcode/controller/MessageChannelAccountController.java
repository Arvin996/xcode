package cn.xk.xcode.controller;

import cn.xk.xcode.entity.dto.account.AddMessageChannelAccountDto;
import cn.xk.xcode.entity.dto.account.QueryMessageChannelAccountDto;
import cn.xk.xcode.entity.dto.account.UpdateMessageChannelAccountDto;
import cn.xk.xcode.entity.vo.account.MessageChannelAccountVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.MessageChannelAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/del/{id}")
    @Operation(description = "删除渠道账户")
    public CommonResult<Boolean> delMessageChannelAccount(@PathVariable("id") Integer id) {
        return CommonResult.success(messageChannelAccountService.delMessageChannelAccount(id));
    }

    @PostMapping("/update")
    @Operation(description = "修改渠道账户")
    public CommonResult<Boolean> updateMessageChannelAccount(@Validated @RequestBody UpdateMessageChannelAccountDto updateMessageChannelAccountDto) {
        return CommonResult.success(messageChannelAccountService.updateMessageChannelAccount(updateMessageChannelAccountDto));
    }

    @PostMapping("/queryAll")
    @Operation(description = "查询渠道账户")
    public CommonResult<PageResult<MessageChannelAccountVo>> queryAll(@RequestBody QueryMessageChannelAccountDto queryMessageChannelAccountDto) {
        return CommonResult.success(messageChannelAccountService.queryAll(queryMessageChannelAccountDto));
    }
}
