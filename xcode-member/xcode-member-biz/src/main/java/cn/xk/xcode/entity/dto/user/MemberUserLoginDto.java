package cn.xk.xcode.entity.dto.user;


import cn.xk.xcode.service.auth.enums.LoginTypeEnum;
import cn.xk.xcode.validation.Email;
import cn.xk.xcode.validation.InStrEnum;
import cn.xk.xcode.validation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

/**
 * @Author Administrator
 * @Date 2024/8/27 15:51
 * @Version V1.0.0
 * @Description MemberUserLoginDto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会员用户登录dto")
public class MemberUserLoginDto {

    @Mobile
    @Schema(description = "用户手机号")
    private String mobile;

    @Email
    @Schema(description = "用户邮箱")
    private String email;

    @Schema(description = "用户账号")
    private String account;

    @Schema(description = "用户密码")
    private String password;

    @Schema(description = "验证码")
    private String code;

    @InStrEnum(value = LoginTypeEnum.class)
    @Schema(description = "登录类型")
    @NotBlank(message = "登录类型不能为空")
    private String loginType;

    @Schema(description = "登录设备")
    private String loginDevice;
}
