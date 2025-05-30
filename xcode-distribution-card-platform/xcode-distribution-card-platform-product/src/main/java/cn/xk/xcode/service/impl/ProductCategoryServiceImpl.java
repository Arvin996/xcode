package cn.xk.xcode.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.ProductCategoryPo;
import cn.xk.xcode.mapper.ProductCategoryMapper;
import cn.xk.xcode.service.ProductCategoryService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author xuk
 * @since 2025-05-30
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategoryPo> implements ProductCategoryService {

}
