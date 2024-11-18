package cn.xk.xcode.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author xuk
 * @Date 2024/9/3 19:21
 * @Version 1.0.0
 * @Description PayRefundStatusEnums
 **/
@AllArgsConstructor
@Getter
public enum PayRefundStatusEnums {

    REFUND_WAITING(0, "等待退款"),
    REFUND_SUCCESS(10, "退款成功"),
    REFUND_FAILURE(20, "退款失败");

    private final Integer status;
    private final String name;

    public static Boolean isSuccess(Integer status) {
        return REFUND_SUCCESS.getStatus().equals(status);
    }

    public static Boolean isFailure(Integer status) {
        return REFUND_FAILURE.getStatus().equals(status);
    }
}
