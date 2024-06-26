package cn.xk.xcode.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema(description = "用户登录实体类")
public class LoginInfoDto {

    @NotNull
    @Schema(description = "用户名")
    private String username;

    @NotNull
    @Schema(description = "密码")
    private String password;

    @Schema(description = "登录设备")
    private String loginDevType;

    @NotNull
    @Schema(description = "客户端id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String clientId;

    @Schema(description = "授权类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private String grantType;

    @Schema(description = "微信小程序登录code")
    private String code;

}