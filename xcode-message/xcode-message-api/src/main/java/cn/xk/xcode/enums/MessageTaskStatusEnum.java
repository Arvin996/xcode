package cn.xk.xcode.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author xuk
 * @Date 2025/3/13 16:47
 * @Version 1.0.0
 * @Description MessageTaskStatusEnum
 **/
@AllArgsConstructor
@Getter
public enum MessageTaskStatusEnum {

    WAITING("00"),
    PART_SUCCESS("10"),
    FAIL("20"),
    ALL_SUCCESS("30"),
    CANCEL("40");

    private final String status;
}
