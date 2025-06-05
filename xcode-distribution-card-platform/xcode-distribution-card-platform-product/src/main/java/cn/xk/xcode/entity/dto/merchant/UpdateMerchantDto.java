package cn.xk.xcode.entity.dto.merchant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author xuk
 * @Date 2025/6/3 8:59
 * @Version 1.0.0
 * @Description UpdateMerchantDto
 **/
@Data
@Schema(name = "UpdateMerchantDto", description = "UpdateMerchantDto 商家信息更新dto")
public class UpdateMerchantDto {

    @Schema(description = "id 商家id")
    private Long id;

    /**
     * 商户昵称
     */
    private String nickname;

    /**
     * 商户头像
     */
    private String avatar;
}
