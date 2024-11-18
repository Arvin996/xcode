package cn.xk.xcode.service;

import cn.xk.xcode.entity.UpdateStockDto;
import cn.xk.xcode.entity.dish.TakeoutDishResultVo;
import cn.xk.xcode.entity.dto.dish.AddDishDto;
import cn.xk.xcode.entity.dto.dish.QueryDishDto;
import cn.xk.xcode.entity.dto.dish.UpdateDishDto;
import cn.xk.xcode.pojo.PageResult;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.TakeoutDishPo;

import java.util.Collection;
import java.util.List;

/**
 *  服务层。
 *
 * @author Administrator
 * @since 2024-10-31
 */
public interface TakeoutDishService extends IService<TakeoutDishPo> {

    List<TakeoutDishResultVo> getDishList();

    TakeoutDishResultVo getDish(Long id);

    List<TakeoutDishResultVo> getDishes(Collection<Long> ids);

    Boolean addTakeoutDish(AddDishDto addDishDto);

    Boolean delTakeoutDish(Long id);

    Boolean updateTakeoutDish(UpdateDishDto updateDishDto);

    Boolean updateDishStock(UpdateStockDto updateStockDto);

    PageResult<TakeoutDishResultVo> queryDishList(QueryDishDto queryDishDto);
}
