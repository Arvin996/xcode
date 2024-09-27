package cn.xk.xcode.convert.order;

import cn.xk.xcode.entity.po.PayOrderPo;
import cn.xk.xcode.entity.vo.order.PayOrderResultVo;
import cn.xk.xcode.entity.dto.order.PayCreateOrderDto;

import static cn.xk.xcode.enums.PayOrderStatusEnums.PAY_WAITING;

/**
 * @Author xuk
 * @Date 2024/9/27 8:53
 * @Version 1.0.0
 * @Description OrderConvert
 **/
public class OrderConvert {

    public static PayOrderResultVo convert(PayOrderPo payOrderPo){
        PayOrderResultVo payOrderResultVo = new PayOrderResultVo();
        payOrderResultVo.setId(payOrderPo.getId());
        payOrderResultVo.setChannelCode(payOrderPo.getChannelCode());
        payOrderResultVo.setMerchantOrderId(payOrderPo.getMerchantOrderId());
        payOrderResultVo.setPrice(payOrderPo.getPrice());
        payOrderResultVo.setStatus(payOrderPo.getStatus());
        return payOrderResultVo;
    }

    public static PayOrderPo convert(PayCreateOrderDto payCreateOrderDto){
        PayOrderPo payOrderPo = new PayOrderPo();
        payOrderPo.setAppId(payCreateOrderDto.getAppId());
        payOrderPo.setMerchantOrderId(payOrderPo.getMerchantOrderId());
        payOrderPo.setSubject(payCreateOrderDto.getSubject());
        payOrderPo.setBody(payCreateOrderDto.getBody());
        payOrderPo.setPrice(payCreateOrderDto.getPrice());
        payOrderPo.setStatus(PAY_WAITING.getStatus());
        payOrderPo.setUserIp(payCreateOrderDto.getUserIp());
        payOrderPo.setExpireTime(payCreateOrderDto.getExpireTime());
        return payOrderPo;
    }
}
