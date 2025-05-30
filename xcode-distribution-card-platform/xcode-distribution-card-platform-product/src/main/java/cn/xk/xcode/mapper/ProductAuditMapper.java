package cn.xk.xcode.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.mybatisflex.core.BaseMapper;
import cn.xk.xcode.entity.po.ProductAuditPo;

/**
 *  映射层。
 *
 * @author xuk
 * @since 2025-05-30
 */
@Mapper
public interface ProductAuditMapper extends BaseMapper<ProductAuditPo> {

}
