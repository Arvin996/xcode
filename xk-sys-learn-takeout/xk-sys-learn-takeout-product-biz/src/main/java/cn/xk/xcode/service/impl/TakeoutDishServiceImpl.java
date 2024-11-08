package cn.xk.xcode.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.xk.xcode.core.lock.DistributedLock;
import cn.xk.xcode.entity.UpdateStockDto;
import cn.xk.xcode.entity.dish.TakeoutDishResultVo;
import cn.xk.xcode.entity.dto.dish.AddDishDto;
import cn.xk.xcode.entity.dto.dish.QueryDishDto;
import cn.xk.xcode.entity.dto.dish.UpdateDishDto;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.mapper.TakeoutCategoryMapper;
import cn.xk.xcode.mapper.TakeoutSetmealDishMapper;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.utils.PageUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.TakeoutDishPo;
import cn.xk.xcode.mapper.TakeoutDishMapper;
import cn.xk.xcode.service.TakeoutDishService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static cn.xk.xcode.constant.GlobalTakeoutProductConstants.*;
import static cn.xk.xcode.entity.def.TakeoutCategoryTableDef.TAKEOUT_CATEGORY_PO;
import static cn.xk.xcode.entity.def.TakeoutDishTableDef.TAKEOUT_DISH_PO;
import static cn.xk.xcode.entity.def.TakeoutSetmealDishTableDef.TAKEOUT_SETMEAL_DISH_PO;

/**
 * 服务层实现。
 *
 * @author Administrator
 * @since 2024-10-31
 */
@Service
public class TakeoutDishServiceImpl extends ServiceImpl<TakeoutDishMapper, TakeoutDishPo> implements TakeoutDishService {

    @Resource
    private TakeoutDishMapper takeoutDishMapper;

    @Resource
    private TakeoutCategoryMapper takeoutCategoryMapper;

    @Resource
    private TakeoutSetmealDishMapper takeoutSetmealDishMapper;

    @Resource
    private DistributedLock distributedLock;

    private static final String REDISSON_PREFIX = "takeout:dish:";

    @Override
    public List<TakeoutDishResultVo> getDishList() {
        return takeoutDishMapper.getDishList(new HashMap<>());
    }

    @Override
    public TakeoutDishResultVo getDish(Long id) {
        List<TakeoutDishResultVo> takeoutDishMapperDishList = takeoutDishMapper.getDishList(MapUtil.of("id", id));
        if (takeoutDishMapperDishList.isEmpty()) {
            return null;
        }
        return takeoutDishMapper.getDishList(MapUtil.of("id", id)).get(0);
    }

    @Override
    public List<TakeoutDishResultVo> getDishes(Collection<Long> ids) {
        List<TakeoutDishResultVo> takeoutDishMapperDishList = takeoutDishMapper.getDishList(MapUtil.of("ids", ids));
        if (takeoutDishMapperDishList.isEmpty()) {
            return Collections.emptyList();
        }
        return takeoutDishMapperDishList;
    }

    @Override
    public Boolean addTakeoutDish(AddDishDto addDishDto) {
        validate(addDishDto);
        return this.save(BeanUtil.toBean(addDishDto, TakeoutDishPo.class));
    }

    @Override
    public Boolean delTakeoutDish(Long id) {
        TakeoutDishPo takeoutDishPo = this.getById(id);
        if (ObjUtil.isNull(takeoutDishPo)) {
            return true;
        }
        if (takeoutSetmealDishMapper.selectCountByCondition(TAKEOUT_SETMEAL_DISH_PO.DISH_ID.eq(id)) > 0) {
            ExceptionUtil.castServiceException(DISH_HAS_BIND_SETMEAL);
        }
        return this.removeById(id);
    }

    @Override
    public Boolean updateTakeoutDish(UpdateDishDto updateDishDto) {
        Long id = updateDishDto.getId();
        TakeoutDishPo takeoutDishPo = this.getById(id);
        if (ObjUtil.isNull(takeoutDishPo)) {
            ExceptionUtil.castServiceException(DISH_HAS_DELETED);
        }
        String name = updateDishDto.getName();
        if (this.count(TAKEOUT_DISH_PO.NAME.eq(name).and(TAKEOUT_DISH_PO.CATEGORY_ID.eq(updateDishDto.getCategoryId())).and(TAKEOUT_DISH_PO.ID.ne(id))) > 0) {
            ExceptionUtil.castServiceException(DISH_HAS_ALREADY_EXISTS);
        }
        BeanUtils.copyProperties(updateDishDto, takeoutDishPo);
        return this.updateById(takeoutDishPo);
    }

    @Override
    public Boolean updateDishStock(UpdateStockDto updateStockDto) {
        // 这个接口的逻辑要注意防止超卖等情况发生
        // 方案1 使用分布式锁 redisson
        // 方案2 使用乐观锁 mysql的版本号机制
        // 方案3 适应cas的思想更新的时候判断以下当前库存是否大于要减少的库存
        if (updateStockDto.getType() == 1) {
            lockStockByCas(updateStockDto);
        } else {
            TakeoutDishPo takeoutDishPo = getById(updateStockDto.getProductId());
            UpdateChain.of(TAKEOUT_DISH_PO)
                    .set(TAKEOUT_DISH_PO.STOCK, updateStockDto.getStock() + takeoutDishPo.getStock())
                    .where(TAKEOUT_DISH_PO.ID.eq(updateStockDto.getProductId()))
                    .update();
        }
        return true;
    }

    @Override
    public PageResult<TakeoutDishResultVo> queryDishList(QueryDishDto queryDishDto) {
        QueryWrapper queryWrapper = QueryWrapper
                .create()
                .select(TAKEOUT_DISH_PO.ID)
                .from(TAKEOUT_DISH_PO)
                .where("1=1")
                .and(TAKEOUT_DISH_PO.ID.eq(queryDishDto.getId()).when(ObjectUtil.isNotNull(queryDishDto.getId()))
                        .and(TAKEOUT_DISH_PO.NAME.like(queryDishDto.getName()).when(StrUtil.isNotBlank(queryDishDto.getName()))
                                .and(TAKEOUT_DISH_PO.CATEGORY_ID.eq(queryDishDto.getCategoryId()).when(ObjectUtil.isNotNull(queryDishDto.getCategoryId()))
                                        .and(TAKEOUT_DISH_PO.PRICE.ge(queryDishDto.getMinPrice()).when(ObjectUtil.isNotNull(queryDishDto.getMinPrice()))
                                                .and(TAKEOUT_DISH_PO.PRICE.le(queryDishDto.getMaxPrice()).when(ObjectUtil.isNotNull(queryDishDto.getMaxPrice())))))));
        List<Long> list = this.listAs(queryWrapper, Long.class);
        List<TakeoutDishResultVo> takeoutDishResultVoList = this.getDishes(list);
        return PageUtil.startPage(queryDishDto, takeoutDishResultVoList);
    }

    private void validate(AddDishDto addDishDto) {
        if (this.count(TAKEOUT_DISH_PO.NAME.eq(addDishDto.getName()).and(TAKEOUT_DISH_PO.CATEGORY_ID.eq(addDishDto.getCategoryId()))) > 0) {
            ExceptionUtil.castServiceException(DISH_HAS_ALREADY_EXISTS);
        }
        if (takeoutCategoryMapper.selectCountByCondition(TAKEOUT_CATEGORY_PO.ID.eq(addDishDto.getCategoryId())) < 1) {
            ExceptionUtil.castServiceException(CATEGORY_NOT_EXISTS);
        }
    }

    // 方案1 使用分布式锁 redisson
    private void lockStockByRedisson(UpdateStockDto updateStockDto) {
        Long productId = updateStockDto.getProductId();
        try {
            boolean locked = distributedLock.tryLock(REDISSON_PREFIX + productId);
            if (!locked) {
                ExceptionUtil.castServiceException(DISH_STOCK_IS_REDUCING);
            }
            // 这里是真正的更新库存的逻辑
            TakeoutDishPo takeoutDishPo = getSelf().getById(productId);
            Integer stock = takeoutDishPo.getStock();
            if (stock < updateStockDto.getStock()) {
                ExceptionUtil.castServiceException(DISH_STOCK_IS_NOT_ENOUGH);
            }
            takeoutDishPo.setStock(stock - updateStockDto.getStock());
            getSelf().updateById(takeoutDishPo);
        } finally {
            distributedLock.unlock(REDISSON_PREFIX + productId);
        }
    }

    private TakeoutDishServiceImpl getSelf() {
        return SpringUtil.getBean(this.getClass());
    }

    // 方案2 使用mysql乐观锁 这里暂时不实现 思路就是要么使用flex内置的@cloumn(version) 要么更新之前先查一下 然后更新的时候对比版本号

    // 方案3 适应cas的思想更新的时候判断以下当前库存是否大于要减少的库存
    private void lockStockByCas(UpdateStockDto updateStockDto) {
        Long productId = updateStockDto.getProductId();
        TakeoutDishPo takeoutDishPo = getSelf().getById(productId);
        boolean update = UpdateChain.of(TAKEOUT_DISH_PO)
                .set(TAKEOUT_DISH_PO.STOCK, takeoutDishPo.getStock() - updateStockDto.getStock())
                .where(TAKEOUT_DISH_PO.STOCK.gt(updateStockDto.getStock()))
                .where(TAKEOUT_DISH_PO.ID.eq(productId))
                .update();
        if (!update) {
            ExceptionUtil.castServiceException(DISH_STOCK_IS_NOT_ENOUGH);
        }
    }
}
