package cn.xk.xcode.entity.vo;

import cn.xk.xcode.entity.DataLongObjectBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Author xuk
 * @Date 2024/10/29 15:26
 * @Version 1.0.0
 * @Description TakeoutAddressResultVo
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户收货地址返回")
public class TakeoutAddressResultVo extends DataLongObjectBaseEntity {

    /**
     * 主键
     */
    @Schema(description = "地址id")
    private Long id;


    /**
     * 收货人
     */
    @Schema(description = "收货人")
    private String consignee;

    /**
     * 性别
     */
    @Schema(description = "性别")
    private String sex;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String mobile;

    /**
     * 省级区划编号
     */
    @Schema(description = "省级区划编号")
    private String provinceCode;

    /**
     * 省级名称
     */
    @Schema(description = "省级名称")
    private String provinceName;

    /**
     * 市级区划编号
     */
    @Schema(description = "市级区划编号")
    private String cityCode;

    /**
     * 市级名称
     */
    @Schema(description = "市级名称")
    private String cityName;

    /**
     * 区级区划编号
     */
    @Schema(description = "区级区划编号")
    private String districtCode;

    /**
     * 区级名称
     */
    @Schema(description = "区级名称")
    private String districtName;

    /**
     * 详细地址
     */
    @Schema(description = "详细地址")
    private String detail;

    /**
     * 标签
     */
    @Schema(description = "标签")
    private String label;

    /**
     * 默认 0 否 1是
     */
    @Schema(description = "是否是默认地址 0否 1是")
    private Integer isDefault;
}
