package cn.xk.xcode.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.ClientPo;
import cn.xk.xcode.mapper.ClientMapper;
import cn.xk.xcode.service.ClientService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author lenovo
 * @since 2024-06-24
 */
@Service
public class ClientServiceImpl extends ServiceImpl<ClientMapper, ClientPo> implements ClientService {

}
