package cn.xk.xcode.entity.dto.user;

import cn.xk.xcode.validation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2024/10/30 14:55
 * @Version 1.0.0
 * @Description UpdateUserMobileDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "更新用户 dto")
public class UpdateUserMobileDto extends UserBaseDto{

    @NotBlank(message = "验证码不能为空")
    @Schema(description = "验证码")
    private String code;

    @Mobile
    @Schema(description = "新手机号")
    private String newMobile;
}
