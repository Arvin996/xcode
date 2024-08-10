package cn.xk.xcode.enums;

import cn.xk.xcode.core.IntEnumValueToArray;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @Author xuk
 * @Date 2024/8/6 13:56
 * @Version 1.0
 * @Description MemberPointChangeBizTypeEnum
 */
@Getter
@AllArgsConstructor
public enum MemberPointChangeBizTypeEnum implements IntEnumValueToArray
{
    SIGN(1, "签到", "签到获得 {} 积分", true),
//    ADMIN(2, "管理员修改", "管理员修改 {} 积分", true),
//
//    ORDER_USE(11, "订单积分抵扣", "下单使用 {} 积分", false), // 下单时，扣减积分
//    ORDER_USE_CANCEL(12, "订单积分抵扣（整单取消）", "订单取消，退还 {} 积分", true), // ORDER_USE 的取消
//    ORDER_USE_CANCEL_ITEM(13, "订单积分抵扣（单个退款）", "订单退款，退还 {} 积分", true), // ORDER_USE 的取消
//
//    ORDER_GIVE(21, "订单积分奖励", "下单获得 {} 积分", true), // 支付订单时，赠送积分
//    ORDER_GIVE_CANCEL(22, "订单积分奖励（整单取消）", "订单取消，退还 {} 积分", false), // ORDER_GIVE 的取消
//    ORDER_GIVE_CANCEL_ITEM(23, "订单积分奖励（单个退款）", "订单退款，扣除赠送的 {} 积分", false) // ORDER_GIVE 的取消
    ;

    /**
     * 类型
     */
    private final Integer type;
    /**
     * 名字
     */
    private final String name;
    /**
     * 描述
     */
    private final String description;
    /**
     * 是否为扣减积分
     */
    private final boolean add;

    public static MemberPointChangeBizTypeEnum getByType(int type){
        return Arrays.stream(values()).filter(v -> v.getType() == type).findFirst().orElse(null);
    }

    @Override
    public int[] toIntArray() {
        return Arrays.stream(values()).mapToInt(MemberPointChangeBizTypeEnum::getType).toArray();
    }
}
