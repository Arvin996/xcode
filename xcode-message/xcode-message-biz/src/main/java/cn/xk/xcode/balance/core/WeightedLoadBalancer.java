package cn.xk.xcode.balance.core;

import cn.xk.xcode.entity.po.MessageChannelAccountPo;
import cn.xk.xcode.utils.collections.CollectionUtil;

import java.util.List;
import java.util.Random;

/**
 * @Author xuk
 * @Date 2025/3/11 14:33
 * @Version 1.0.0
 * @Description WeightedLoadBalancer
 **/
public class WeightedLoadBalancer implements ILoadBalancer {
    @Override
    public MessageChannelAccountPo choose(List<MessageChannelAccountPo> accountPoList) {
        if (CollectionUtil.isEmpty(accountPoList)){
            return null;
        }
        double totalWeight = accountPoList.stream().mapToDouble(MessageChannelAccountPo::getWeight).sum();
        Random random = new Random();
        double randNum = random.nextDouble() * totalWeight;
        double cumulativeWeight = 0.0;
        for (MessageChannelAccountPo accountPo : accountPoList) {
            cumulativeWeight += accountPo.getWeight();
            if (cumulativeWeight >= randNum) {
                return accountPo;
            }
        }
        return null;
    }
}
