package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.category.AddCategoryDto;
import cn.xk.xcode.entity.dto.category.UpdateCategoryDto;
import cn.xk.xcode.entity.vo.category.ProductCategoryVo;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.ProductCategoryPo;

import java.util.List;

/**
 *  服务层。
 *
 * @author xuk
 * @since 2025-05-30
 */
public interface ProductCategoryService extends IService<ProductCategoryPo> {

    Boolean addProductCategory(AddCategoryDto addCategoryDto);

    Boolean updateProductCategory(UpdateCategoryDto updateCategoryDto);

    Boolean delProductCategory(Long categoryId);

    List<ProductCategoryVo> queryProductCategory();
}
