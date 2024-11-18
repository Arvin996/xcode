package cn.xk.xcode.entity.dto.seatmeal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author xuk
 * @Date 2024/11/6 10:33
 * @Version 1.0.0
 * @Description AddSetmealDto
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "新增套餐 dto")
public class AddSetmealDto {

    /**
     * 菜品分类id
     */
    @NotNull(message = "菜品分类id不能为空")
    @Schema(description = "菜品分类id")
    private Long categoryId;

    /**
     * 套餐名称
     */
    @NotBlank(message = "套餐名称不能为空")
    @Schema(description = "套餐名称")
    private String name;

    /**
     * 套餐价格
     */
    @NotNull(message = "套餐价格不能为空")
    @Schema(description = "套餐价格")
    @DecimalMin(value = "0.01", message = "套餐价格不能小于0.01")
    private BigDecimal price;

    /**
     * 套餐库存
     */
    @Schema(description = "套餐库存")
    private Integer stock;

    /**
     * 编码
     */
    @Schema(description = "编码")
    private String code;

    /**
     * 描述信息
     */
    @Schema(description = "描述信息")
    private String description;

    /**
     * 图片
     */
    @Schema(description = "图片")
    private String image;
}
