package cn.xk.xcode.entity.dto.user;

import cn.xk.xcode.enums.LoginTypeEnum;
import cn.xk.xcode.validation.InStrEnum;
import cn.xk.xcode.validation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2024/10/30 15:54
 * @Version 1.0.0
 * @Description UserLoginDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "用户注册dto")
public class UserLoginDto {

    @Schema
    @NotBlank(message = "手机号不能为空")
    @Mobile
    private String mobile;

    @Schema(description = "验证码")
    private String code;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "登录类型")
    @InStrEnum(LoginTypeEnum.class)
    private String loginType;

    @Schema(description = "登录设备")
    private String loginDevice;
}
