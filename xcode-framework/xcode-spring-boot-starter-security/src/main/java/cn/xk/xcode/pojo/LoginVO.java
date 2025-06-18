package cn.xk.xcode.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(description = "通用用户返回")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginVO {

    @Schema(description = "access_token")
    private String accessToken;

    @Schema(description = "授权令牌 access_token 的有效期")
    private Long expireIn;

    @Schema(description = "用户信息")
    private Object UserInfo;

    @Schema(description = "是否需要验证码")
    private boolean requireCaptcha = false;

}