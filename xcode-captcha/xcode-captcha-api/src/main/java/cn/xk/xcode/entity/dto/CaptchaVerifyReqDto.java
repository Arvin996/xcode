package cn.xk.xcode.entity.dto;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.enums.CaptchaGenerateType;
import cn.xk.xcode.validation.Email;
import cn.xk.xcode.validation.InStrEnum;
import cn.xk.xcode.validation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/7/31 19:02
 * @Version 1.0
 * @Description CaptchaVerifyReqDto 验证码验证
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "验证码校验dto")
public class CaptchaVerifyReqDto {

    @Schema(description = "uuid 图形验证需要上送")
    private String uuid;

    @NotBlank(message = "验证码不能为空")
    @Schema(description = "验证码", example = "1234")
    private String code;

    @NotNull(message = "验证码生成类型不能为空")
    @Schema(description = "生成类型", example = "email")
    @InStrEnum(CaptchaGenerateType.class)
    private String type;

    @Email
    @Schema(description = "邮箱", example = "1347459620@qq.com")
    private String email;

    @Mobile
    @Schema(description = "手机号", example = "13027102413")
    private String mobile;

    public boolean isInvalid() {
        return StrUtil.isBlank(email) && StrUtil.isBlank(mobile);
    }
}
