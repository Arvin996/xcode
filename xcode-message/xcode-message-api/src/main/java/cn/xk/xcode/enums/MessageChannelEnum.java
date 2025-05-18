package cn.xk.xcode.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xukai
 * @version 1.0
 * @date 2025/5/18 12:02
 * @description MessageChannelEnum
 */
@AllArgsConstructor
@Getter
public enum MessageChannelEnum {
    WX_MINI_PROGRAM("wx_miniProgram", "微信小程序"),
    WX_OFFICE("wx_office", "微信公众号"),
    DING_DING_ROBOT("dingDingRobot", "钉钉机器人"),
    FEI_SHU_ROBOT("feiShuRoot", "飞书机器人"),
    EMAIL("email", "邮件"),
    ALIYUN_SMS("aliyun_sms", "阿里云短信");
    private final String code;
    private final String name;

}
