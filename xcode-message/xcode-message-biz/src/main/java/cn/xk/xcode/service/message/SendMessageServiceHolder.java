package cn.xk.xcode.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2025/5/19 10:51
 * @Version 1.0.0
 * @Description SendMessageServiceHolder
 **/
@Component
public class SendMessageServiceHolder {

    private final Map<String, AbstractSendMessageService> sendMessageServiceMap;

    @Autowired
    public SendMessageServiceHolder(List<AbstractSendMessageService> sendMessageServices) {
        sendMessageServiceMap = new HashMap<>();
        sendMessageServices.forEach(sendMessageService -> sendMessageServiceMap.put(sendMessageService.sendType(), sendMessageService));
    }

    public AbstractSendMessageService routeSendMessageService(String msgChannel) {
        return sendMessageServiceMap.get(msgChannel);
    }
}
