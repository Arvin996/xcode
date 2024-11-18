package cn.xk.xcode.entity.vo.category;

import cn.xk.xcode.entity.DataLongObjectBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @Author xuk
 * @Date 2024/11/4 8:53
 * @Version 1.0.0
 * @Description CategoryResultVo
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "菜品分类查询返回 vo")
public class CategoryResultVo extends DataLongObjectBaseEntity {

    /**
     * 主键
     */
   @Schema(description = "分类id")
    private Long id;

    /**
     * 类型   1 菜品分类 2 套餐分类
     */
    @Schema(description = "分类类型")
    private Integer type;

    /**
     * 分类名称
     */
    @Schema(description = "分类名称")
    private String name;

    @Schema(description = "分类顺序")
    private Integer sort;
}
