package cn.xk.xcode.entity.dto.user;

import cn.xk.xcode.typehandler.SexTypeHandler;
import cn.xk.xcode.validation.Mobile;
import com.mybatisflex.annotation.Column;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/10/30 15:27
 * @Version 1.0.0
 * @Description RegisterUserDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "用户注册dto")
public class RegisterUserDto {

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    @NotBlank(message = "姓名不能为空")
    private String name;

    @Schema(description = "验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    @Length(min = 10, max = 16, message = "密码长度必须为6-16位")
    private String password;

    @Schema(description = "确认密码")
    @NotBlank(message = "确认密码不能为空")
    @Length(min = 10, max = 16, message = "确认密码长度必须为6-16位")
    private String confirmPassword;

    /**
     * 角色id
     */
    @Schema(description = "角色id")
    @NotNull(message = "角色id不能为空")
    private Integer roleId;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @Mobile
    private String mobile;

    /**
     * 性别 0 男 1 女
     */
    @Schema(description = "性别 0 男 1 女")
    @NotNull(message = "性别不能为空")
    @Column(typeHandler = SexTypeHandler.class)
    private String sex;

    /**
     * 身份证号
     */
    @Schema(description = "身份证号")
    private String idNumber;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;
}
