package cn.xk.xcode.entity.dto.store;

import cn.xk.xcode.validation.Qq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2025/6/4 10:57
 * @Version 1.0.0
 * @Description UpdateStoreDto
 **/
@Data
@Schema(name = "UpdateStoreDto", description = "UpdateStoreDto 店铺更新dto")
public class UpdateStoreDto {

    @Schema(description = "id 店铺id")
    @NotNull(message = "店铺id不能为空")
    private Long id;

    /**
     * 店铺简介
     */
    @Schema(description = "desc 店铺简介")
    @NotBlank(message = "店铺简介不能为空")
    private String desc;

    /**
     * 店铺客服qq
     */
    @Qq
    private String contactQq;

    /**
     * 店铺客户微信
     */
    @Schema(description = "contactWx 店铺客户微信")
    private String contactWx;
}
