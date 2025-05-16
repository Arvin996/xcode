package cn.xk.xcode.entity.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author xuk
 * @Date 2025/5/15 10:09
 * @Version 1.0.0
 * @Description WxMiniProgramAccount
 **/
@Data
@Schema(name = "WxMiniProgramAccount", description = "微信小程序账户")
public class WxMiniProgramAccount {

    @Schema(description = "appId")
    private String appId;

    @Schema(description = "appSecret")
    private String appSecret;

}
