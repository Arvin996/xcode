package cn.xk.xcode.enums.notify;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author xuk
 * @Date 2024/9/12 11:17
 * @Version 1.0.0
 * @Description PayNotifyTypeEnum
 **/
@AllArgsConstructor
@Getter
public enum PayNotifyTypeEnum {

    ORDER("order", "支付异步通知"),
    REFUND("refund", "退费异步通知");

    private final String type;
    private final String name;
}
