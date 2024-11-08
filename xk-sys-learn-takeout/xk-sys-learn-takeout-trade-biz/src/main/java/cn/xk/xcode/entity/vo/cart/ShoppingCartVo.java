package cn.xk.xcode.entity.vo.cart;

import cn.xk.xcode.entity.po.TakeoutShoppingCartPo;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/11/6 15:57
 * @Version 1.0.0
 * @Description ShoppingCartVo
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Tag(name = "购物车数据返回")
public class ShoppingCartVo {

    @Schema(description = "总价")
    private BigDecimal totalPrice;

    @Schema(description = "购物车明细数据")
    private List<TakeoutShoppingCartPo> takeoutShoppingCartPoList;

}
