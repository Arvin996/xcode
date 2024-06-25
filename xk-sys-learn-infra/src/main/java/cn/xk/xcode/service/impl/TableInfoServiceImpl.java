package cn.xk.xcode.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.xk.xcode.entity.dto.BatchSaveTablesDto;
import cn.xk.xcode.entity.dto.GenerateCodeDto;
import cn.xk.xcode.entity.dto.UpdateDatabaseConnInfoPoDto;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.impl.generate.CodeGenerate;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.TableInfoPo;
import cn.xk.xcode.mapper.TableInfoMapper;
import cn.xk.xcode.service.TableInfoService;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static cn.xk.xcode.entity.def.TableInfoTableDef.TABLE_INFO_PO;

/**
 * 服务层实现。
 *
 * @author lenovo
 * @since 2024-06-25
 */
@Service
public class TableInfoServiceImpl extends ServiceImpl<TableInfoMapper, TableInfoPo> implements TableInfoService {

    @Override
    public CommonResult<Boolean> loadTableInfo(BatchSaveTablesDto batchSaveTablesDto) {
        HikariDataSource dataSource = new HikariDataSource();
        String databaseName = batchSaveTablesDto.getDatabaseName();
        String url = batchSaveTablesDto.getUrl();
        String username = batchSaveTablesDto.getUserName();
        String password = batchSaveTablesDto.getPassword();
        String jdbcUrl = "jdbc:mysql://" + url + "/" + databaseName + "?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setJdbcUrl(jdbcUrl);
        try {
            Connection connection = dataSource.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            List<TableInfoPo> tableInfoPoList = new ArrayList<>();
            while (resultSet.next()) {

                tableInfoPoList.add(TableInfoPo
                        .builder()
                        .tablePre(batchSaveTablesDto.getTablePre())
                        .entitySuff(batchSaveTablesDto.getEntitySuff() == null ? "po" : batchSaveTablesDto.getEntitySuff())
                        .createUser(StpUtil.getLoginIdAsString())
                        .databaseId(batchSaveTablesDto.getId())
                        .tableName(resultSet.getString("TABLE_NAME"))
                        .build());
            }
            resultSet.close();
            connection.close();
            this.saveBatch(tableInfoPoList);
        } catch (SQLException e) {
            return CommonResult.error(new ServiceException("数据库连接失败"));
        } finally {
            dataSource.close();
        }

        return CommonResult.success(true);
    }

    @Transactional
    @Override
    public List<TableInfoPo> refreshTables(UpdateDatabaseConnInfoPoDto updateDatabaseConnInfoPoDto) {
        TableInfoPo tableInfoPo = this.getOne(QueryWrapper.create().where(TABLE_INFO_PO.DATABASE_ID.eq(updateDatabaseConnInfoPoDto.getId())).limit(1));
        if (Objects.isNull(tableInfoPo)) {
            return Collections.emptyList();
        }
        this.remove(TABLE_INFO_PO.DATABASE_ID.eq(updateDatabaseConnInfoPoDto.getId()));
        BatchSaveTablesDto batchSaveTablesDto = new BatchSaveTablesDto();
        batchSaveTablesDto.setId(updateDatabaseConnInfoPoDto.getId());
        batchSaveTablesDto.setTablePre(tableInfoPo.getTablePre());
        batchSaveTablesDto.setEntitySuff(tableInfoPo.getEntitySuff());
        this.loadTableInfo(batchSaveTablesDto);
        return this.list(TABLE_INFO_PO.DATABASE_ID.eq(updateDatabaseConnInfoPoDto.getId()));
    }

    @Override
    public CommonResult<String> generateCode(GenerateCodeDto generateCodeDto) {
        CodeGenerate codeGenerate = SpringUtil.getBean(generateCodeDto.getCode() + "CodeGenerate", CodeGenerate.class);
        try {
            String generate = codeGenerate.generate(generateCodeDto);
            return CommonResult.success(generate);
        } catch (Exception e) {
            throw new ServiceException("代码生成失败");
        }
    }
}
