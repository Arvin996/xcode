package cn.xk.xcode.service.impl;

import cn.xk.xcode.entity.po.HisBizMessagePo;
import cn.xk.xcode.service.HisBizMessageService;
import cn.xk.xcode.utils.object.BeanUtil;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.BizMessagePo;
import cn.xk.xcode.mapper.BizMessageMapper;
import cn.xk.xcode.service.BizMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static cn.xk.xcode.entity.def.BizMessageTableDef.BIZ_MESSAGE_PO;

/**
 *  服务层实现。
 *
 * @author lenovo
 * @since 2024-07-16
 */
@Service
public class BizMessageServiceImpl extends ServiceImpl<BizMessageMapper, BizMessagePo> implements BizMessageService {

    @Resource
    private HisBizMessageService hisBizMessageService;

    @Override
    public List<BizMessagePo> getBizMessageList(int shardIndex, int shardTotal, String messageType, int count) {
        QueryWrapper queryWrapper = QueryWrapper.create().select(BIZ_MESSAGE_PO.ALL_COLUMNS)
                .from(BIZ_MESSAGE_PO)
                .where(QueryMethods.mod(BIZ_MESSAGE_PO.ID, shardTotal).eq(shardIndex))
                .and(BIZ_MESSAGE_PO.FAIL_COUNT.lt(BIZ_MESSAGE_PO.MAX_FAIL_COUNT))
                .and(BIZ_MESSAGE_PO.STATUS.eq("0").or(BIZ_MESSAGE_PO.STATUS.eq("3")))
                .limit(count);
        return this.list(queryWrapper);
    }

    @Transactional
    @Override
    public boolean complete(Long id) {
        // 1. 改状态
        boolean res = UpdateChain.of(BizMessageMapper.class)
                .set(BIZ_MESSAGE_PO.STATUS, "1")
                .where(BIZ_MESSAGE_PO.ID.eq(id))
                .update();
        if (!res){
            return false;
        }
        // 2. 传到历史表
        BizMessagePo bizMessagePo = this.getById(id);
        HisBizMessagePo hisBizMessagePo = BeanUtil.toBean(bizMessagePo, HisBizMessagePo.class);
        res = this.removeById(id);
        if (!res){
            return false;
        }
        return hisBizMessageService.save(hisBizMessagePo);
    }

    @Override
    public boolean startWork(Long id){
        return UpdateChain.of(BizMessageMapper.class)
                .set(BIZ_MESSAGE_PO.STATUS, "3")
                .where(BIZ_MESSAGE_PO.ID.eq(id))
                .and(BIZ_MESSAGE_PO.STATUS.eq("0").or(BIZ_MESSAGE_PO.STATUS.eq("3")))
                .update();
    }

    @Override
    @Transactional
    public void handlerFailMessage(Long id, String errMsg){
        BizMessagePo bizMessagePo = getById(id);
        Integer failCount = bizMessagePo.getFailCount();
        if (failCount + 1 == bizMessagePo.getMaxFailCount()){
            //达到最大失败次数 删除 并 传到历史表
            hisBizMessageService.save(BeanUtil.toBean(bizMessagePo, HisBizMessagePo.class));
            this.removeById(id);
            return;
        }
        // 更新
        UpdateChain.of(BizMessageMapper.class)
                .set(BIZ_MESSAGE_PO.FAIL_COUNT, failCount + 1)
                .set(BIZ_MESSAGE_PO.ERR_MSG, errMsg)
                .where(BIZ_MESSAGE_PO.ID.eq(id))
                .update();
    }

    public boolean addMessage(BizMessagePo bizMessagePo){
        return this.save(bizMessagePo);
    }
}
