package cn.xk.xcode.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.UserRolePo;
import cn.xk.xcode.mapper.UserRoleMapper;
import cn.xk.xcode.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author Arvin
 * @since 2024-06-21
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRolePo> implements UserRoleService {

}
