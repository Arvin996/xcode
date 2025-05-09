package cn.xk.xcode.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.MessageTaskPo;
import cn.xk.xcode.mapper.MessageTaskMapper;
import cn.xk.xcode.service.MessageTaskService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author xuk
 * @since 2025-03-10
 */
@Service
public class MessageTaskServiceImpl extends ServiceImpl<MessageTaskMapper, MessageTaskPo> implements MessageTaskService {

}
