package cn.xk.xcode.constant;

import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.IntErrorCode;

/**
 * @Author xuk
 * @Date 2024/11/1 16:36
 * @Version 1.0.0
 * @Description GlobalTakeoutProductConstants
 **/
public interface GlobalTakeoutProductConstants {

    ErrorCode CATEGORY_HAS_ALREADY_EXISTS = new IntErrorCode(1100_1_500, "%s分类%s已经存在");
    ErrorCode CATEGORY_HAS_USED = new IntErrorCode(1100_1_501, "该分类已经被使用， 删除失败");
    ErrorCode FLAVOR_HAS_ALREADY_EXISTS = new IntErrorCode(1100_1_502, "该口味已经存在");
    ErrorCode FLAVOR_PROPERTY_HAS_EXISTS = new IntErrorCode(1100_1_503, "口味{}的属性{}重复");
    ErrorCode FLAVOR_HAS_USED = new IntErrorCode(1100_1_504, "该口味已经被使用， 删除失败");
    ErrorCode DISH_HAS_ALREADY_EXISTS = new IntErrorCode(1100_1_505, "该菜品已经存在");
    ErrorCode CATEGORY_NOT_EXISTS =  new IntErrorCode(1100_1_506, "分类不存在");
    ErrorCode DISH_HAS_BIND_SETMEAL =  new IntErrorCode(1100_1_507, "该菜品已经被绑定套餐");
    ErrorCode DISH_HAS_DELETED =  new IntErrorCode(1100_1_508, "该菜品已经被删除");
    ErrorCode DISH_STOCK_IS_REDUCING =  new IntErrorCode(1100_1_509, "该菜品库存正在减少中, 请稍后再试");
    ErrorCode DISH_STOCK_IS_NOT_ENOUGH =  new IntErrorCode(1100_1_510, "该菜品库存不足");
    ErrorCode SETMEAL_STOCK_IS_NOT_ENOUGH =  new IntErrorCode(1100_1_511, "该套餐库存不足");
    ErrorCode SETMEAL_HAS_ALREADY_EXISTS =  new IntErrorCode(1100_1_512, "该套餐已经存在");
    ErrorCode SETMEAL_HAS_DELETED =  new IntErrorCode(1100_1_513, "该套餐已经被删除");
}
