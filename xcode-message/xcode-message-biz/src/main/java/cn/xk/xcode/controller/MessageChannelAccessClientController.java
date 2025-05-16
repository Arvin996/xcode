package cn.xk.xcode.controller;

import cn.xk.xcode.entity.dto.client.*;
import cn.xk.xcode.entity.vo.client.MessageChannelClientVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.MessageChannelAccessClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2025/5/15 11:19
 * @Version 1.0.0
 * @Description MessageChannelAccessClientController
 **/
@RestController
@RequestMapping("/manage/client")
@Tag(name = "消息渠道接入商接口")
@Validated
public class MessageChannelAccessClientController {

    @Resource
    private MessageChannelAccessClientService messageChannelAccessClientService;

    @Operation(summary = "查询所有接入商")
    @PostMapping("/queryAll")
    public CommonResult<PageResult<MessageChannelClientVo>> queryAll(@RequestBody QueryChannelClientDto queryChannelClientDto) {
        return CommonResult.success(messageChannelAccessClientService.queryAll(queryChannelClientDto));
    }

    @DeleteMapping("/del/{id}")
    @Operation(summary = "删除接入商")
    public CommonResult<Boolean> deleteClient(@PathVariable("id") Integer id) {
        return CommonResult.success(messageChannelAccessClientService.deleteClient(id));
    }

    @PostMapping("/update")
    @Operation(summary = "修改接入商")
    public CommonResult<Boolean> updateClient(@RequestBody UpdateChannelClientDto updateChannelClientDto) {
        return CommonResult.success(messageChannelAccessClientService.updateClient(updateChannelClientDto));
    }

    @PostMapping("/register")
    @Operation(summary = "申请注册接入商")
    public CommonResult<Boolean> registerClient(@RequestBody RegisterChannelClientDto registerChannelClientDto) {
        return CommonResult.success(messageChannelAccessClientService.registerClient(registerChannelClientDto));
    }

    @PostMapping("/addAccessCount")
    @Operation(summary = "增加接入商配额")
    public CommonResult<Boolean> addAccessCount(@RequestBody AddChannelClientAccessCount addChannelClientAccessCount) {
        return CommonResult.success(messageChannelAccessClientService.addAccessCount(addChannelClientAccessCount));
    }

    @PostMapping("/refreshToken")
    @Operation(summary = "刷新接入商token")
    public CommonResult<Boolean> refreshToken(@RequestBody ClientRefreshTokenDto clientRefreshTokenDto) {
        return CommonResult.success(messageChannelAccessClientService.refreshToken(clientRefreshTokenDto));
    }

    @PutMapping("/lockClient/{id}")
    @Operation(summary = "锁定接入商")
    public CommonResult<Boolean> lockClient(@PathVariable("id") Integer id) {
        return CommonResult.success(messageChannelAccessClientService.lockClient(id));
    }

    @PutMapping("/unlockClient/{id}")
    @Operation(summary = "锁定接入商")
    public CommonResult<Boolean> unlockClient(@PathVariable("id") Integer id) {
        return CommonResult.success(messageChannelAccessClientService.unlockClient(id));
    }

    @PostMapping("/bindClientChannel")
    @Operation(summary = "绑定渠道")
    public CommonResult<Boolean> bindClientChannel(@RequestBody BindClientChannelDto bindClientChannelDto) {
        return CommonResult.success(messageChannelAccessClientService.bindClientChannel(bindClientChannelDto));
    }
}
