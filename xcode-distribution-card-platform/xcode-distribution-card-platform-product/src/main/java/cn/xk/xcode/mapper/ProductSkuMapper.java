package cn.xk.xcode.mapper;

import cn.xk.xcode.entity.dto.sku.QuerySkuDto;
import cn.xk.xcode.entity.vo.sku.ProductSkuVo;
import org.apache.ibatis.annotations.Mapper;
import com.mybatisflex.core.BaseMapper;
import cn.xk.xcode.entity.po.ProductSkuPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  映射层。
 *
 * @author xuk
 * @since 2025-05-30
 */
@Mapper
public interface ProductSkuMapper extends BaseMapper<ProductSkuPo> {

    List<ProductSkuVo> queryProductSku(@Param("querySkuDto") QuerySkuDto querySkuDto);
}
