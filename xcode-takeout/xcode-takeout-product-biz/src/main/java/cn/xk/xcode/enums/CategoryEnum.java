package cn.xk.xcode.enums;

import cn.xk.xcode.core.IntEnumValueToArray;
import cn.xk.xcode.utils.collections.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * @Author xuk
 * @Date 2024/11/1 16:26
 * @Version 1.0.0
 * @Description CategoryEnum
 **/
@AllArgsConstructor
@Getter
public enum CategoryEnum implements IntEnumValueToArray {

    DISH_CATEGORY(1, "菜品"),
    SETMEAL_CATEGORY(2, "套餐");
    private final Integer type;
    private final String name;

    @Override
    public int[] toIntArray() {
        return Arrays.stream(values()).mapToInt(CategoryEnum::getType).toArray();
    }

    public static String getTypeName(Integer type){
        Optional<CategoryEnum> optional = Arrays.stream(values()).filter(v -> v.getType().equals(type)).findFirst();
        return optional.map(CategoryEnum::getName).orElse(null);
    }
}
