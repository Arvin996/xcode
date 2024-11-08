package cn.xk.xcode.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.entity.UpdateStockDto;
import cn.xk.xcode.entity.dto.seatmeal.AddSetmealDto;
import cn.xk.xcode.entity.dto.seatmeal.QuerySetmealDto;
import cn.xk.xcode.entity.dto.seatmeal.UpdateSetmealDto;
import cn.xk.xcode.entity.setmeal.TakeoutSetmealResultVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.TakeoutDishService;
import cn.xk.xcode.service.TakeoutSetmealDishService;
import cn.xk.xcode.utils.PageUtil;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.TakeoutSetmealPo;
import cn.xk.xcode.mapper.TakeoutSetmealMapper;
import cn.xk.xcode.service.TakeoutSetmealService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static cn.xk.xcode.constant.GlobalTakeoutProductConstants.*;
import static cn.xk.xcode.entity.def.TakeoutCategoryTableDef.TAKEOUT_CATEGORY_PO;
import static cn.xk.xcode.entity.def.TakeoutSetmealDishTableDef.TAKEOUT_SETMEAL_DISH_PO;
import static cn.xk.xcode.entity.def.TakeoutSetmealTableDef.TAKEOUT_SETMEAL_PO;

/**
 * 服务层实现。
 *
 * @author Administrator
 * @since 2024-10-31
 */
@Service
public class TakeoutSetmealServiceImpl extends ServiceImpl<TakeoutSetmealMapper, TakeoutSetmealPo> implements TakeoutSetmealService {

    @Resource
    private TakeoutDishService takeoutDishService;

    @Resource
    private TakeoutSetmealDishService takeoutSetmealDishService;

    @Override
    public List<TakeoutSetmealResultVo> getSetmealList() {
        QueryWrapper queryWrapper = QueryWrapper
                .create()
                .select(TAKEOUT_SETMEAL_PO.ALL_COLUMNS, TAKEOUT_CATEGORY_PO.NAME.as("categoryName"))
                .from(TAKEOUT_SETMEAL_PO)
                .leftJoin(TAKEOUT_CATEGORY_PO)
                .on(TAKEOUT_SETMEAL_PO.CATEGORY_ID.eq(TAKEOUT_CATEGORY_PO.ID));
        return getSetmealList(queryWrapper);
    }

    @Override
    public List<TakeoutSetmealResultVo> getSetmeals(Collection<Long> ids) {
        QueryWrapper queryWrapper = QueryWrapper
                .create()
                .select(TAKEOUT_SETMEAL_PO.ALL_COLUMNS, TAKEOUT_CATEGORY_PO.NAME.as("categoryName"))
                .from(TAKEOUT_SETMEAL_PO)
                .leftJoin(TAKEOUT_CATEGORY_PO)
                .on(TAKEOUT_SETMEAL_PO.CATEGORY_ID.eq(TAKEOUT_CATEGORY_PO.ID))
                .where(TAKEOUT_SETMEAL_PO.ID.in(ids));

        return getSetmealList(queryWrapper);
    }

    @Override
    public TakeoutSetmealResultVo getSetmeal(Long id) {
        List<TakeoutSetmealResultVo> takeoutSetmealResultVos = getSetmeals(CollectionUtil.createSingleList(id));
        if (takeoutSetmealResultVos.isEmpty()) {
            return null;
        }
        return takeoutSetmealResultVos.get(0);
    }

    @Override
    public Boolean updateSetmealStock(UpdateStockDto updateStockDto) {
        Long productId = updateStockDto.getProductId();
        TakeoutSetmealPo takeoutSetmealPo = this.getById(productId);
        boolean update = UpdateChain.of(TAKEOUT_SETMEAL_PO)
                .set(TAKEOUT_SETMEAL_PO.STOCK, takeoutSetmealPo.getStock() - updateStockDto.getStock())
                .where(TAKEOUT_SETMEAL_PO.STOCK.gt(updateStockDto.getStock()))
                .where(TAKEOUT_SETMEAL_PO.ID.eq(productId))
                .update();
        if (!update) {
            ExceptionUtil.castServiceException(SETMEAL_STOCK_IS_NOT_ENOUGH);
        }
        return true;
    }

    @Override
    public Boolean addSetmeal(AddSetmealDto addSetmealDto) {
        if (this.count(TAKEOUT_SETMEAL_PO.NAME.eq(addSetmealDto.getName())
                .and(TAKEOUT_SETMEAL_PO.CATEGORY_ID.eq(addSetmealDto.getCategoryId()))) > 0) {
            ExceptionUtil.castServiceException(SETMEAL_HAS_ALREADY_EXISTS);
        }
        return this.save(BeanUtil.toBean(addSetmealDto, TakeoutSetmealPo.class));
    }

    @Transactional
    @Override
    public Boolean delSetmeal(Long id) {
        this.removeById(id);
        takeoutSetmealDishService.remove(TAKEOUT_SETMEAL_DISH_PO.SETMEAL_ID.eq(id));
        return true;
    }

    @Override
    public Boolean updateSetmeal(UpdateSetmealDto updateSetmealDto) {
        if (this.getById(updateSetmealDto.getId()) == null){
            ExceptionUtil.castServiceException(SETMEAL_HAS_DELETED);
        }
        if (this.count(TAKEOUT_SETMEAL_PO.NAME.eq(updateSetmealDto.getName())
                .and(TAKEOUT_SETMEAL_PO.CATEGORY_ID.eq(updateSetmealDto.getCategoryId())
                        .and(TAKEOUT_SETMEAL_PO.ID.ne(updateSetmealDto.getId())))) > 0){
            ExceptionUtil.castServiceException(SETMEAL_HAS_ALREADY_EXISTS);
        }
        return this.updateById(BeanUtil.toBean(updateSetmealDto, TakeoutSetmealPo.class));
    }

    @Override
    public PageResult<TakeoutSetmealResultVo> querySetmealList(QuerySetmealDto querySetmealDto) {
        QueryWrapper queryWrapper = QueryWrapper
                .create()
                .select(TAKEOUT_SETMEAL_PO.ID)
                .from(TAKEOUT_SETMEAL_PO)
                .where("1=1")
                .and(TAKEOUT_SETMEAL_PO.ID.eq(querySetmealDto.getId()).when(ObjectUtil.isNotNull(querySetmealDto.getId()))
                        .and(TAKEOUT_SETMEAL_PO.NAME.like(querySetmealDto.getName()).when(StrUtil.isNotBlank(querySetmealDto.getName()))
                                .and(TAKEOUT_SETMEAL_PO.CATEGORY_ID.eq(querySetmealDto.getCategoryId()).when(ObjectUtil.isNotNull(querySetmealDto.getCategoryId()))
                                        .and(TAKEOUT_SETMEAL_PO.PRICE.ge(querySetmealDto.getMinPrice()).when(ObjectUtil.isNotNull(querySetmealDto.getMinPrice()))
                                                .and(TAKEOUT_SETMEAL_PO.PRICE.le(querySetmealDto.getMaxPrice()).when(ObjectUtil.isNotNull(querySetmealDto.getMaxPrice())))))));
        List<Long> list = this.listAs(queryWrapper, Long.class);
        List<TakeoutSetmealResultVo> takeoutSetmealResultVos = this.getSetmeals(list);
        return PageUtil.startPage(querySetmealDto, takeoutSetmealResultVos);
    }

    public List<TakeoutSetmealResultVo> getSetmealList(QueryWrapper queryWrapper) {
        List<TakeoutSetmealResultVo> takeoutSetmealResultVos = this.listAs(queryWrapper, TakeoutSetmealResultVo.class);
        if (takeoutSetmealResultVos == null || takeoutSetmealResultVos.isEmpty()) {
            return Collections.emptyList();
        }
        takeoutSetmealResultVos.forEach(v -> {
            QueryWrapper wrapper = QueryWrapper
                    .create()
                    .select(TAKEOUT_SETMEAL_DISH_PO.DISH_ID)
                    .from(TAKEOUT_SETMEAL_DISH_PO)
                    .where(TAKEOUT_SETMEAL_DISH_PO.SETMEAL_ID.eq(v.getId()));
            List<Long> dishList = takeoutSetmealDishService.listAs(wrapper, Long.class);
            v.setSetmealDishList(takeoutDishService.getDishes(dishList));
        });
        return takeoutSetmealResultVos;
    }
}
