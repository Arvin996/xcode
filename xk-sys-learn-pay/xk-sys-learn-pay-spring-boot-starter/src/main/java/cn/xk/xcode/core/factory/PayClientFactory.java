package cn.xk.xcode.core.factory;

import cn.xk.xcode.core.client.AbstractPayClient;
import cn.xk.xcode.core.client.PayClient;
import cn.xk.xcode.core.config.PayClientConfig;

/**
 * @Author xuk
 * @Date 2024/9/3 19:23
 * @Version 1.0.0
 * @Description PayClientFactory
 **/
public interface PayClientFactory {

    /**
     * 根据支付渠道获取支付客户端
     * @param channel 支付渠道
     * @return 支付客户端
     */
    PayClient getPayClient(String channel);

    /**
     * 注册支付客户端
     * @param channel 客户端号
     * @param payClient 客户端
     */
    <Config extends PayClientConfig> void registerPayClient(String channel, AbstractPayClient<Config> payClient);

    /**
     * 更新支付客户端
     * @param channel 支付渠道
     * @param payClient 支付客户端
     */
    <Config extends PayClientConfig> void updatePayClient(String channel, Config config, PayClient payClient);
}
