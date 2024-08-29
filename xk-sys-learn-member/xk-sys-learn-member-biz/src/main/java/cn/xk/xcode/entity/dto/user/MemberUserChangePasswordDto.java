package cn.xk.xcode.entity.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * @Author Administrator
 * @Date 2024/8/29 14:04
 * @Version V1.0.0
 * @Description MemberUserChangePassword
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会员用户修改密码 dto")
public class MemberUserChangePasswordDto extends MemberUserBaseDto {

    @NotBlank(message = "旧密码不能为空")
    @Schema(description = "旧密码")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    @Schema(description = "新密码")
    private String newPassword;

}
