package cn.xk.xcode.entity.dto.seatmeal;

import cn.xk.xcode.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author xuk
 * @Date 2024/11/6 13:54
 * @Version 1.0.0
 * @Description QuerySetmealDto
 **/

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "查询套餐 dto")
public class QuerySetmealDto extends PageParam {

    @Schema(description = "套餐id")
    private Long id;

    @Schema(description = "曹套餐名")
    private String name;

    @Schema(description = "套餐分类类型")
    private Integer categoryId;

    @Schema(description = "菜品最低价格")
    private BigDecimal minPrice;

    @Schema(description = "菜品最高价格")
    private BigDecimal maxPrice;
}
