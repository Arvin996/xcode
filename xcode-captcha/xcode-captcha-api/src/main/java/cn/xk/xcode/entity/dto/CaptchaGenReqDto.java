package cn.xk.xcode.entity.dto;

import cn.xk.xcode.enums.CaptchaGenerateType;
import cn.xk.xcode.validation.InStrEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2024/7/31 19:01
 * @Version 1.0
 * @Description CaptchaGenReqDto
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "生成验证码dto")
public class CaptchaGenReqDto
{
    @NotBlank(message = "验证码生成类型不能为空")
    @Schema(description = "生成类型", example = "email")
    @InStrEnum(CaptchaGenerateType.class)
    private String type;

    @Schema(description = "邮箱", example = "1347459620@qq.com")
    private String email;

    @Schema(description = "手机号", example = "13027102413")
    private String mobile;
}
