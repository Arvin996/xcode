package cn.xk.xcode.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.PayNotifyLogPo;
import cn.xk.xcode.mapper.PayNotifyLogMapper;
import cn.xk.xcode.service.PayNotifyLogService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author Administrator
 * @since 2024-09-23
 */
@Service
public class PayNotifyLogServiceImpl extends ServiceImpl<PayNotifyLogMapper, PayNotifyLogPo> implements PayNotifyLogService {

    @Override
    public void createSuccessNotifyLog(String type, Long orderId) {

    }
}
