package cn.xk.xcode.service.impl;

import cn.xk.xcode.entity.po.MessageTaskPo;
import cn.xk.xcode.mapper.MessageTaskMapper;
import cn.xk.xcode.service.MessageTaskService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author Administrator
 * @since 2025-05-15
 */
@Service
public class MessageTaskServiceImpl extends ServiceImpl<MessageTaskMapper, MessageTaskPo> implements MessageTaskService {

}
