package cn.xk.xcode.entity.setmeal;

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
 * @Description TakeoutSetmealResultVo
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户收货地址返回")
public class TakeoutSetmealResultVo extends DataLongObjectBaseEntity {

    /**
     *  套餐id
     */
    @Schema(description = "套餐id")
    private Long id;

    /**
     * 菜品分类id
     */
    @Schema(description = "套餐分类分类id")
    private Long categoryId;


    /**
     * 菜品分类名称
     */
    @Schema(description = "套餐分类名称")
    private String categoryName;

    /**
     * 套餐名称
     */
    private String name;

    /**
     * 套餐价格
     */
    private BigDecimal price;

    /**
     * 状态 0:启用 1:停用
     */
    private Integer status;

    /**
     * 编码
     */
    private String code;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 图片
     */
    private String image;
}
