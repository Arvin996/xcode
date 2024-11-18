package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.BatchSaveTablesDto;
import cn.xk.xcode.entity.dto.GenerateCodeDto;
import cn.xk.xcode.entity.dto.UpdateDatabaseConnInfoPoDto;
import cn.xk.xcode.pojo.CommonResult;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.TableInfoPo;

import java.util.List;

/**
 *  服务层。
 *
 * @author lenovo
 * @since 2024-06-25
 */
public interface TableInfoService extends IService<TableInfoPo> {

    CommonResult<Boolean> loadTableInfo(BatchSaveTablesDto batchSaveTablesDto);

    List<TableInfoPo> refreshTables(UpdateDatabaseConnInfoPoDto updateDatabaseConnInfoPoDto);

    CommonResult<String> generateCode(GenerateCodeDto generateCodeDto);
}
