package cn.xk.xcode.mapper;

import cn.xk.xcode.entity.dish.TakeoutDishResultVo;
import org.apache.ibatis.annotations.Mapper;
import com.mybatisflex.core.BaseMapper;
import cn.xk.xcode.entity.po.TakeoutDishPo;

import java.util.List;
import java.util.Map;

/**
 *  映射层。
 *
 * @author Administrator
 * @since 2024-10-31
 */
@Mapper
public interface TakeoutDishMapper extends BaseMapper<TakeoutDishPo> {

    List<TakeoutDishResultVo> getDishList(Map<String, Object> map);
}
