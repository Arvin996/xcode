package cn.xk.xcode.entity.dto.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author xuk
 * @Date 2024/11/4 14:37
 * @Version 1.0.0
 * @Description UpdateDishDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "新增菜品 dto")
public class UpdateDishDto extends DishBaseDto{

    /**
     * 菜品名称
     */
    @Schema(description = "菜品名称")
    @NotBlank(message = "菜品名称不能为空")
    private String name;

    /**
     * 菜品分类id
     */
    @Schema(description = "菜品分类id")
    @NotNull(message = "菜品分类id不能为空")
    private Long categoryId;

    /**
     * 菜品价格
     */
    @Schema(description = "菜品价格")
    @NotNull(message = "菜品价格不能为空")
    @DecimalMin(value = "0.01", message = "菜品价格不能小于0.01")
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
     * 顺序
     */
    @Schema(description = "顺序")
    private Integer sort;
}
