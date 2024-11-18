package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.cart.AddShoppingCartDto;
import cn.xk.xcode.entity.dto.cart.ClearShoppingCartDto;
import cn.xk.xcode.entity.dto.cart.ShoppingCartBaseDto;
import cn.xk.xcode.entity.dto.cart.UpdateShoppingCartItemDto;
import cn.xk.xcode.entity.vo.cart.ShoppingCartVo;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.TakeoutShoppingCartPo;

/**
 *  服务层。
 *
 * @author xuk
 * @since 2024-11-06
 */
public interface TakeoutShoppingCartService extends IService<TakeoutShoppingCartPo> {

    ShoppingCartVo addShoppingCart(AddShoppingCartDto addShoppingCartDto);

    ShoppingCartVo delShoppingCartItem(ShoppingCartBaseDto shoppingCartBaseDto);

    ShoppingCartVo updateShoppingCartItem(UpdateShoppingCartItemDto updateShoppingCartItemDto);

    ShoppingCartVo subShoppingCartItem(ShoppingCartBaseDto shoppingCartBaseDto);

    ShoppingCartVo clearShoppingCart(ClearShoppingCartDto clearShoppingCartDto);

    ShoppingCartVo getShoppingCart(Long userId);
}
