package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.category.AddCategoryDto;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.TakeoutCategoryPo;

/**
 *  服务层。
 *
 * @author Administrator
 * @since 2024-10-31
 */
public interface TakeoutCategoryService extends IService<TakeoutCategoryPo> {

    Boolean addCategory(AddCategoryDto addCategoryDto);
}
