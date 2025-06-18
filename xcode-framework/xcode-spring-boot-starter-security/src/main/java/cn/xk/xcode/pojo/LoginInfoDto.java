package cn.xk.xcode.pojo;

import cn.xk.xcode.config.LoginValidationGroupsConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "用户登录实体类")
@Builder
public class LoginInfoDto {

    @NotBlank(groups = {LoginValidationGroupsConfig.PASSWORD_LOGIN.class})
    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户名")
    @NotBlank(groups = {LoginValidationGroupsConfig.EMAIL_LOGIN.class})
    private String email;

    @NotBlank(groups = {LoginValidationGroupsConfig.PASSWORD_LOGIN.class})
    @Schema(description = "密码")
    private String password;

    @Schema(description = "登录设备")
    private String loginDevType;

    // @NotNull
    @Schema(description = "客户端id")
    private String clientId;

    // @NotNull
    @Schema(description = "授权类型")
    private String grantType;

    // @NotNull(groups = {LoginValidationGroupsConfig.WX_LOGIN.class})
    @Schema(description = "微信小程序登录oauth授权返回的code或者验证码")
    private String code;

}