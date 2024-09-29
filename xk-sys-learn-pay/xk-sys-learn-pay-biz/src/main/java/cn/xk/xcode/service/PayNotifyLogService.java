package cn.xk.xcode.service;

import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.PayNotifyLogPo;

/**
 *  服务层。
 *
 * @author Administrator
 * @since 2024-09-23
 */
public interface PayNotifyLogService extends IService<PayNotifyLogPo> {

    void createPayNotifyTask(String type, Long orderId);
}
