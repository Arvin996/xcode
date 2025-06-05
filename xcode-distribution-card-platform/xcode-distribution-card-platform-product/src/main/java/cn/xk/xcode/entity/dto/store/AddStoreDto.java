package cn.xk.xcode.entity.dto.store;

import cn.xk.xcode.validation.Qq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2025/6/4 9:33
 * @Version 1.0.0
 * @Description AddStoreDto
 **/
@Data
@Schema(name = "AddStoreDto", description = "AddStoreDto 店铺添加dto")
public class AddStoreDto {

    /**
     * 所属商户id
     */
    @Schema(description = "merchantId 所属商户id")
    @NotNull(message = "所属商户id不能为空")
    private Long merchantId;

    /**
     * 店铺名称
     */
    @Schema(description = "name 店铺名称")
    @NotBlank(message = "店铺名称不能为空")
    private String name;

    /**
     * 店铺简介
     */
    @Schema(description = "desc 店铺简介")
    @NotBlank(message = "店铺简介不能为空")
    private String desc;

    /**
     * 店铺logo
     */
    @Schema(description = "logo 店铺logo")
    @NotBlank(message = "店铺logo不能为空")
    private String logo;

    /**
     * 店铺客服qq
     */
    @Qq
    @Schema(description = "contactQq 店铺客服qq")
    private String contactQq;

    /**
     * 店铺客户微信
     */
    @Schema(description = "contactWx 店铺客户微信")
    private String contactWx;
}
