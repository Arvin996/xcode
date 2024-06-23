package cn.xk.xcode.entity.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/6/22 16:23
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户登录")
public class LoginUserDto
{
    @NotNull
    @Schema(description = "用户名", example = "xukai")
    private String username;

    @NotNull
    @Schema(description = "密码", example = "123456789")
    private String password;

}
