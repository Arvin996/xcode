package cn.xk.xcode.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author xuk
 * @Date 2024/7/31 19:00
 * @Version 1.0
 * @Description CaptchaGenResultVo
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "验证码返回dto")
public class CaptchaGenResultVo {

    @Schema(description = "图片base64验证码")
    private String picCode;

    @Schema(description = "uuid")
    private String uuid;
}
