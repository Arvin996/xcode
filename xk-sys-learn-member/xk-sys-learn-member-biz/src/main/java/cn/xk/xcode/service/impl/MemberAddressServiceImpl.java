package cn.xk.xcode.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.MemberAddressPo;
import cn.xk.xcode.mapper.MemberAddressMapper;
import cn.xk.xcode.service.MemberAddressService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author lenovo
 * @since 2024-08-05
 */
@Service
public class MemberAddressServiceImpl extends ServiceImpl<MemberAddressMapper, MemberAddressPo> implements MemberAddressService {

}
