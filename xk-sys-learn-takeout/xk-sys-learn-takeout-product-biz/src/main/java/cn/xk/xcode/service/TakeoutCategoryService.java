package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.category.AddCategoryDto;
import cn.xk.xcode.entity.dto.category.QueryCategoryDto;
import cn.xk.xcode.entity.dto.category.TakeoutCategoryBaseDto;
import cn.xk.xcode.entity.dto.category.UpdateCategoryDto;
import cn.xk.xcode.entity.vo.category.CategoryResultVo;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.TakeoutCategoryPo;

import java.util.List;

/**
 *  服务层。
 *
 * @author Administrator
 * @since 2024-10-31
 */
public interface TakeoutCategoryService extends IService<TakeoutCategoryPo> {

    Boolean addCategory(AddCategoryDto addCategoryDto);

    Boolean delCategory(TakeoutCategoryBaseDto takeoutCategoryBaseDto);

    Boolean updateCategory(UpdateCategoryDto updateCategoryDto);

    List<CategoryResultVo> getCategoryList(QueryCategoryDto queryCategoryDto);
}
