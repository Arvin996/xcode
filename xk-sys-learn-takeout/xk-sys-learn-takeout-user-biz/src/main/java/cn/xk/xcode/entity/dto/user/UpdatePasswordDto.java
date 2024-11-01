package cn.xk.xcode.entity.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/10/30 14:39
 * @Version 1.0.0
 * @Description UpdatePasswordDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "新增指定角色用户dto 管理员接口")
public class UpdatePasswordDto {

    @NotNull(message = "用户id不能为空")
    @Schema(description = "用户id")
    private Long Id;

    @NotNull(message = "旧密码不能为空")
    @Schema(description = "旧密码")
    private String oldPassword;

    @NotNull(message = "新密码不能为空")
    @Length(min = 10, max = 16, message = "密码长度必须为10-16位")
    @Schema(description = "新密码")
    private String newPassword;
}
