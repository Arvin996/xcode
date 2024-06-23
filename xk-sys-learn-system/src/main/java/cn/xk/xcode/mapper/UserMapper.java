package cn.xk.xcode.mapper;

import com.mybatisflex.core.BaseMapper;
import cn.xk.xcode.entity.po.UserPo;
import org.apache.ibatis.annotations.Mapper;

/**
 *  映射层。
 *
 * @author Arvin
 * @since 2024-06-21
 */
@Mapper
public interface UserMapper extends BaseMapper<UserPo> {

}
