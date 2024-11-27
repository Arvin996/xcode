package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.*;
import cn.xk.xcode.entity.vo.GenCodePreviewVo;
import cn.xk.xcode.entity.vo.GenTableColumnVo;
import cn.xk.xcode.entity.vo.GenTableResultVo;
import cn.xk.xcode.pojo.PageResult;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.GenTablePo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *  服务层。
 *
 * @author xuk
 * @since 2024-11-25
 */
public interface GenTableService extends IService<GenTablePo> {

    PageResult<GenTableResultVo> genTableList(QueryGenTableDto queryGenTableDto);

    Boolean createTableBySql(CreateGenTableDto createGenTableDto);

    List<GenTableResultVo> queryDbTableList(QueryGenTableDto queryGenTableDto);

    List<GenTableColumnVo> queryDbTableColumnList(Long tableId);

    Boolean importTableSave(ImportTableDto importTableDto);

    Boolean updateGenTable(UpdateGenTableDto updateGenTableDto);

    Boolean removeGenTable(DelGenTableDto delGenTableDto);

    GenCodePreviewVo preview(Long tableId);

    void download(HttpServletResponse response, String tableName) throws IOException;

    Boolean syncDb(String tableName);

    void batchGenCode(HttpServletResponse response, String tables) throws IOException;
}
