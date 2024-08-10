package cn.xk.xcode.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.MemberUserPo;
import cn.xk.xcode.mapper.MemberUserMapper;
import cn.xk.xcode.service.MemberUserService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author lenovo
 * @since 2024-08-05
 */
@Service
public class MemberUserServiceImpl extends ServiceImpl<MemberUserMapper, MemberUserPo> implements MemberUserService {

}
