package cn.xk.xcode.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.SystemUserPo;
import cn.xk.xcode.mapper.SystemUserMapper;
import cn.xk.xcode.service.SystemUserService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author xuk
 * @since 2025-05-09
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUserPo> implements SystemUserService {

}
