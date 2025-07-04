package cn.xk.xcode.entity.discard.account.other;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 企业微信 机器人 账号信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseWeChatRobotAccount {
    /**
     * 自定义群机器人中的 webhook
     */
    private String webhook;

}
