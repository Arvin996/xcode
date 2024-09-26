package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.xk.xcode.entity.dto.channel.AddPayChannelDto;
import cn.xk.xcode.entity.dto.channel.PayChannelBaseDto;
import cn.xk.xcode.entity.dto.channel.QueryChannelDto;
import cn.xk.xcode.entity.dto.channel.UpdatePayChannelDto;
import cn.xk.xcode.entity.vo.channel.PayChannelResultVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.PayAppChannelService;
import cn.xk.xcode.service.PayChannelService;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static cn.xk.xcode.entity.def.PayAppChannelTableDef.PAY_APP_CHANNEL_PO;
import static cn.xk.xcode.entity.def.PayChannelTableDef.PAY_CHANNEL_PO;
import static cn.xk.xcode.enums.PayModuleErrorCodeConstants.CHANNEL_DELETE_FAILED;

/**
 * @Author xuk
 * @Date 2024/9/26 14:50
 * @Version 1.0.0
 * @Description PayChannelController
 **/
@RestController
@RequestMapping("pay/channel")
@Tag(name = "支付渠道controller")
public class PayChannelController {

    @Resource
    private PayChannelService payChannelService;

    @Resource
    private PayAppChannelService payAppChannelService;

    @PostMapping("/add")
    @SaCheckPermission("pay:channel:add")
    @Operation(summary = "添加支付渠道")
    public CommonResult<Boolean> addPayChannel(@Validated @RequestBody AddPayChannelDto addPayChannelDto) {
        return CommonResult.success(payChannelService.addPayChannel(addPayChannelDto));
    }

    @PostMapping("/del")
    @SaCheckPermission("pay:channel:del")
    @Operation(summary = "删除支付渠道")
    public CommonResult<Boolean> delPayChannel(@Validated @RequestBody PayChannelBaseDto payChannelBaseDto) {
        if (payAppChannelService.count(PAY_APP_CHANNEL_PO.CHANNEL_ID.eq(payChannelBaseDto.getId())) > 0){
            ExceptionUtil.castServiceException(CHANNEL_DELETE_FAILED);
        }
        return CommonResult.success(payChannelService.removeById(payChannelBaseDto.getId()));
    }

    @PostMapping("/update")
    @SaCheckPermission("pay:channel:update")
    @Operation(summary = "更新支付渠道")
    public CommonResult<Boolean> updatePayChannel(@Validated @RequestBody UpdatePayChannelDto updatePayChannelDto) {
        return CommonResult.success(payChannelService.updatePayChannel(updatePayChannelDto));
    }

    @PostMapping("/list")
    @SaCheckPermission("pay:channel:list")
    @Operation(summary = "查询支付渠道列表")
    public CommonResult<PayChannelResultVo> queryPayChannelList(@RequestBody QueryChannelDto queryChannelDto){
        return CommonResult.success(new PayChannelResultVo().setList(
                payChannelService.list(QueryWrapper.create()
                        .where("1=1")
                        .and(PAY_CHANNEL_PO.CHANNEL_CODE.like(queryChannelDto.getChannelCode()).when(StringUtils.hasText(queryChannelDto.getChannelCode()))))
        ));
    }
}
