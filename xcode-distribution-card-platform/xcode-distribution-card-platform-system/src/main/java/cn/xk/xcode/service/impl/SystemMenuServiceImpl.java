package cn.xk.xcode.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.SystemMenuPo;
import cn.xk.xcode.mapper.SystemMenuMapper;
import cn.xk.xcode.service.SystemMenuService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author xuk
 * @since 2025-05-09
 */
@Service
public class SystemMenuServiceImpl extends ServiceImpl<SystemMenuMapper, SystemMenuPo> implements SystemMenuService {

}
