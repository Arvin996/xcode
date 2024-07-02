package com.xk.xcode.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/7/2 18:12
 * @description
 */
@AllArgsConstructor
@Getter
public enum AreaTypeEnum
{
    COUNTRY(1, "国家"),
    PROVINCE(2, "省份"),
    CITY(3, "城市"),
    DISTRICT(4, "地区"), // 县、镇、区等
    ;

    private final Integer type;

    private final String name;
}
