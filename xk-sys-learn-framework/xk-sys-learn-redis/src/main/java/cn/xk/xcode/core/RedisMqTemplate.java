package cn.xk.xcode.core;

import cn.hutool.json.JSONUtil;
import cn.xk.xcode.message.Header;
import cn.xk.xcode.message.MessageEntity;
import lombok.Getter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/7/1 16:53
 * @Version 1.0
 * @Description RedisMqTemplate
 */
@Component
@ConditionalOnBean(MessageInterceptorHolder.class)
@Getter
public class RedisMqTemplate {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "messageInterceptorHolder")
    private MessageInterceptorHolder messageInterceptorHolder;

    public void sendMessage(String msgType, Object messageContent, String fromUser, List<String> toUsers, boolean pushAll){
        sendMessage(msgType, "default", messageContent, fromUser, toUsers, pushAll);
    }

    public void sendMessage(String msgType, String msgChannel, Object messageContent, String fromUser, List<String> toUsers, boolean pushAll){
        MessageEntity messageEntity = new MessageEntity();
        Header header = new Header();
        header.setType(msgType);
        header.setChannel(msgChannel);
        messageEntity.setHeader(header);
        messageEntity.setData(messageContent);
        messageEntity.setFromUser(fromUser);
        messageEntity.setToUsers(toUsers);
        messageEntity.setToPushAll(pushAll);
        sendMessage(messageEntity);
    }


    public void sendMessage(MessageEntity message) {
        before(message);
        Object data = message.getData();
        redisTemplate.convertAndSend(message.getHeader().getChannel(), JSONUtil.toJsonStr(data));
        after(message);
    }

    private void before(MessageEntity message) {
        messageInterceptorHolder.getSendMessageInterceptor().forEach(
                interceptor -> interceptor.sendMessageBefore(message)
        );
    }

    private void after(MessageEntity message) {
      messageInterceptorHolder.getSendMessageInterceptor().forEach(
                interceptor -> interceptor.sendMessageAfter(message)
        );
    }
}
