package cn.xk.xcode.constants;

import cn.xk.xcode.exception.IntErrorCode;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/8/16 9:23
 * @description
 */
public interface TransFlexGlobalConstants {
    IntErrorCode ENUM_ALREADY_EXIST = new IntErrorCode(400_1_500, "枚举类型:{}的key:{}已经存在");
    IntErrorCode CONFIGURER_MUST_UNIQUE = new IntErrorCode(400_1_501, "枚举类配置器必须唯一");
    IntErrorCode TRANS_FAILED = new IntErrorCode(400_1_502, "翻译失败");

}