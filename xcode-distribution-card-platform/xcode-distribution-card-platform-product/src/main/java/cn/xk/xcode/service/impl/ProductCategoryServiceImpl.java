package cn.xk.xcode.service.impl;

import cn.xk.xcode.entity.dto.category.AddCategoryDto;
import cn.xk.xcode.entity.dto.category.UpdateCategoryDto;
import cn.xk.xcode.entity.vo.category.ProductCategoryVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.service.ProductProdCategoryService;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.ProductCategoryPo;
import cn.xk.xcode.mapper.ProductCategoryMapper;
import cn.xk.xcode.service.ProductCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.*;

import static cn.xk.xcode.config.DistributionCardProductErrorCode.*;
import static cn.xk.xcode.entity.def.ProductCategoryTableDef.PRODUCT_CATEGORY_PO;
import static cn.xk.xcode.entity.def.ProductProdCategoryTableDef.PRODUCT_PROD_CATEGORY_PO;
import static cn.xk.xcode.entity.def.ProductSpuTableDef.PRODUCT_SPU_PO;

/**
 * 服务层实现。
 *
 * @author xuk
 * @since 2025-05-30
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategoryPo> implements ProductCategoryService {

    @Resource
    private ProductProdCategoryService productProdCategoryService;

    @Override
    public Boolean addProductCategory(AddCategoryDto addCategoryDto) {
        String name = addCategoryDto.getName();
        if (LogicDeleteManager.execWithoutLogicDelete(() -> this.exists(PRODUCT_CATEGORY_PO.NAME.eq(name).and(PRODUCT_CATEGORY_PO.PARENT_ID.eq(addCategoryDto.getParentId()))))) {
            ExceptionUtil.castServiceException(CATEGORY_NAME_ALREADY_EXISTS);
        }
        Long parentId = addCategoryDto.getParentId();
        if (ObjectUtil.isNotNull(parentId)) {
            if (!this.exists(PRODUCT_CATEGORY_PO.ID.eq(parentId))) {
                ExceptionUtil.castServiceException(PARENT_CATEGORY_NOT_EXISTS);
            }
            addCategoryDto.setParentId(0L);
        }
        ProductCategoryPo productCategoryPo = BeanUtil.toBean(addCategoryDto, ProductCategoryPo.class);
        save(productCategoryPo);
        productCategoryPo.setSort(productCategoryPo.getId());
        return updateById(productCategoryPo);

    }

    @Override
    public Boolean updateProductCategory(UpdateCategoryDto updateCategoryDto) {
        ProductCategoryPo productCategoryPo = this.getById(updateCategoryDto.getId());
        if (ObjectUtil.isNull(productCategoryPo)) {
            ExceptionUtil.castServiceException(CATEGORY_NOT_EXISTS);
        }
        if (LogicDeleteManager.execWithoutLogicDelete(() -> this.exists(PRODUCT_CATEGORY_PO.NAME.eq(updateCategoryDto.getName())
                .and(PRODUCT_CATEGORY_PO.ID.ne(updateCategoryDto.getId()))
                .and(PRODUCT_CATEGORY_PO.PARENT_ID.eq(productCategoryPo.getParentId()))))) {
            ExceptionUtil.castServiceException(CATEGORY_NAME_ALREADY_EXISTS);
        }
        return updateById(BeanUtil.toBean(updateCategoryDto, ProductCategoryPo.class));
    }

    @Transactional
    @Override
    public Boolean delProductCategory(Long categoryId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(QueryMethods.count(PRODUCT_PROD_CATEGORY_PO.ID))
                .from(PRODUCT_PROD_CATEGORY_PO)
                .leftJoin(PRODUCT_SPU_PO)
                .on(PRODUCT_PROD_CATEGORY_PO.SPU_ID.eq(PRODUCT_SPU_PO.ID))
                .where(PRODUCT_PROD_CATEGORY_PO.IS_DELETED.eq("0"))
                .and(PRODUCT_SPU_PO.IS_DELETED.eq("0"))
                .and(PRODUCT_SPU_PO.STATUS.eq("0"));
        Integer count = productProdCategoryService.getOneAs(queryWrapper, Integer.class);
        if (count > 0) {
            ExceptionUtil.castServiceException(CATEGORY_HAS_SKU);
        }
        if (this.exists(PRODUCT_CATEGORY_PO.ID.eq(categoryId))) {
            ExceptionUtil.castServiceException(CATEGORY_HAS_CHILDREN);
        }
        removeById(categoryId);
        return productProdCategoryService.remove(PRODUCT_PROD_CATEGORY_PO.CATEGORY_ID.eq(categoryId));
    }

    @Override
    public List<ProductCategoryVo> queryProductCategory() {
        List<ProductCategoryVo> productCategoryVoList = QueryChain.of(ProductCategoryPo.class)
                .select(PRODUCT_CATEGORY_PO.ALL_COLUMNS)
                .from(PRODUCT_CATEGORY_PO)
                .orderBy(PRODUCT_CATEGORY_PO.PARENT_ID, true)
                .orderBy(PRODUCT_CATEGORY_PO.SORT, true)
                .listAs(ProductCategoryVo.class);
        Map<Long, ProductCategoryVo> categoryVoMap = new HashMap<>();
        for (ProductCategoryVo productCategoryVo : productCategoryVoList) {
            categoryVoMap.put(productCategoryVo.getId(), productCategoryVo);
        }
        List<ProductCategoryVo> result = new ArrayList<>();
        for (ProductCategoryVo productCategoryVo : productCategoryVoList) {
            if (productCategoryVo.getParentId() == 0) {
                productCategoryVo.setChildren(new ArrayList<>());
                result.add(productCategoryVo);
            } else {
                Long parentId = productCategoryVo.getParentId();
                if (categoryVoMap.containsKey(parentId)) {
                    ProductCategoryVo parentCategoryVo = categoryVoMap.get(parentId);
                    if (CollectionUtil.isEmpty(parentCategoryVo.getChildren())) {
                        parentCategoryVo.setChildren(new ArrayList<>());
                    }
                    parentCategoryVo.getChildren().add(productCategoryVo);
                }
            }
        }
        return result;
    }
}
