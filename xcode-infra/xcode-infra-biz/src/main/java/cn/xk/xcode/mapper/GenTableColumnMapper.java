package cn.xk.xcode.mapper;

import cn.xk.xcode.entity.vo.GenTableColumnVo;
import org.apache.ibatis.annotations.Mapper;
import com.mybatisflex.core.BaseMapper;
import cn.xk.xcode.entity.po.GenTableColumnPo;

import java.util.List;

/**
 *  映射层。
 *
 * @author xuk
 * @since 2024-11-25
 */
@Mapper
public interface GenTableColumnMapper extends BaseMapper<GenTableColumnPo> {

    List<GenTableColumnVo> queryDbTableColumnList(Long tableId);

    List<GenTableColumnVo> selectDbTableColumnsByName(String tableName);

    void updateGenTableColumn(GenTableColumnPo cenTableColumn);
}
