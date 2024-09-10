package cn.xk.xcode.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author xuk
 * @Date 2024/9/3 16:33
 * @Version 1.0.0
 * @Description PayOrderStatusEnums
 **/
@AllArgsConstructor
@Getter
public enum PayOrderStatusEnums {

    PAY_WAITING(0, "未支付"),
    PAY_SUCCESS(10, "支付成功"),
    PAY_REFUND(20, "已退款"),
    PAY_CLOSED(30, "支付关闭"),
    ;
    private final Integer status;
    private final String name;

    public static Boolean isSuccess(Integer status){
        return Objects.equals(status, PAY_SUCCESS.getStatus());
    }

    public static Boolean isRefund(Integer status){
        return Objects.equals(status, PAY_REFUND.getStatus());
    }

    public static Boolean isClosed(Integer status){
        return Objects.equals(status, PAY_CLOSED.getStatus());
    }

    public static boolean isValid(Integer status){
        return Arrays.stream(values()).map(PayOrderStatusEnums::getStatus).collect(Collectors.toList()).contains(status);
    }

}
