package cn.xk.xcode.mapper;

import cn.xk.xcode.entity.dto.dict.QueryDictDto;
import cn.xk.xcode.entity.vo.dict.SysDictDataVo;
import org.apache.ibatis.annotations.Mapper;
import com.mybatisflex.core.BaseMapper;
import cn.xk.xcode.entity.po.SystemDictTypePo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  映射层。
 *
 * @author xuk
 * @since 2025-05-09
 */
@Mapper
public interface SystemDictTypeMapper extends BaseMapper<SystemDictTypePo> {

    List<SysDictDataVo> selectAllDictDate(@Param("queryDictDto") QueryDictDto queryDictDto);
}
