package cn.xk.xcode.convert.merchant;

import cn.xk.xcode.entity.dto.merchant.AddMerchantDto;
import cn.xk.xcode.entity.po.PayMerchantPo;

/**
 * @Author xuk
 * @Date 2024/9/23 14:55
 * @Version 1.0.0
 * @Description MerchantConvert
 **/
public class MerchantConvert {

    public static PayMerchantPo convert(AddMerchantDto addMerchantDto){
        PayMerchantPo payMerchantPo = new PayMerchantPo();
        payMerchantPo.setMerchantNo(addMerchantDto.getMerchantNo());
        payMerchantPo.setMerchantName(addMerchantDto.getMerchantName());
        payMerchantPo.setMerchantShortName(addMerchantDto.getMerchantShortName());
        payMerchantPo.setRemark(addMerchantDto.getRemark());
        return payMerchantPo;
    }
}
