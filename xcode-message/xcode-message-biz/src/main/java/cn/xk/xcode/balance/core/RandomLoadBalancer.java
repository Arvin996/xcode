package cn.xk.xcode.balance.core;

import cn.xk.xcode.entity.po.MessageChannelAccountPo;
import cn.xk.xcode.utils.collections.CollectionUtil;

import java.util.List;

/**
 * @Author xuk
 * @Date 2025/3/11 14:32
 * @Version 1.0.0
 * @Description RondomLoadBalancer
 **/
public class RandomLoadBalancer implements ILoadBalancer {

    @Override
    public MessageChannelAccountPo choose(List<MessageChannelAccountPo> accountPoList) {
        if (CollectionUtil.isEmpty(accountPoList)){
            return null;
        }
        int randomIndex = (int) (Math.random() * accountPoList.size());
        return accountPoList.get(randomIndex);
    }
}
