package cn.xk.xcode.entity.dto.user;

import cn.xk.xcode.service.auth.enums.RegisterTypeEnum;
import cn.xk.xcode.typehandler.ListIntTypeHandler;
import cn.xk.xcode.validation.Email;
import cn.xk.xcode.validation.InStrEnum;
import cn.xk.xcode.validation.Mobile;
import com.mybatisflex.annotation.Column;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Author Administrator
 * @Date 2024/8/27 11:28
 * @Version V1.0.0
 * @Description MemberUserRegisterDto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会员用户注册dto")
public class MemberUserRegisterDto {

    @NotBlank
    @Schema(description = "注册类型 mobile手机注册 email邮箱注册", example = "mobile")
    @InStrEnum(RegisterTypeEnum.class)
    private String registerType;

    @Mobile
    @Schema(description = "用户手机号")
    private String mobile;

    @Email
    @Schema(description = "用户邮箱")
    private String email;

    @NotBlank(message = "用户密码不能为空")
    @Length(min = 8, max = 16, message = "用户密码长度必须为8-16位")
    @Schema(description = "用户密码")
    private String password;

    @NotBlank(message = "用户确认密码不能为空")
    @Length(min = 8, max = 16, message = "用户确认密码长度必须为8-16位")
    @Schema(description = "用户确认密码")
    private String confirmPassword;

    @Schema(description = "验证码")
    private String code;

    @Schema(description = "用户昵称")
    @NotBlank(message = "用户昵称不能为空")
    private String nickname;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "用户标签")
    @Column(typeHandler = ListIntTypeHandler.class)
    private List<Integer> tagIds;

    @Schema(description = "用户分组")
    private Integer groupId;

    @Schema(description = "用户性别")
    private String sex;

    @Schema(description = "用户生日")
    private String birthday;
}
