package cn.xk.xcode.entity.dto.user;

import cn.xk.xcode.entity.dto.CaptchaVerifyReqDto;
import cn.xk.xcode.validation.Email;
import cn.xk.xcode.validation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2025/5/9 10:50
 * @Version 1.0.0
 * @Description RegisterUserDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "RegisterUserDto 用户注册实体类")
public class RegisterUserDto extends CaptchaVerifyReqDto {

    /**
     * 登录用户名
     */
    @NotBlank(message = "登录用户名不能为空")
    @Length(min = 8, max = 16, message = "登录用户名长度必须再8-16之间")
    @Schema(description = "username 登录用户名", example = "xuk")
    private String username;

    /**
     * 登录密码
     */
    @NotBlank(message = "登录密码不能为空")
    @Schema(description = "password 登录密码", example = "123456")
    private String password;

    @NotNull(message = "角色id不能为空")
    @Schema(description = "roleId 角色id", example = "1")
    private Integer roleId;

    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码不能为空")
    @Schema(description = "confirmPassword 确认密码", example = "123456")
    private String confirmPassword;

    /**
     * 用户昵称
     */
    @Schema(description = "nickname 用户昵称", example = "xuk")
    private String nickname;

    /**
     * 0 男  1女 2未知
     */
    @Schema(description = "sex 性别", example = "2")
    private String sex = "2";

    /**
     * 年龄
     */
    @Schema(description = "age 年龄", example = "20")
    private Integer age;

    /**
     * 手机号
     */
    @Mobile
    @Schema(description = "mobile 手机号", example = "18888888888")
    private String mobile;

    @Email
    @Schema(description = "email 邮箱", example = "1247459620@qq.com")
    private String email;
}
