package cn.xk.xcode.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author xuk
 * @Date 2024/11/6 14:24
 * @Version 1.0.0
 * @Description TakeoutOrderDetailsResultVo
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(name = "订单信息返回")
public class TakeoutOrderDetailsResultVo {

    /**
     * 主键
     */
    @Schema(description = "明细主键")
    private Long id;

    /**
     * 名字
     */
    @Schema(description = "物品名字")
    private String name;

    /**
     * 图片
     */
    @Schema(description = "物品图片")
    private String image;


    /**
     * 菜品id
     */
    @Schema(description = "菜品id")
    private Long dishId;

    /**
     * 套餐id
     */
    private Long setmealId;


    /**
     * 口味
     */
    @Schema(description = "口味")
    private String dishFlavor;

    /**
     * 数量
     */
    @Schema(description = "数量")
    private Integer number;

    /**
     * 金额
     */
    @Schema(description = "金额")
    private BigDecimal amount;

    @Schema(description = "商品价格")
    private BigDecimal price;

    @Schema(description = "商品码")
    private String code;

    @Schema(description = "商品描述")
    private String description;
}
