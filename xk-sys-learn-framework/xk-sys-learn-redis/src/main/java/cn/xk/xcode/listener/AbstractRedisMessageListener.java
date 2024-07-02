package cn.xk.xcode.listener;

import cn.hutool.core.lang.Assert;
import cn.hutool.extra.spring.SpringUtil;
import cn.xk.xcode.core.RedisMqTemplate;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.message.MessageEntity;
import cn.xk.xcode.utils.object.BeanUtil;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * @Author xuk
 * @Date 2024/7/1 16:39
 * @Version 1.0
 * @Description AbstractMessageListener
 */
public abstract class AbstractRedisMessageListener implements MessageListener {

    public abstract String channel();

    private final RedisMqTemplate redisMqTemplate = SpringUtil.getBean(RedisMqTemplate.class);

    @Override
    public void onMessage(Message message, byte[] pattern) {
        MessageEntity messageEntity = BeanUtil.toBean(message.getBody(), MessageEntity.class);
        before(messageEntity);
        onMessage(messageEntity);
        after(messageEntity);
    }

    public abstract void onMessage(MessageEntity messageEntity);

    private void before(MessageEntity messageEntity) {
        Assert.isNull(redisMqTemplate, () -> {
            throw new ServiceException("redisMqTemplate is null");
        });
        redisMqTemplate.getMessageInterceptorHolder().getConsumeMessageInterceptor()
                .forEach(interceptor -> {
                    interceptor.consumeMessageBefore(messageEntity);
                });
    }

    private void after(MessageEntity messageEntity) {
        redisMqTemplate.getMessageInterceptorHolder().getConsumeMessageInterceptor()
                .forEach(interceptor -> {
                    interceptor.consumeMessageAfter(messageEntity);
                });
    }
}
