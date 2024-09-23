package cn.xk.xcode.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.PayOrderPo;
import cn.xk.xcode.mapper.PayOrderMapper;
import cn.xk.xcode.service.PayOrderService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author Administrator
 * @since 2024-09-23
 */
@Service
public class PayOrderServiceImpl extends ServiceImpl<PayOrderMapper, PayOrderPo> implements PayOrderService {

}
