package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.order.ExportOrderDto;
import cn.xk.xcode.entity.dto.order.PayCreateOrderDto;
import cn.xk.xcode.entity.dto.order.PayOrderSubmitReqDto;
import cn.xk.xcode.entity.vo.order.PayOrderResultVo;
import cn.xk.xcode.entity.vo.order.PayOrderSubmitRespVO;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.PayOrderPo;

import javax.servlet.http.HttpServletResponse;

/**
 *  服务层。
 *
 * @author Administrator
 * @since 2024-09-23
 */
public interface PayOrderService extends IService<PayOrderPo> {

    PayOrderResultVo getCreateOrder(Long orderId);

    Long createOrder(PayCreateOrderDto payCreateOrderDto);

    PayOrderSubmitRespVO submitOrder(PayOrderSubmitReqDto payOrderSubmitReqDto);

    void updateOrderRefundPrice(Long orderId, Integer refundPrice);
}
