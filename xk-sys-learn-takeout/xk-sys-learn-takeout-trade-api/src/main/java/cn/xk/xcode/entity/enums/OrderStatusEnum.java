package cn.xk.xcode.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author xuk
 * @Date 2024/11/8 13:42
 * @Version 1.0.0
 * @Description OrderStatusEnum
 **/
@AllArgsConstructor
@Getter
public enum OrderStatusEnum {

    WAITING_PAY(1, "等待付款"),
    WAITING_SEND(2, "待派送"),
    ALREADY_SEND(3, "已派送"),
    SUCCESS(4, "已完成"),
    CANCEL(5, "已取消");

    private final Integer status;
    private final String name;

    public static boolean isWaitingPay(Integer status) {
        return WAITING_PAY.status.equals(status);
    }
}
