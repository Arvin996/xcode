package cn.xk.xcode.entity.vo.search;

import cn.xk.xcode.entity.dish.TakeoutDishResultVo;
import cn.xk.xcode.entity.setmeal.TakeoutSetmealResultVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/11/6 10:59
 * @Version 1.0.0
 * @Description SearchVo
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "商品查询返回")
public class SearchVo {

    @Schema(description = "菜品")
    private List<TakeoutDishResultVo> dishList;

    @Schema(description = "套餐")
    private List<TakeoutSetmealResultVo> setmealList;
}
