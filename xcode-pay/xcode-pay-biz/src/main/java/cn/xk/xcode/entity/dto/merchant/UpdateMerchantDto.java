package cn.xk.xcode.entity.dto.merchant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2024/9/23 15:02
 * @Version 1.0.0
 * @Description UpdateMerchantDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "商户修改 dto")
public class UpdateMerchantDto extends MerchantBaseDto{

    /**
     * 商户名称
     */
    @Schema(description = "商户名称")
    @NotBlank(message = "商户名称不能为空")
    private String merchantName;

    /**
     * 商户简称
     */
    @Schema(description = "商户简称")
    @NotBlank(message = "商户简称不能为空")
    private String merchantShortName;

    /**
     * 商户备注
     */
    @Schema(description = "商户备注")
    private String remark;

    /**
     * 商户状态 0 开启 1关闭
     */
    @Schema(description = "商户状态 0 开启 1关闭")
    @NotBlank(message = "商户状态不能为空")
    private String status;
}
