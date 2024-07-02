package cn.xk.xcode.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/7/2 20:02
 * @description
 */
@Getter
@AllArgsConstructor
public enum LogType
{
    BIZ_LOG("biz"),
    GATEWAY_LOG("gw");
    private final String type;
}
