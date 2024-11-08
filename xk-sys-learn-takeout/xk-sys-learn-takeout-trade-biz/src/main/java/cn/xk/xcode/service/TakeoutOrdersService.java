package cn.xk.xcode.service;

import cn.xk.xcode.entity.TakeoutOrderResultVo;
import cn.xk.xcode.entity.dto.order.CancelOrderDto;
import cn.xk.xcode.entity.dto.order.PayOrderDto;
import cn.xk.xcode.entity.dto.order.SubmitOrderDto;
import cn.xk.xcode.entity.vo.order.OrderSubmitResultVo;
import cn.xk.xcode.pojo.PageResult;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.TakeoutOrdersPo;

import java.util.List;

/**
 *  服务层。
 *
 * @author xuk
 * @since 2024-11-06
 */
public interface TakeoutOrdersService extends IService<TakeoutOrdersPo> {

    TakeoutOrderResultVo getOrder(Long orderId);

    List<TakeoutOrderResultVo> getOrderList(Long userId);

    TakeoutOrderResultVo getUserOrderId(Long userId, Long orderId);

    OrderSubmitResultVo submitOrder(SubmitOrderDto submitOrderDto);

    Boolean cancelOrder(CancelOrderDto cancelOrderDto);

    Boolean payOrder(PayOrderDto payOrderDto);
}
