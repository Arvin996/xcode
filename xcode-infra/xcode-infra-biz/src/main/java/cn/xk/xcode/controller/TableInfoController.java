package cn.xk.xcode.controller;

import cn.xk.xcode.entity.dto.BatchSaveTablesDto;
import cn.xk.xcode.entity.dto.GenerateCodeDto;
import cn.xk.xcode.entity.dto.UpdateDatabaseConnInfoPoDto;
import cn.xk.xcode.entity.dto.UpdateTableInfoDto;
import cn.xk.xcode.entity.po.DatabaseConnInfoPo;
import cn.xk.xcode.entity.po.TableInfoPo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.TableInfoService;
import cn.xk.xcode.utils.object.BeanUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static cn.xk.xcode.entity.def.TableInfoTableDef.TABLE_INFO_PO;

/**
 * @Author xuk
 * @Date 2024/6/25 09:56
 * @Version 1.0
 * @Description TableInfoController
 */
@RestController
@Tag(name = "数据库表信息接口")
@RequestMapping("/tbinfo")
public class TableInfoController {
    @Resource
    private TableInfoService tableInfoService;

    @Operation(description = "根据数据库连接信息，一建导入数据库中所有的表信息")
    @PostMapping("/loadTableInfo")
    public CommonResult<Boolean> loadTableInfo(@Validated @RequestBody BatchSaveTablesDto batchSaveTablesDto) {
        return tableInfoService.loadTableInfo(batchSaveTablesDto);
    }

    @Operation(description = "更新表信息")
    @PostMapping("/updateTableInfo")
    public CommonResult<Boolean> updateTableInfo(@Validated @RequestBody UpdateTableInfoDto updateTableInfoDto) {
        TableInfoPo tableInfoPo = BeanUtil.toBean(updateTableInfoDto, TableInfoPo.class);
        return CommonResult.success(tableInfoService.updateById(tableInfoPo));
    }

    @Operation(description = "删除表信息")
    @PostMapping("/delTableInfo")
    public CommonResult<Boolean> delTableInfo(@Validated @RequestBody UpdateTableInfoDto updateTableInfoDto) {
        TableInfoPo tableInfoPo = BeanUtil.toBean(updateTableInfoDto, TableInfoPo.class);
        return CommonResult.success(tableInfoService.removeById(tableInfoPo));
    }

    @Operation(description = "根据数据库查询所有表")
    @PostMapping("/queryTables")
    public CommonResult<List<TableInfoPo>> queryTables(@Validated @RequestBody UpdateDatabaseConnInfoPoDto updateDatabaseConnInfoPoDto) {
        return CommonResult.success(tableInfoService.list(TABLE_INFO_PO.DATABASE_ID.eq(updateDatabaseConnInfoPoDto.getId())));
    }

    @Operation(description = "刷新数据库表")
    @PostMapping("/refreshTables")
    public CommonResult<List<TableInfoPo>> refreshTables(@Validated @RequestBody UpdateDatabaseConnInfoPoDto updateDatabaseConnInfoPoDto) {
        return CommonResult.success(tableInfoService.refreshTables(updateDatabaseConnInfoPoDto));
    }

    @Operation(description = "生成代码")
    @PostMapping("/generateCode")
    public CommonResult<String> generateCode(@Validated @RequestBody GenerateCodeDto generateCodeDto) {
      return tableInfoService.generateCode(generateCodeDto);
    }

}
