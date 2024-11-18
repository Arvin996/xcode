package cn.xk.xcode.entity.dto.merchant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author xuk
 * @Date 2024/9/23 14:59
 * @Version 1.0.0
 * @Description MerchantBaseDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "商户 base dto")
public class MerchantBaseDto {
    @Schema(description = "id")
    private String id;
}
