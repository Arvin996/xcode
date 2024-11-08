package cn.xk.xcode.service;

import cn.xk.xcode.entity.UpdateStockDto;
import cn.xk.xcode.entity.dto.seatmeal.AddSetmealDto;
import cn.xk.xcode.entity.dto.seatmeal.QuerySetmealDto;
import cn.xk.xcode.entity.dto.seatmeal.SetmealBaseDto;
import cn.xk.xcode.entity.dto.seatmeal.UpdateSetmealDto;
import cn.xk.xcode.entity.setmeal.TakeoutSetmealResultVo;
import cn.xk.xcode.pojo.PageResult;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.TakeoutSetmealPo;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

/**
 *  服务层。
 *
 * @author Administrator
 * @since 2024-10-31
 */
public interface TakeoutSetmealService extends IService<TakeoutSetmealPo> {

    List<TakeoutSetmealResultVo> getSetmealList();

    List<TakeoutSetmealResultVo> getSetmeals(Collection<Long> ids);

    TakeoutSetmealResultVo getSetmeal(Long id);

    Boolean updateSetmealStock(UpdateStockDto updateStockDto);

    Boolean addSetmeal(AddSetmealDto addSetmealDto);

    Boolean delSetmeal(Long id);

    Boolean updateSetmeal(UpdateSetmealDto updateSetmealDto);

    PageResult<TakeoutSetmealResultVo> querySetmealList(QuerySetmealDto querySetmealDto);
}
