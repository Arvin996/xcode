package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.refund.PayCreateRefundDto;
import cn.xk.xcode.entity.vo.refund.PayCreateRefundVo;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.PayRefundPo;

/**
 *  服务层。
 *
 * @author Administrator
 * @since 2024-09-23
 */
public interface PayRefundService extends IService<PayRefundPo> {

    PayCreateRefundVo getRefund(Long refundId);

    Long createRefund(PayCreateRefundDto reqDTO);
}
