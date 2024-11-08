package cn.xk.xcode.mapper;

import cn.xk.xcode.entity.TakeoutOrderResultVo;
import org.apache.ibatis.annotations.Mapper;
import com.mybatisflex.core.BaseMapper;
import cn.xk.xcode.entity.po.TakeoutOrdersPo;

import java.util.List;
import java.util.Map;

/**
 *  映射层。
 *
 * @author xuk
 * @since 2024-11-06
 */
@Mapper
public interface TakeoutOrdersMapper extends BaseMapper<TakeoutOrdersPo> {
    List<TakeoutOrderResultVo> getTakeoutOrderResult(Map<String, Object> params);
}
