package cn.xk.xcode.balance.core;

import cn.xk.xcode.entity.po.MessageChannelAccountPo;

import java.util.List;

/**
 * @Author xuk
 * @Date 2025/3/11 14:21
 * @Version 1.0.0
 * @Description ILoadBalancer
 **/
public interface ILoadBalancer {

    MessageChannelAccountPo choose(List<MessageChannelAccountPo> accountPoList);
}
