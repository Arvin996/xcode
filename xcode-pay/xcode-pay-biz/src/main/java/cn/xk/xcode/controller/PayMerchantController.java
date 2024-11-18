package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.entity.dto.merchant.AddMerchantDto;
import cn.xk.xcode.entity.dto.merchant.MerchantBaseDto;
import cn.xk.xcode.entity.dto.merchant.QueryMerchantDto;
import cn.xk.xcode.entity.dto.merchant.UpdateMerchantDto;
import cn.xk.xcode.entity.vo.merchant.MerchantResultVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.PayMerchantService;
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

import static cn.xk.xcode.entity.def.PayMerchantTableDef.PAY_MERCHANT_PO;

/**
 * @Author xuk
 * @Date 2024/9/23 14:38
 * @Version 1.0.0
 * @Description PayMerchantController
 **/
@RestController
@RequestMapping("/pay/merchant")
@Tag(name = "商户接口")
public class PayMerchantController {

    @Resource
    private PayMerchantService payMerchantService;

    @Operation(summary = "添加商户")
    @PostMapping("/add")
    @SaCheckPermission("pay:merchant:add")
    public CommonResult<Boolean> addPayMerchant(@Validated @RequestBody AddMerchantDto addMerchantDto){
        return CommonResult.success(payMerchantService.addPayMerchant(addMerchantDto));
    }

    @Operation(summary = "删除商户")
    @PostMapping("/del")
    @SaCheckPermission("pay:merchant:del")
    public CommonResult<Boolean> delPayMerchant(@Validated @RequestBody MerchantBaseDto merchantBaseDto){
        return CommonResult.success(payMerchantService.removeById(merchantBaseDto.getId()));
    }

    @Operation(summary = "修改商户")
    @PostMapping("/update")
    @SaCheckPermission("pay:merchant:update")
    public CommonResult<Boolean> updatePayMerchant(@Validated @RequestBody UpdateMerchantDto updateMerchantDto){
        return CommonResult.success(payMerchantService.updatePayMerchant(updateMerchantDto));
    }

    @Operation(summary = "查询商户")
    @PostMapping("/list")
    @SaCheckPermission("pay:merchant:list")
    public CommonResult<MerchantResultVo> queryPayMerchantList(@Validated @RequestBody QueryMerchantDto queryMerchantDto){
        QueryWrapper queryWrapper = QueryWrapper
                .create()
                .where(PAY_MERCHANT_PO.MERCHANT_NAME.like(queryMerchantDto.getMerchantName()).when(StringUtils.hasText(queryMerchantDto.getMerchantName())))
                .where(PAY_MERCHANT_PO.MERCHANT_NO.like(queryMerchantDto.getMerchantNo()).when(StringUtils.hasText(queryMerchantDto.getMerchantNo())))
                .where(PAY_MERCHANT_PO.MERCHANT_SHORT_NAME.like(queryMerchantDto.getMerchantShortName()).when(StringUtils.hasText(queryMerchantDto.getMerchantShortName())))
                .where(PAY_MERCHANT_PO.STATUS.eq(queryMerchantDto.getStatus()).when(ObjectUtil.isNotNull(queryMerchantDto.getStatus())));
        return CommonResult.success(new MerchantResultVo().setPayMerchantPoList(payMerchantService.list(queryWrapper)));
    }
}

