package cn.xk.xcode.entity.dish;

import cn.xk.xcode.entity.DataLongObjectBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Author xuk
 * @Date 2024/11/1 11:03
 * @Version 1.0.0
 * @Description TakeoutDishResultVo
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户收货地址返回")
public class TakeoutDishResultVo extends DataLongObjectBaseEntity {

    /**
     * 菜品id
     */
    @Schema(description = "菜品id")
    private Long id;

    /**
     * 菜品名称
     */
    @Schema(description = "菜品名称")
    private String name;

    /**
     * 菜品分类id
     */
    @Schema(description = "菜品分类id")
    private Long categoryId;

    /**
     * 菜品分类名称
     */
    @Schema(description = "菜品分类名称")
    private String categoryName;

    /**
     * 菜品价格
     */
    @Schema(description = "菜品价格")
    private BigDecimal price;

    /**
     * 商品码
     */
    @Schema(description = "商品码")
    private String code;

    /**
     * 图片
     */
    @Schema(description = "图片")
    private String image;

    /**
     * 描述信息
     */
    @Schema(description = "描述信息")
    private String description;

    /**
     * 0 起售 1 停售
     */
    @Schema(description = " 0 起售 1 停售")
    private Integer status;

    /**
     * 顺序
     */
    private Integer sort;
}
