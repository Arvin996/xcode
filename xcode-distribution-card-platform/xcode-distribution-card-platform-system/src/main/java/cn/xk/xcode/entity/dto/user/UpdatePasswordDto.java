package cn.xk.xcode.entity.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2025/5/13 8:32
 * @Version 1.0.0
 * @Description UpdatePasswordDto
 **/
@Data
@Schema(name = "UpdatePasswordDto", description = "UpdatePasswordDto 用户密码更新dto")
public class UpdatePasswordDto {

    /**
     * 登录用户名
     */
    @Schema(description = "username 登录用户名")
    @NotBlank(message = "username 不能为空")
    private String username;

    /**
     * 旧密码
     */
    @Schema(description = "oldPassword 旧密码")
    @NotBlank(message = "oldPassword 不能为空")
    private String oldPassword;

    /**
     * 新密码
     */
    @Schema(description = "newPassword 新密码")
    @NotBlank(message = "newPassword 不能为空")
    private String newPassword;

    /**
     * 确认密码
     */
    @Schema(description = "confirmPassword 确认密码")
    @NotBlank(message = "confirmPassword 不能为空")
    private String confirmPassword;
}
