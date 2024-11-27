package cn.xk.xcode.mapper;

import cn.xk.xcode.entity.dto.ImportTableDto;
import cn.xk.xcode.entity.dto.QueryGenTableDto;
import cn.xk.xcode.entity.vo.GenTableResultVo;
import org.apache.ibatis.annotations.Mapper;
import com.mybatisflex.core.BaseMapper;
import cn.xk.xcode.entity.po.GenTablePo;

import java.util.Collection;
import java.util.List;

/**
 *  映射层。
 *
 * @author xuk
 * @since 2024-11-25
 */
@Mapper
public interface GenTableMapper extends BaseMapper<GenTablePo> {

    List<GenTableResultVo> selectGenTableList(QueryGenTableDto queryGenTableDto);

    int createTable(String sql);

    List<GenTableResultVo> queryDbTableList(QueryGenTableDto queryGenTableDto);

    List<GenTableResultVo> selectDbTableListByNames(Collection<String> array);
}
