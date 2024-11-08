package cn.xk.xcode.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.mybatisflex.core.BaseMapper;
import cn.xk.xcode.entity.po.TakeoutOrderDetailPo;

/**
 *  映射层。
 *
 * @author xuk
 * @since 2024-11-06
 */
@Mapper
public interface TakeoutOrderDetailMapper extends BaseMapper<TakeoutOrderDetailPo> {

}
