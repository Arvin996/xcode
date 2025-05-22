package cn.xk.xcode.entity.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2025/5/9 16:46
 * @Version 1.0.0
 * @Description LoginUserDto
 **/
@Data
@Schema(name = "LoginUserDto 登录用户实体类")
public class LoginUserDto {

    @NotBlank(message = "用户名不能未空")
    @Schema(description = "username 用户名", example = "xuk")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Schema(description = "password 密码", example = "123456")
    private String password;

  //  @NotBlank(message = "验证码不能为空")
    @Schema(description = "code 验证码", example = "123456")
    private String code;

    @Schema(description = "登录设备")
    private String loginDevType = "browser";
}
