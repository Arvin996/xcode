package cn.xk.xcode.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.entity.dto.category.AddCategoryDto;
import cn.xk.xcode.entity.dto.category.QueryCategoryDto;
import cn.xk.xcode.entity.dto.category.TakeoutCategoryBaseDto;
import cn.xk.xcode.entity.dto.category.UpdateCategoryDto;
import cn.xk.xcode.entity.vo.category.CategoryResultVo;
import cn.xk.xcode.enums.CategoryEnum;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.service.TakeoutDishService;
import cn.xk.xcode.service.TakeoutSetmealService;
import cn.xk.xcode.utils.object.BeanUtil;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.TakeoutCategoryPo;
import cn.xk.xcode.mapper.TakeoutCategoryMapper;
import cn.xk.xcode.service.TakeoutCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static cn.xk.xcode.constant.GlobalTakeoutProductConstants.CATEGORY_HAS_ALREADY_EXISTS;
import static cn.xk.xcode.constant.GlobalTakeoutProductConstants.CATEGORY_HAS_USED;
import static cn.xk.xcode.entity.def.TakeoutCategoryTableDef.TAKEOUT_CATEGORY_PO;
import static cn.xk.xcode.entity.def.TakeoutDishTableDef.TAKEOUT_DISH_PO;
import static cn.xk.xcode.entity.def.TakeoutSetmealTableDef.TAKEOUT_SETMEAL_PO;

/**
 * 服务层实现。
 *
 * @author Administrator
 * @since 2024-10-31
 */
@Service
public class TakeoutCategoryServiceImpl extends ServiceImpl<TakeoutCategoryMapper, TakeoutCategoryPo> implements TakeoutCategoryService {

    @Resource
    private TakeoutDishService takeoutDishService;

    @Resource
    private TakeoutSetmealService takeoutSetmealService;

    @Override
    public Boolean addCategory(AddCategoryDto addCategoryDto) {
        validate(addCategoryDto);
        Integer sort = addCategoryDto.getSort();
        if (Objects.isNull(sort)) {
            QueryWrapper queryWrapper = QueryWrapper.create().select(QueryMethods.max(TAKEOUT_CATEGORY_PO.SORT))
                    .from(TAKEOUT_CATEGORY_PO).where(TAKEOUT_CATEGORY_PO.TYPE.eq(addCategoryDto.getType()));
            sort = this.getObjAs(queryWrapper, Integer.class) + 1;
        }
        TakeoutCategoryPo takeoutCategoryPo = BeanUtil.toBean(addCategoryDto, TakeoutCategoryPo.class);
        takeoutCategoryPo.setSort(sort);
        return this.save(takeoutCategoryPo);
    }

    @Override
    public Boolean delCategory(TakeoutCategoryBaseDto takeoutCategoryBaseDto) {
        validate(takeoutCategoryBaseDto);
        return this.removeById(takeoutCategoryBaseDto.getId());
    }

    @Override
    public Boolean updateCategory(UpdateCategoryDto updateCategoryDto) {
        if (this.count(TAKEOUT_CATEGORY_PO.TYPE.eq(updateCategoryDto.getType()).and(TAKEOUT_CATEGORY_PO.NAME.eq(updateCategoryDto.getName()).and(TAKEOUT_CATEGORY_PO.ID.ne(updateCategoryDto.getId())))) > 0) {
            ExceptionUtil.castServiceException(CATEGORY_HAS_ALREADY_EXISTS, CategoryEnum.getTypeName(updateCategoryDto.getType()), updateCategoryDto.getName());
        }
        return this.updateById(BeanUtil.toBean(updateCategoryDto, TakeoutCategoryPo.class));
    }

    @Override
    public List<CategoryResultVo> getCategoryList(QueryCategoryDto queryCategoryDto) {
        QueryWrapper queryWrapper = QueryWrapper
                .create()
                .where(TAKEOUT_CATEGORY_PO.ID.eq(queryCategoryDto.getId()).when(ObjUtil.isNotNull(queryCategoryDto.getId())))
                .and(TAKEOUT_CATEGORY_PO.TYPE.eq(queryCategoryDto.getType()).when(ObjUtil.isNotNull(queryCategoryDto.getType())))
                .and(TAKEOUT_CATEGORY_PO.NAME.like(queryCategoryDto.getName()).when(StrUtil.isNotBlank(queryCategoryDto.getName())))
                .orderBy(TAKEOUT_CATEGORY_PO.SORT, true);
        return this.listAs(queryWrapper, CategoryResultVo.class);
    }

    private void validate(TakeoutCategoryBaseDto takeoutCategoryBaseDto) {
        if (takeoutDishService.count(TAKEOUT_DISH_PO.CATEGORY_ID.eq(takeoutCategoryBaseDto.getId())) > 0) {
            ExceptionUtil.castServiceException(CATEGORY_HAS_USED);
        }
        if (takeoutSetmealService.count(TAKEOUT_SETMEAL_PO.CATEGORY_ID.eq(takeoutCategoryBaseDto.getId())) > 0) {
            ExceptionUtil.castServiceException(CATEGORY_HAS_USED);
        }
    }

    private void validate(AddCategoryDto addCategoryDto) {
        String name = addCategoryDto.getName();
        Integer type = addCategoryDto.getType();
        if (this.exists(TAKEOUT_CATEGORY_PO.NAME.eq(name).and(TAKEOUT_CATEGORY_PO.TYPE.eq(type)))) {
            ExceptionUtil.castServiceException(CATEGORY_HAS_ALREADY_EXISTS, CategoryEnum.getTypeName(type), name);
        }
    }
}
