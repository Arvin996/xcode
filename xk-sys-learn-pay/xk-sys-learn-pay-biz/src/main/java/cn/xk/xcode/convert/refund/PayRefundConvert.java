package cn.xk.xcode.convert.refund;

import cn.xk.xcode.entity.po.PayRefundPo;
import cn.xk.xcode.entity.vo.refund.PayCreateRefundVo;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/10/6 14:58
 * @description PayRefundConvert
 */
public class PayRefundConvert {

    public static PayCreateRefundVo convert(PayRefundPo payRefundPo){
        PayCreateRefundVo payCreateRefundVo = new PayCreateRefundVo();
        payCreateRefundVo.setId(payRefundPo.getId());
        payCreateRefundVo.setStatus(payRefundPo.getStatus());
        payCreateRefundVo.setRefundPrice(payRefundPo.getRefundPrice());
        payCreateRefundVo.setMerchantOrderId(payRefundPo.getMerchantOrderId());
        payCreateRefundVo.setSuccessTime(payRefundPo.getSuccessTime());
        return payCreateRefundVo;
    }
}
