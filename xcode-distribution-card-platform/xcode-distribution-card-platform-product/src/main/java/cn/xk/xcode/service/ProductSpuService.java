package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.spu.AddSpuDto;
import cn.xk.xcode.entity.dto.spu.QuerySpuDto;
import cn.xk.xcode.entity.dto.spu.UpdateSpuDto;
import cn.xk.xcode.entity.vo.spu.ProductSpuVo;
import cn.xk.xcode.pojo.PageResult;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.ProductSpuPo;

/**
 *  服务层。
 *
 * @author xuk
 * @since 2025-05-30
 */
public interface ProductSpuService extends IService<ProductSpuPo> {

    Boolean addProductSpu(AddSpuDto addSpuDto);

    Boolean updateProductSpu(UpdateSpuDto updateSpuDto);

    Boolean delProductSpu(Long spuId);

    PageResult<ProductSpuVo> queryProductSpu(QuerySpuDto querySpuDto);
}
