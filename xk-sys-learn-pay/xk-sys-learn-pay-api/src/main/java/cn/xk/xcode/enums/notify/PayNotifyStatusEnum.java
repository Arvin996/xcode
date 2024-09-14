package cn.xk.xcode.enums.notify;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author xuk
 * @Date 2024/9/12 11:24
 * @Version 1.0.0
 * @Description PayNotifyStatusEnum
 **/
@AllArgsConstructor
@Getter
public enum PayNotifyStatusEnum {

    WAITING(0, "等待通知"),
    SUCCESS(10, "通知成功"),
    FAILURE(20, "通知失败"), // 多次尝试，彻底失败
    REQUEST_SUCCESS(21, "请求成功，但是结果失败"),
    REQUEST_FAILURE(22, "请求失败"),
    ;

    /**
     * 状态
     */
    private final Integer status;
    /**
     * 名字
     */
    private final String name;
}
