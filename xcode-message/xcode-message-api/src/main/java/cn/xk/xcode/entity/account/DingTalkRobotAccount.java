package cn.xk.xcode.entity.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2025/5/14 16:54
 * @Version 1.0.0
 * @Description DingTalkRobotAccount
 **/
@Data
@Schema(name = "DingTalkRobotAccount", description = "DingTalk 机器人 webhook账户")
public class DingTalkRobotAccount {

    @Schema(description = "token")
    @NotBlank(message = "token 不能为空")
    private String token;

    @Schema(description = "sign 签名")
    private String sign;
}
