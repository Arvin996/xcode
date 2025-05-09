package cn.xk.xcode.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.mybatisflex.core.BaseMapper;
import cn.xk.xcode.entity.po.SystemApiPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *  映射层。
 *
 * @author xuk
 * @since 2025-05-09
 */
@Mapper
public interface SystemApiMapper extends BaseMapper<SystemApiPo> {
}
