package cn.xk.xcode.enums;

import cn.xk.xcode.core.annotation.StringEnumValueToArray;
import cn.xk.xcode.entity.discard.content.*;
import cn.xk.xcode.utils.collections.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Author xuk
 * @Date 2025/3/10 15:47
 * @Version 1.0.0
 * @Description ChannelTypeEnum
 **/
@AllArgsConstructor
@Getter
public enum ChannelTypeEnum implements StringEnumValueToArray {

    /**
     * getui
     */
    GE_TUI("ge_tui", "ge_tui(个推)", GeTuiContentModel.class),

    /**
     * sms(短信)  -- 腾讯云、云片
     */
    SMS("sms", "sms短信服务", SmsContentModel.class),
    /**
     * email(邮件) -- QQ、163邮箱
     */
    EMAIL("email", "email邮件服务", EmailContentModel.class),
    /**
     * officialAccounts(微信服务号) --
     * accessToken 交由 weixin-java-mp 组件管理
     */
    OFFICIAL_ACCOUNT("official_accounts", "officialAccounts", OfficialAccountsContentModel.class),
    /**
     * miniProgram(微信小程序)
     * accessToken 交由 weixin-java-miniapp 组件管理
     */
    MINI_PROGRAM("wx_miniProgram", "wx_miniProgram(小程序)", MiniProgramContentModel.class),

    /**
     * enterpriseWeChat(企业微信)
     */
    ENTERPRISE_WE_CHAT("enterpriseWeChat", "enterpriseWeChat(企业微信)", EnterpriseWeChatContentModel.class),
    /**
     * dingDingRobot(钉钉机器人)
     */
    DING_DING_ROBOT("dingDingRobot", "dingDingRobot(钉钉机器人)", DingDingRobotContentModel.class),
    /**
     * dingDingWorkNotice(钉钉工作通知)
     */
    DING_DING_WORK_NOTICE("dingDingWorkNotice", "dingDingWorkNotice(钉钉工作通知)", DingDingWorkContentModel.class),
    /**
     * enterpriseWeChat(企业微信机器人)
     */
    ENTERPRISE_WE_CHAT_ROBOT("enterpriseWeChat", "enterpriseWeChat(企业微信机器人)", EnterpriseWeChatRobotContentModel.class),
    /**
     * feiShuRoot(飞书机器人)
     */
    FEI_SHU_ROBOT("feiShuRoot", "feiShuRoot(飞书机器人)", FeiShuRobotContentModel.class),
    /**
     * alipayMiniProgram(支付宝小程序)
     */
    ALIPAY_MINI_PROGRAM("alipayMiniProgram", "alipayMiniProgram(支付宝小程序)", AlipayMiniProgramContentModel.class);
    ;

    private final String code;
    private final String desc;
    private final Class<? extends ContentModel> contentModelClass;

    @Override
    public String[] toArrayString() {
        return ArrayUtil.toArray(Arrays.stream(values()).map(ChannelTypeEnum::getCode).collect(Collectors.toList()));
    }
}
