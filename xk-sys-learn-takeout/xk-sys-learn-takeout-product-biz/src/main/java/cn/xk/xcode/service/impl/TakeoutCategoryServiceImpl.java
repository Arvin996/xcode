package cn.xk.xcode.service.impl;

import cn.xk.xcode.entity.dto.category.AddCategoryDto;
import cn.xk.xcode.enums.CategoryEnum;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.TakeoutCategoryPo;
import cn.xk.xcode.mapper.TakeoutCategoryMapper;
import cn.xk.xcode.service.TakeoutCategoryService;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static cn.xk.xcode.constant.GlobalTakeoutProductConstants.CATEGORY_HAS_ALREADY_EXISTS;
import static cn.xk.xcode.entity.def.TakeoutCategoryTableDef.TAKEOUT_CATEGORY_PO;

/**
 * 服务层实现。
 *
 * @author Administrator
 * @since 2024-10-31
 */
@Service
public class TakeoutCategoryServiceImpl extends ServiceImpl<TakeoutCategoryMapper, TakeoutCategoryPo> implements TakeoutCategoryService {

    @Override
    public Boolean addCategory(AddCategoryDto addCategoryDto) {
        validate(addCategoryDto);
        Integer sort = addCategoryDto.getSort();
        if (Objects.isNull(sort))  {
            QueryWrapper queryWrapper = QueryWrapper.create().select(QueryMethods.max(TAKEOUT_CATEGORY_PO.SORT))
                    .from(TAKEOUT_CATEGORY_PO).where(TAKEOUT_CATEGORY_PO.TYPE.eq(addCategoryDto.getType()));
            sort = this.getObjAs(queryWrapper, Integer.class) + 1;
        }
        TakeoutCategoryPo takeoutCategoryPo = BeanUtil.toBean(addCategoryDto, TakeoutCategoryPo.class);
        takeoutCategoryPo.setSort(sort);
        return this.save(takeoutCategoryPo);
    }

    private void validate(AddCategoryDto addCategoryDto) {
        String name = addCategoryDto.getName();
        Integer type = addCategoryDto.getType();
        if (this.exists(TAKEOUT_CATEGORY_PO.NAME.eq(name).and(TAKEOUT_CATEGORY_PO.TYPE.eq(type)))) {
            ExceptionUtil.castServiceException(CATEGORY_HAS_ALREADY_EXISTS, CategoryEnum.getTypeName(type), name);
        }
    }
}
