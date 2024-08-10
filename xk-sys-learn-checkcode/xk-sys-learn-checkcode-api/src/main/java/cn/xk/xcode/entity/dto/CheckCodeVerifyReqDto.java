package cn.xk.xcode.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/7/31 19:02
 * @Version 1.0
 * @Description CheckCodeVerifyReqDto 验证码验证
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "验证码校验dto")
public class CheckCodeVerifyReqDto
{
    @NotNull
    @Schema(description = "验证码")
    private String code;

    @NotNull
    @Schema(description = "生成类型")
    private String type;
}
