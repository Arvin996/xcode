package cn.xk.xcode.service;

import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.BizMessagePo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *  服务层。
 *
 * @author lenovo
 * @since 2024-07-16
 */
public interface BizMessageService extends IService<BizMessagePo> {
    List<BizMessagePo> getBizMessageList(int shardIndex, int shardTotal, String messageType, int count);

    boolean complete(Long id);

    boolean startWork(Long id);

    @Transactional
    void handlerFailMessage(Long id, String errMsg);
}
