package cn.xk.xcode.service.api;

import cn.xk.xcode.entity.dto.sku.QuerySkuDto;
import cn.xk.xcode.entity.vo.api.ProductIndexVo;
import cn.xk.xcode.entity.vo.category.ProductCategoryVo;
import cn.xk.xcode.entity.vo.sku.ProductSkuVo;
import cn.xk.xcode.mapper.ProductSkuMapper;
import cn.xk.xcode.service.*;
import cn.xk.xcode.utils.collections.CollectionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static cn.xk.xcode.entity.def.ProductProdCategoryTableDef.PRODUCT_PROD_CATEGORY_PO;

/**
 * @Author xuk
 * @Date 2025/6/6 16:15
 * @Version 1.0.0
 * @Description HomeService
 **/
@Service
public class HomeService {

    @Resource
    private ProductCategoryService productCategoryService;

    @Resource
    private ProductProdCategoryService productProdCategoryService;

    @Resource
    private ProductSkuMapper productSkuMapper;


    public ProductIndexVo getProductIndex() {
        ProductIndexVo productIndexVo = new ProductIndexVo();
        List<ProductIndexVo.ProductCategoryNumVo> productCategoryNumVos = new ArrayList<>();
        // // 获取每个一级分类下的spu数量
        List<ProductCategoryVo> productCategoryVoList = productCategoryService.queryProductCategory();
        if (CollectionUtil.isNotEmpty(productCategoryVoList)) {
            for (ProductCategoryVo productCategoryVo : productCategoryVoList) {
                ProductIndexVo.ProductCategoryNumVo productCategoryNumVo = new ProductIndexVo.ProductCategoryNumVo();
                productCategoryNumVo.setCategoryId(productCategoryVo.getId());
                productCategoryNumVo.setCategoryName(productCategoryVo.getName());
                productCategoryNumVo.setSpuCount(0);
                List<Long> childList = new ArrayList<>();
                getProductCategoryChildIdList(productCategoryVo, childList);
                if (CollectionUtil.isNotEmpty(childList)) {
                    long count = productProdCategoryService.count(PRODUCT_PROD_CATEGORY_PO.CATEGORY_ID.in(childList));
                    productCategoryNumVo.setSpuCount((int) count);
                }
                productCategoryNumVos.add(productCategoryNumVo);
            }
        }
        productIndexVo.setCategoryNumVoList(productCategoryNumVos);
        QuerySkuDto querySkuDto = new QuerySkuDto();
        querySkuDto.setOrderBySale(true);
        querySkuDto.setLimit(3);
        List<ProductSkuVo> productSkuVos = productSkuMapper.queryProductSku(querySkuDto);
        productIndexVo.setWeeklyBestSkuList(productSkuVos);
        return productIndexVo;
    }

    private void getProductCategoryChildIdList(ProductCategoryVo parent, List<Long> list) {
        // 获取最小子分类id
        if (CollectionUtil.isEmpty(parent.getChildren())) {
            list.add(parent.getId());
            return;
        }
        for (ProductCategoryVo child : parent.getChildren()) {
            getProductCategoryChildIdList(child, list);
        }
    }
}
