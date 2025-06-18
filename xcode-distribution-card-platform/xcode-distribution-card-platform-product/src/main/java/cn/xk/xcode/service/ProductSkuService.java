package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.sku.AddSkuDto;
import cn.xk.xcode.entity.dto.sku.UpdateSkuDto;
import cn.xk.xcode.entity.vo.sku.ProductSkuVo;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.ProductSkuPo;

import java.util.List;

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

    List<ProductSkuVo> queryProductSku(Long spuId);
}
