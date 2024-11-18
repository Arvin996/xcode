package cn.xk.xcode.entity.dto.merchant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2024/9/23 14:40
 * @Version 1.0.0
 * @Description AddMerchantDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "新增商户")
public class AddMerchantDto {

    /**
     * 商户编号
     */
    @NotBlank(message = "商户编号不能为空")
    @Schema(description = "商户编号")
    private String merchantNo;

    /**
     * 商户名称
     */
    @NotBlank(message = "商户名称不能为空")
    @Schema(description = "商户名称")
    private String merchantName;

    /**
     * 商户简称
     */
    @NotBlank(message = "商户简称不能为空")
    @Schema(description = "商户简称")
    private String merchantShortName;

    /**
     * 商户备注
     */
    @Schema(description = "商户备注")
    private String remark;
}
