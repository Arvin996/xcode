package cn.xk.xcode.entity.dto.user;

import cn.xk.xcode.validation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * @Author Administrator
 * @Date 2024/8/29 14:31
 * @Version V1.0.0
 * @Description MemberUserBandingMobileDto
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会员用户修改密码 dto")
public class MemberUserBindMobileDto extends MemberUserBaseDto{
    @Schema(description = "mobile")
    @Mobile
    private String mobile;

    @Schema(description = "email")
    @NotBlank(message = "验证码不能为空")
    private String code;
}
