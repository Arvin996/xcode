package cn.xk.xcode.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * @Author xuk
 * @Date 2024/11/8 10:48
 * @Version 1.0.0
 * @Description DelayLevelEnum
 **/
@AllArgsConstructor
@Getter
public enum DelayLevelEnum {

    LEVEL1(1, 1, TimeUnit.SECONDS),
    LEVEL2(2, 5, TimeUnit.SECONDS),
    LEVEL3(3, 10, TimeUnit.SECONDS),
    LEVEL4(4, 30, TimeUnit.SECONDS),
    LEVEL5(5, 60, TimeUnit.SECONDS),
    LEVEL6(6, 2 * 60, TimeUnit.SECONDS),
    LEVEL7(7, 3 * 60, TimeUnit.SECONDS),
    LEVEL8(8, 4 * 60, TimeUnit.SECONDS),
    LEVEL9(9, 5 * 60, TimeUnit.SECONDS),
    LEVEL10(10, 6 * 60, TimeUnit.SECONDS),
    LEVEL11(11, 7 * 60, TimeUnit.SECONDS),
    LEVEL12(12, 8 * 60, TimeUnit.SECONDS),
    LEVEL13(13, 9 * 60, TimeUnit.SECONDS),
    LEVEL14(14, 10 * 60, TimeUnit.SECONDS),
    LEVEL15(15, 20 * 60, TimeUnit.SECONDS),
    LEVEL16(16, 30 * 60, TimeUnit.SECONDS),
    LEVEL17(17, 60 * 60, TimeUnit.SECONDS),
    LEVEL18(18, 2 * 60 * 60, TimeUnit.SECONDS),
    ;
    private final Integer level;
    private final Integer time;
    private final TimeUnit timeUnit;
}
