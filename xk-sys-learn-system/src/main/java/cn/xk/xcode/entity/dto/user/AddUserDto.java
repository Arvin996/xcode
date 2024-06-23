package cn.xk.xcode.entity.dto.user;

import cn.xk.xcode.validation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

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
    @NotNull
    private String username;

    /**
     * 用户登录密码
     */
    @NotNull
    @Length(min = 8, max = 16)
    @Schema(description = "用户密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickname;

    /**
     * 用户头像
     */
    @Schema(description = "用户头像")
    private String userpic;

    /**
     * qq
     */
    @Schema(description = "qq")
    private String qq;

    /**
     * 微信
     */
    @Schema(description = "微信")
    private String wx;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @Mobile
    private String mobile;

}
