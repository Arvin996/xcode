package cn.xk.xcode.entity.dto.address;

import cn.xk.xcode.validation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2024/10/30 10:20
 * @Version 1.0.0
 * @Description UpdateAddressDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "修改用户收货地址dto")
public class UpdateAddressDto extends AddressBaseDto{

    /**
     * 收货人
     */
    @Schema(description = "收货人")
    @NotBlank(message = "收货人不能为空")
    private String consignee;

    /**
     * 性别 0 男 1 女
     */
    @Schema(description = "性别")
    @NotBlank(message = "性别不能为空")
    private String sex;

    /**
     * 手机号
     */
    @Mobile
    @Schema(description = "手机号")
    private String mobile;

    /**
     * 省级区划编号
     */
    @NotBlank(message = "省级区划编号不能为空")
    @Schema(description = "省级区划编号")
    private String provinceCode;

    /**
     * 省级名称
     */
    @NotBlank(message = "省级名称不能为空")
    @Schema(description = "省级名称")
    private String provinceName;

    /**
     * 市级区划编号
     */
    @NotBlank(message = "市级区划编号不能为空")
    @Schema(description = "市级区划编号")
    private String cityCode;

    /**
     * 市级名称
     */
    @NotBlank(message = "市级名称不能为空")
    @Schema(description = "市级名称")
    private String cityName;

    /**
     * 区级区划编号
     */
    @NotBlank(message = "区级区划编号不能为空")
    @Schema(description = "区级区划编号")
    private String districtCode;

    /**
     * 区级名称
     */
    @NotBlank(message = "区级名称不能为空")
    @Schema(description = "区级名称")
    private String districtName;

    /**
     * 详细地址
     */
    @NotBlank(message = "详细地址不能为空")
    @Schema(description = "详细地址")
    private String detail;

    /**
     * 标签
     */
    @Schema(description = "标签")
    private String label;
}
