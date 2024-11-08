package cn.xk.xcode;

import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.IntErrorCode;

/**
 * @Author xuk
 * @Date 2024/11/6 16:33
 * @Version 1.0.0
 * @Description GlobalTakeoutTradeConstants
 **/
public interface GlobalTakeoutTradeConstants {

    ErrorCode SHOPPING_CART_PARAMS_INVALID = new IntErrorCode(114_0_500, "购物车参数非法，菜品和套餐id同时为空");
    ErrorCode OPERATOR_USER_NOT_EXISTS = new IntErrorCode(114_0_501, "操作用户不存在");
    ErrorCode OPERATOR_USER_IS_DISABLED = new IntErrorCode(114_0_503, "操作用户已被封禁，请联系管理员");
    ErrorCode DISH_NOT_EXISTS = new IntErrorCode(114_0_504, "菜品不存在");
    ErrorCode SETMEAL_NOT_EXISTS = new IntErrorCode(114_0_505, "套餐不存在");
    ErrorCode SHOPPING_CART_ITEM_HAS_DELETED = new IntErrorCode(114_0_506, "购物车商品已经删除");
    ErrorCode USER_ADDRESS_NOT_EXISTS = new IntErrorCode(114_0_507, "用户地址不存在");
    ErrorCode SHOPPING_CART_NOT_CONTAINS_ITEM = new IntErrorCode(114_0_508, "购物车不包含商品");
    ErrorCode ORDER_NOT_EXISTS =  new IntErrorCode(114_0_509, "订单不存在");
    ErrorCode ORDER_IS_NOT_WAITING_APY =  new IntErrorCode(114_0_510, "订单不是待支付状态,无法取消");
    ErrorCode ORDER_STATUS_IS_NOT_WAITING_PAY =  new IntErrorCode(114_0_511, "订单状态不是待支付状态, 请刷新重新支付");
}
