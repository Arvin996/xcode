package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.sku.AddSkuDto;
import cn.xk.xcode.entity.dto.sku.UpdateSkuDto;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.ProductSkuPo;

/**
 *  服务层。
 *
 * @author xuk
 * @since 2025-05-30
 */
public interface ProductSkuService extends IService<ProductSkuPo> {

    Boolean addProductSku(AddSkuDto addSkuDto);

    Boolean updateProductSku(UpdateSkuDto updateSkuDto);

    Boolean delProductSku(Long skuId);
}
