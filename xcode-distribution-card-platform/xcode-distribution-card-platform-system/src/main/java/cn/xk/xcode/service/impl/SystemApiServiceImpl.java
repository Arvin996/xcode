package cn.xk.xcode.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.SystemApiPo;
import cn.xk.xcode.mapper.SystemApiMapper;
import cn.xk.xcode.service.SystemApiService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author xuk
 * @since 2025-05-09
 */
@Service
public class SystemApiServiceImpl extends ServiceImpl<SystemApiMapper, SystemApiPo> implements SystemApiService {

}
