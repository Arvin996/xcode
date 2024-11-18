package cn.xk.xcode.entity.dto.merchant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author xuk
 * @Date 2024/9/23 16:10
 * @Version 1.0.0
 * @Description QueryMerchantDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "新增商户")
public class QueryMerchantDto {

    /**
     * 商户编号
     */
    @Schema(description = "商户编号")
    private String merchantNo;

    /**
     * 商户名称
     */
    @Schema(description = "商户名称")
    private String merchantName;

    /**
     * 商户简称
     */
    @Schema(description = "商户简称")
    private String merchantShortName;

    /**
     * 商户状态 0 开启 1关闭
     */
    @Schema(description = "商户状态 0 开启 1关闭")
    private String status;
}
