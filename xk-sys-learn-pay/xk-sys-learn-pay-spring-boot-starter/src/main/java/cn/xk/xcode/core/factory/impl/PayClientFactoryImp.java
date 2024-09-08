package cn.xk.xcode.core.factory.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.core.client.AbstractPayClient;
import cn.xk.xcode.core.client.PayClient;
import cn.xk.xcode.core.config.PayClientConfig;
import cn.xk.xcode.core.factory.PayClientFactory;
import cn.xk.xcode.exception.core.ExceptionUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static cn.xk.xcode.enums.PayErrorCodeConstants.PAY_CLIENT_NOT_EXISTS;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/9/6 22:04
 * @description
 */
public class PayClientFactoryImp implements PayClientFactory {

    Map<String, AbstractPayClient<?>> payClientMap = new ConcurrentHashMap<>();


    @Override
    public PayClient getPayClient(String channel) {
        AbstractPayClient<?> payClient = payClientMap.get(channel);
        if (ObjectUtil.isAllEmpty(payClient)){
            ExceptionUtil.castServiceException(PAY_CLIENT_NOT_EXISTS, channel);
        }
        return payClient;
    }

    @Override
    public <Config extends PayClientConfig> void registerPayClient(String channel, AbstractPayClient<Config> payClient) {
        payClientMap.put(channel, payClient);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <Config extends PayClientConfig> void updatePayClient(String channel, Config config, PayClient payClient) {
        AbstractPayClient<Config> client = (AbstractPayClient<Config>) payClientMap.get(channel);
        client.reloadConfig(config);
    }
}
