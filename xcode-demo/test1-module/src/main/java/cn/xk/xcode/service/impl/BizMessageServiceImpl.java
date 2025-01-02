package cn.xk.xcode.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.BizMessagePo;
import cn.xk.xcode.mapper.BizMessageMapper;
import cn.xk.xcode.service.BizMessageService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author xuk
 * @since 2024-12-27
 */
@Service
public class BizMessageServiceImpl extends ServiceImpl<BizMessageMapper, BizMessagePo> implements BizMessageService {

}
