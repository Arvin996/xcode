package cn.xk.xcode.enums;

import cn.xk.xcode.core.annotation.IntEnumValueToArray;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @Author xuk
 * @Date 2024/11/21 13:27
 * @Version 1.0.0
 * @Description LeaveTypeEnum
 **/
@AllArgsConstructor
@Getter
public enum LeaveTypeEnum implements IntEnumValueToArray {

    // 事假
    SICK_LEAVE(1,"事假", "type_one_leave"),
    // 年假
    ANNUAL_LEAVE(2,"年假", "type_two_leave"),
    // 调休
    REST_DAY(3,"调休", "type_one_leave"),
    // 婚假
    WEDDING_LEAVE(4,"婚假", "type_two_leave"),
    // 产假
    MATERNITY_LEAVE(5,"产假", "type_two_leave"),
    // 丧假
    FUNERAL_LEAVE(6,"丧假", "type_two_leave"),
    // 病假
    VISITING_LEAVE(7,"病假", "type_one_leave");
    private final Integer type;
    private final String name;
    private final String flowCode;

    @Override
    public int[] toIntArray() {
        return Arrays.stream(values()).mapToInt(LeaveTypeEnum::getType).toArray();
    }

    public static String getNameByType(Integer type) {
        for (LeaveTypeEnum leaveTypeEnum : values()) {
            if (leaveTypeEnum.getType().equals(type)) {
                return leaveTypeEnum.getName();
            }
        }
        return null;
    }

    public static Integer getTypeByName(String name) {
        for (LeaveTypeEnum leaveTypeEnum : values()) {
            if (leaveTypeEnum.getName().equals(name)) {
                return leaveTypeEnum.getType();
            }
        }
        return null;
    }

    public static String getFlowCodeByType(Integer type) {
        for (LeaveTypeEnum leaveTypeEnum : values()) {
            if (Objects.equals(leaveTypeEnum.getType(), type)) {
                return leaveTypeEnum.getFlowCode();
            }
        }
        return null;
    }
}
