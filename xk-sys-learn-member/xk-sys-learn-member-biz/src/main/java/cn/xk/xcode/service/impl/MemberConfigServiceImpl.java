package cn.xk.xcode.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.MemberConfigPo;
import cn.xk.xcode.mapper.MemberConfigMapper;
import cn.xk.xcode.service.MemberConfigService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author lenovo
 * @since 2024-08-05
 */
@Service
public class MemberConfigServiceImpl extends ServiceImpl<MemberConfigMapper, MemberConfigPo> implements MemberConfigService {

}
