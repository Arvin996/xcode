package cn.xk.xcode.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.client.TakeoutDishClient;
import cn.xk.xcode.client.TakeoutSetmealClient;
import cn.xk.xcode.client.TakeoutUserClient;
import cn.xk.xcode.core.CommonStatusEnum;
import cn.xk.xcode.entity.dish.TakeoutDishResultVo;
import cn.xk.xcode.entity.dto.cart.AddShoppingCartDto;
import cn.xk.xcode.entity.dto.cart.ClearShoppingCartDto;
import cn.xk.xcode.entity.dto.cart.ShoppingCartBaseDto;
import cn.xk.xcode.entity.dto.cart.UpdateShoppingCartItemDto;
import cn.xk.xcode.entity.enums.ProductType;
import cn.xk.xcode.entity.setmeal.TakeoutSetmealResultVo;
import cn.xk.xcode.entity.vo.TakeoutUserResultVo;
import cn.xk.xcode.entity.vo.cart.ShoppingCartVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.utils.number.MoneyUtil;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.TakeoutShoppingCartPo;
import cn.xk.xcode.mapper.TakeoutShoppingCartMapper;
import cn.xk.xcode.service.TakeoutShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static cn.xk.xcode.GlobalTakeoutTradeConstants.*;
import static cn.xk.xcode.entity.def.TakeoutShoppingCartTableDef.TAKEOUT_SHOPPING_CART_PO;

/**
 * 服务层实现。
 *
 * @author xuk
 * @since 2024-11-06
 */
@Service
public class TakeoutShoppingCartServiceImpl extends ServiceImpl<TakeoutShoppingCartMapper, TakeoutShoppingCartPo> implements TakeoutShoppingCartService {

    @Resource
    private TakeoutUserClient takeoutUserClient;

    @Resource
    private TakeoutDishClient takeoutDishClient;

    @Resource
    private TakeoutSetmealClient takeoutSetmealClient;

    @Override
    public ShoppingCartVo addShoppingCart(AddShoppingCartDto addShoppingCartDto) {
        if (!addShoppingCartDto.isValid()) {
            ExceptionUtil.castServiceException(SHOPPING_CART_PARAMS_INVALID);
        }
        TakeoutUserResultVo takeoutUserResultVo = takeoutUserClient.getTakeoutUser(addShoppingCartDto.getUserId()).getData();
        if (Objects.isNull(takeoutUserResultVo)) {
            ExceptionUtil.castServiceException(OPERATOR_USER_NOT_EXISTS);
        }
        if (CommonStatusEnum.isDisable(String.valueOf(takeoutUserResultVo.getStatus()))) {
            ExceptionUtil.castServiceException(OPERATOR_USER_IS_DISABLED);
        }
        if (Objects.nonNull(addShoppingCartDto.getDishId())) {
            // 菜品
            TakeoutDishResultVo takeoutDishResultVo = takeoutDishClient.getDish(addShoppingCartDto.getDishId()).getData();
            if (Objects.isNull(takeoutDishResultVo)) {
                ExceptionUtil.castServiceException(DISH_NOT_EXISTS);
            }
            // 存在 此时看购物车里有没有这个菜品 有的话需要更新 没有的话就插入
            TakeoutShoppingCartPo takeoutShoppingCartPo = getShoppingCartByUserIdAndItemId(addShoppingCartDto.getUserId(), addShoppingCartDto.getDishId());
            if (Objects.isNull(takeoutShoppingCartPo)) {
                // 直接插入
                takeoutShoppingCartPo = new TakeoutShoppingCartPo();
                BeanUtils.copyProperties(addShoppingCartDto, takeoutShoppingCartPo);
                takeoutShoppingCartPo.setAmount(MoneyUtil.priceMultiply(takeoutDishResultVo.getPrice(), new BigDecimal(addShoppingCartDto.getNumber().toString())));
                this.save(takeoutShoppingCartPo);
            } else {
                // 更新
                takeoutShoppingCartPo.setNumber(addShoppingCartDto.getNumber());
                takeoutShoppingCartPo.setAmount(MoneyUtil.priceMultiply(takeoutDishResultVo.getPrice(), new BigDecimal(addShoppingCartDto.getNumber().toString())));
                this.updateById(takeoutShoppingCartPo);
            }

        } else {
            // 套餐
            TakeoutSetmealResultVo takeoutSetmealResultVo = takeoutSetmealClient.getSetmeal(addShoppingCartDto.getSetmealId()).getData();
            if (Objects.isNull(takeoutSetmealResultVo)) {
                ExceptionUtil.castServiceException(SETMEAL_NOT_EXISTS);
            }
            TakeoutShoppingCartPo takeoutShoppingCartPo = getShoppingCartByUserIdAndItemId(addShoppingCartDto.getUserId(), addShoppingCartDto.getSetmealId());
            if (Objects.isNull(takeoutShoppingCartPo)) {
                // 直接插入
                takeoutShoppingCartPo = new TakeoutShoppingCartPo();
                BeanUtils.copyProperties(addShoppingCartDto, takeoutShoppingCartPo);
                takeoutShoppingCartPo.setAmount(MoneyUtil.priceMultiply(takeoutSetmealResultVo.getPrice(), new BigDecimal(addShoppingCartDto.getNumber().toString())));
                this.save(takeoutShoppingCartPo);
            } else {
                // 更新
                takeoutShoppingCartPo.setNumber(addShoppingCartDto.getNumber());
                takeoutShoppingCartPo.setAmount(MoneyUtil.priceMultiply(takeoutSetmealResultVo.getPrice(), new BigDecimal(addShoppingCartDto.getNumber().toString())));
                this.updateById(takeoutShoppingCartPo);
            }
        }
        // 构造返回值
        return buildShoppingCartVo(addShoppingCartDto.getUserId());
    }

    public ShoppingCartVo buildShoppingCartVo(Long userId) {
        BigDecimal totalAmount = getTotalAmount(userId);
        ShoppingCartVo shoppingCartVo = new ShoppingCartVo();
        shoppingCartVo.setTotalPrice(totalAmount);
        shoppingCartVo.setTakeoutShoppingCartPoList(getShoppingCartByUserId(userId));
        return shoppingCartVo;
    }

    @Override
    public ShoppingCartVo delShoppingCartItem(ShoppingCartBaseDto shoppingCartBaseDto) {
        this.removeById(shoppingCartBaseDto.getId());
        return buildShoppingCartVo(shoppingCartBaseDto.getUserId());
    }

    @Override
    public ShoppingCartVo updateShoppingCartItem(UpdateShoppingCartItemDto updateShoppingCartItemDto) {
        TakeoutShoppingCartPo shoppingCartPo = this.getById(updateShoppingCartItemDto.getId());
        if (Objects.isNull(shoppingCartPo)) {
            ExceptionUtil.castServiceException(SHOPPING_CART_ITEM_HAS_DELETED);
        }
        shoppingCartPo.setDishFlavor(updateShoppingCartItemDto.getDishFlavor());
        shoppingCartPo.setNumber(updateShoppingCartItemDto.getNumber());
        Long userId = shoppingCartPo.getUserId();
        shoppingCartPo.setAmount(MoneyUtil.priceMultiply(getItemPrice(shoppingCartPo), new BigDecimal(updateShoppingCartItemDto.getNumber().toString())));
        this.updateById(shoppingCartPo);
        return buildShoppingCartVo(userId);
    }

    @Override
    public ShoppingCartVo subShoppingCartItem(ShoppingCartBaseDto shoppingCartBaseDto) {
        TakeoutShoppingCartPo shoppingCartPo = this.getById(shoppingCartBaseDto.getId());
        if (Objects.isNull(shoppingCartPo)) {
            ExceptionUtil.castServiceException(SHOPPING_CART_ITEM_HAS_DELETED);
        }
        Integer number = shoppingCartPo.getNumber();
        if (number == 1) {
            this.removeById(shoppingCartBaseDto.getId());
        } else {
            shoppingCartPo.setNumber(number - 1);
            shoppingCartPo.setAmount(MoneyUtil.priceMultiply(getItemPrice(shoppingCartPo), new BigDecimal(shoppingCartPo.getNumber().toString())));
            this.updateById(shoppingCartPo);
        }
        return null;
    }

    @Override
    public ShoppingCartVo clearShoppingCart(ClearShoppingCartDto clearShoppingCartDto) {
        this.remove(TAKEOUT_SHOPPING_CART_PO.USER_ID.eq(clearShoppingCartDto.getUserId()));
        return new ShoppingCartVo().setTotalPrice(new BigDecimal("0")).setTakeoutShoppingCartPoList(new ArrayList<>());
    }

    @Override
    public ShoppingCartVo getShoppingCart(Long userId) {
        List<TakeoutShoppingCartPo> takeoutShoppingCartPos = getShoppingCartByUserId(userId);
        BigDecimal totalAmount = getTotalAmount(userId);
        return new ShoppingCartVo().setTotalPrice(totalAmount).setTakeoutShoppingCartPoList(takeoutShoppingCartPos);
    }

    private BigDecimal getItemPrice(TakeoutShoppingCartPo takeoutShoppingCartPo){
        if (ObjectUtil.isNotNull(takeoutShoppingCartPo.getDishId())){
            return getItemPrice(takeoutShoppingCartPo.getDishId(), ProductType.DISH);
        }else {
            return getItemPrice(takeoutShoppingCartPo.getSetmealId(), ProductType.SETMEAL);
        }
    }

    private BigDecimal getItemPrice(Long itemId, ProductType type) {
        if (ObjectUtil.equals(type, ProductType.DISH)) {
            TakeoutDishResultVo takeoutDishResultVo = takeoutDishClient.getDish(itemId).getData();
            if (Objects.isNull(takeoutDishResultVo)) {
                ExceptionUtil.castServiceException(DISH_NOT_EXISTS);
            }
            return takeoutDishResultVo.getPrice();
        } else {
            TakeoutSetmealResultVo takeoutSetmealResultVo = takeoutSetmealClient.getSetmeal(itemId).getData();
            if (Objects.isNull(takeoutSetmealResultVo)) {
                ExceptionUtil.castServiceException(SETMEAL_NOT_EXISTS);
            }
            return takeoutSetmealResultVo.getPrice();
        }
    }

    public List<TakeoutShoppingCartPo> getShoppingCartByUserId(Long userId) {
        return this.list(TAKEOUT_SHOPPING_CART_PO.USER_ID.eq(userId));
    }

    public TakeoutShoppingCartPo getShoppingCartByUserIdAndItemId(Long userId, Long itemId) {
        return this.getOne(TAKEOUT_SHOPPING_CART_PO.USER_ID.eq(userId)
                .and(TAKEOUT_SHOPPING_CART_PO.DISH_ID.eq(itemId).or(TAKEOUT_SHOPPING_CART_PO.SETMEAL_ID.eq(itemId))));
    }

    public BigDecimal getTotalAmount(Long userId) {
        QueryWrapper queryWrapper = QueryWrapper
                .create()
                .select(QueryMethods.sum(TAKEOUT_SHOPPING_CART_PO.AMOUNT).as("sum"))
                .from(TAKEOUT_SHOPPING_CART_PO)
                .where(TAKEOUT_SHOPPING_CART_PO.USER_ID.eq(userId));
        return this.getOneAs(queryWrapper, BigDecimal.class);
    }

}
