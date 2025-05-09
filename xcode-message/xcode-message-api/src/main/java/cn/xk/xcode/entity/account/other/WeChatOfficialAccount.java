package cn.xk.xcode.entity.account.other;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 模板消息参数
 * <p>
 * 参数示例：
 * <a href="https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Template_Message_Interface.html">...</a>
 * * @author zyg
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeChatOfficialAccount {

    /**
     * 账号相关
     */
    private String appId;
    private String secret;
    private String token;

}
