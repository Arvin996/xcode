package cn.xk.xcode.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.PayAppPo;
import cn.xk.xcode.mapper.PayAppMapper;
import cn.xk.xcode.service.PayAppService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author Administrator
 * @since 2024-09-23
 */
@Service
public class PayAppServiceImpl extends ServiceImpl<PayAppMapper, PayAppPo> implements PayAppService {

}
