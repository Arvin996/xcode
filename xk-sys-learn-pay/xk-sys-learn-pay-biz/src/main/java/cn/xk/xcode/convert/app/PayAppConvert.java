package cn.xk.xcode.convert.app;

import cn.xk.xcode.entity.dto.app.AddPayAppDto;
import cn.xk.xcode.entity.po.PayAppPo;

/**
 * @Author xuk
 * @Date 2024/9/26 14:19
 * @Version 1.0.0
 * @Description PayAppConvert
 **/
public class PayAppConvert {

    public static PayAppPo convert(AddPayAppDto addPayAppDto){
        PayAppPo payAppPo = new PayAppPo();
        payAppPo.setAppCode(addPayAppDto.getAppCode());
        payAppPo.setAppName(addPayAppDto.getAppName());
        payAppPo.setRemark(addPayAppDto.getRemark());
        payAppPo.setOrderNotifyUrl(addPayAppDto.getOrderNotifyUrl());
        payAppPo.setRefundNotifyUrl(addPayAppDto.getRefundNotifyUrl());
        return payAppPo;
    }
}
