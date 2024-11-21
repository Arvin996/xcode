package cn.xk.xcode.entity.dto.user;

import cn.xk.xcode.validation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/6/21 20:57
 * @description
 */
@Data
@NoArgsConstructor
@Schema(description = "新增用户dto")
public class AddUserDto
{
    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(min = 8, max = 16, message = "密码长度必须在8-16位之间")
    @Schema(description = "用户密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "用户头像")
    private String userpic;

    @Schema(description = "qq")
    private String qq;

    @Schema(description = "微信")
    private String wx;

    @Schema(description = "手机号")
    @Mobile
    private String mobile;

}
