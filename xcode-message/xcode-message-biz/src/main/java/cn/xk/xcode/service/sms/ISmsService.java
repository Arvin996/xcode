package cn.xk.xcode.service.sms;

import cn.xk.xcode.handler.message.HandlerResult;

/**
 * @Author xuk
 * @Date 2025/3/13 14:19
 * @Version 1.0.0
 * @Description SmsService
 **/
public interface ISmsService {

    HandlerResult sendMessage(SmsReqDto smsReqDto);

    SmsPlatEnum type();
}
