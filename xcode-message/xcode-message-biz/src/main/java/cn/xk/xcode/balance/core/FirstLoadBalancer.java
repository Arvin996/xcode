package cn.xk.xcode.balance.core;

import cn.xk.xcode.entity.po.MessageChannelAccountPo;
import cn.xk.xcode.utils.collections.CollectionUtil;

import java.util.List;

/**
 * @Author xuk
 * @Date 2025/3/11 14:34
 * @Version 1.0.0
 * @Description FirstLoadBalancer
 **/
public class FirstLoadBalancer implements ILoadBalancer {

    @Override
    public MessageChannelAccountPo choose(List<MessageChannelAccountPo> accountPoList) {
        if (CollectionUtil.isEmpty(accountPoList)){
            return null;
        }
        return accountPoList.get(0);
    }
}
