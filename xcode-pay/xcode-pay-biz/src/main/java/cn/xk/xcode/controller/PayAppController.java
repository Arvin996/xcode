package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.xk.xcode.entity.dto.app.*;
import cn.xk.xcode.entity.vo.app.PayAppResultVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.PayAppChannelService;
import cn.xk.xcode.service.PayAppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import static cn.xk.xcode.entity.def.PayAppChannelTableDef.PAY_APP_CHANNEL_PO;
import static cn.xk.xcode.enums.PayModuleErrorCodeConstants.APP_DELETE_FAILED;

/**
 * @Author xuk
 * @Date 2024/9/26 14:05
 * @Version 1.0.0
 * @Description PayAppController
 **/
@RestController
@RequestMapping("/pay/app")
@Tag(name = "支付应用controller")
public class PayAppController {

    @Resource
    private PayAppService payAppService;

    @Resource
    private PayAppChannelService payAppChannelService;

    @PostMapping("/add")
    @SaCheckPermission("pay:app:add")
    @Operation(summary = "添加支付应用")
    public CommonResult<Boolean> addPayApp(@Validated @RequestBody AddPayAppDto addPayAppDto) {
        return CommonResult.success(payAppService.addPayApp(addPayAppDto));
    }

    @PostMapping("/del")
    @SaCheckPermission("pay:app:del")
    @Operation(summary = "删除支付应用")
    public CommonResult<Boolean> delPayApp(@Validated @RequestBody PayAppBaseDto payAppBaseDto) {
        if (payAppChannelService.count(PAY_APP_CHANNEL_PO.APP_ID.eq(payAppBaseDto.getId())) > 0) {
            ExceptionUtil.castServiceException(APP_DELETE_FAILED);
        }
        return CommonResult.success(payAppService.removeById(payAppBaseDto.getId()));
    }

    @PostMapping("/update")
    @SaCheckPermission("pay:app:update")
    @Operation(summary = "修改支付应用")
    public CommonResult<Boolean> updatePayApp(@Validated @RequestBody UpdatePayAppDto updatePayAppDto) {
        return CommonResult.success(payAppService.updatePayApp(updatePayAppDto));
    }

    @PostMapping("/list")
    @SaCheckPermission("pay:app:list")
    @Operation(summary = "查询支付应用列表")
    public CommonResult<PayAppResultVo> queryPayAppList(@RequestBody QueryPayAppDto queryPayAppDto) {
        return CommonResult.success(payAppService.queryPayAppList(queryPayAppDto));
    }

    @PostMapping("/bindAppChannel")
    @SaCheckPermission("pay:app:bindAppChannel")
    @Operation(summary = "绑定支付应用渠道")
    public CommonResult<Boolean> bindAppChannel(@Validated @RequestBody BindAppChannelDto bindAppChannelDto) {
        return CommonResult.success(payAppChannelService.bindAppChannel(bindAppChannelDto));
    }
}
