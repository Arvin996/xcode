package cn.xk.xcode.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.GenConstants;
import cn.xk.xcode.entity.dto.*;
import cn.xk.xcode.entity.po.GenTableColumnPo;
import cn.xk.xcode.entity.vo.GenCodePreviewVo;
import cn.xk.xcode.entity.vo.GenTableColumnVo;
import cn.xk.xcode.entity.vo.GenTableResultVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.mapper.GenTableColumnMapper;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.utils.*;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.GenTablePo;
import cn.xk.xcode.mapper.GenTableMapper;
import cn.xk.xcode.service.GenTableService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static cn.xk.xcode.XcodeInfraGlobalConstants.*;
import static cn.xk.xcode.entity.def.GenTableColumnTableDef.GEN_TABLE_COLUMN_PO;
import static cn.xk.xcode.entity.def.GenTableTableDef.GEN_TABLE_PO;

/**
 * 服务层实现。
 *
 * @author xuk
 * @since 2024-11-25
 */
@Slf4j
@Service
public class GenTableServiceImpl extends ServiceImpl<GenTableMapper, GenTablePo> implements GenTableService {

    @Resource
    private GenTableMapper genTableMapper;

    @Resource
    private GenTableColumnMapper genTableColumnMapper;

    @Override
    public PageResult<GenTableResultVo> genTableList(QueryGenTableDto queryGenTableDto) {
        List<GenTableResultVo> list = genTableMapper.selectGenTableList(queryGenTableDto);
        return PageUtil.startPage(queryGenTableDto, list);
    }

    @Override
    public List<GenTableResultVo> queryDbTableList(QueryGenTableDto queryGenTableDto) {
        return genTableMapper.queryDbTableList(queryGenTableDto);
    }

    @Override
    public List<GenTableColumnVo> queryDbTableColumnList(Long tableId) {
        return genTableColumnMapper.queryDbTableColumnList(tableId);
    }

    @Override
    public Boolean importTableSave(ImportTableDto importTableDto) {
        String tableNames = importTableDto.getTableNames();
        List<String> collect = Arrays.stream(tableNames.trim().split(",")).collect(Collectors.toList());
        List<GenTableResultVo> list = genTableMapper.selectDbTableListByNames(collect);
        importTables(list, importTableDto);
        return null;
    }

    @Override
    public Boolean updateGenTable(UpdateGenTableDto updateGenTableDto) {
        validateEdit(updateGenTableDto);
        String options = JSON.toJSONString(updateGenTableDto.getParams());
        updateGenTableDto.setOptions(options);
        GenTablePo genTablePo = BeanUtil.toBean(updateGenTableDto, GenTablePo.class);
        if (this.updateById(genTablePo)) {
            for (GenTableColumnPo cenTableColumn : updateGenTableDto.getColumns()) {
                genTableColumnMapper.updateGenTableColumn(cenTableColumn);
            }
        }
        return true;
    }

    @Override
    public Boolean createTableBySql(CreateGenTableDto createGenTableDto) {
        String sql = createGenTableDto.getSql();
        try {
            SqlUtil.filterKeyword(sql);
            List<SQLStatement> sqlStatements = SQLUtils.parseStatements(sql, DbType.mysql);
            List<String> tableNames = new ArrayList<>();
            for (SQLStatement sqlStatement : sqlStatements) {
                if (sqlStatement instanceof MySqlCreateTableStatement) {
                    MySqlCreateTableStatement createTableStatement = (MySqlCreateTableStatement) sqlStatement;
                    if (genTableMapper.createTable(createTableStatement.toString()) == 0) {
                        String tableName = createTableStatement.getTableName().replaceAll("`", "");
                        tableNames.add(tableName);
                    }
                }
            }
            List<GenTableResultVo> tableList = genTableMapper.selectDbTableListByNames(tableNames);
            importTables(tableList, createGenTableDto);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.castServerException(CREATE_TABLE_SCHEMA_ERROR);
        }
        return false;
    }

    @Transactional
    @Override
    public Boolean removeGenTable(DelGenTableDto delGenTableDto) {
        List<Long> tableIds = delGenTableDto.getTableIds();
        genTableColumnMapper.deleteByCondition(GEN_TABLE_COLUMN_PO.TABLE_ID.in(tableIds));
        this.removeByIds(tableIds);
        return true;
    }

    @Override
    public GenCodePreviewVo preview(Long tableId) {
        Map<String, Object> dataMap = new LinkedHashMap<>();
        // 查询表信息
        GenTablePo table = this.getById(tableId);
        GenTableResultVo genTableResultVo = BeanUtil.toBean(table, GenTableResultVo.class);
        // 设置子表
        setSubTable(genTableResultVo);
        // 设置主键列信息
        setPkColumn(genTableResultVo);
        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(genTableResultVo);

        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory(), table.getTplWebType());
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);
            dataMap.put(template, sw.toString());
        }
        return new GenCodePreviewVo(dataMap);
    }

    @Override
    public void download(HttpServletResponse response, String tableName) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        generatorCode(tableName, zip);
        IOUtils.closeQuietly(zip);
        byte[] byteArray = outputStream.toByteArray();
        response.reset();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename=" + tableName + ".zip");
        response.addHeader("Content-Length", "" + byteArray.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(byteArray, response.getOutputStream());
    }

    @Override
    public Boolean syncDb(String tableName) {
        QueryGenTableDto queryGenTableDto = new QueryGenTableDto();
        queryGenTableDto.setTableName(tableName);
        GenTableResultVo table = genTableMapper.selectGenTableList(queryGenTableDto).get(0);
        List<GenTableColumnPo> tableColumns = table.getColumns();
        Map<String, GenTableColumnPo> tableColumnMap = tableColumns.stream().collect(Collectors.toMap(GenTableColumnPo::getColumnName, Function.identity()));

        List<GenTableColumnVo> dbTableColumns = genTableColumnMapper.selectDbTableColumnsByName(tableName);
        if (ObjectUtil.isEmpty(dbTableColumns)) {
           ExceptionUtil.castServiceException(TABLE_SCHEMA_NOT_EXIST);
        }
        List<String> dbTableColumnNames = dbTableColumns.stream().map(GenTableColumnVo::getColumnName).collect(Collectors.toList());

        dbTableColumns.forEach(column -> {
            GenUtils.initColumnField(column, table);
            if (tableColumnMap.containsKey(column.getColumnName())) {
                GenTableColumnPo prevColumn = tableColumnMap.get(column.getColumnName());
                column.setColumnId(prevColumn.getColumnId());
                if ("1".equals(column.getIsList())) {
                    // 如果是列表，继续保留查询方式/字典类型选项
                    column.setDictType(prevColumn.getDictType());
                    column.setQueryType(prevColumn.getQueryType());
                }
                if (StringUtils.isNotEmpty(prevColumn.getIsRequired()) && !"1".equals(column.getIsPk())
                        && ("1".equals(column.getIsInsert()) || "1".equals(column.getIsEdit())
                        && ((column.isUsableColumn()) || (!column.isSuperColumn())))) {
                    // 如果是(新增/修改&非主键/非忽略及父属性)，继续保留必填/显示类型选项
                    column.setIsRequired(prevColumn.getIsRequired());
                    column.setHtmlType(prevColumn.getHtmlType());
                }
                genTableColumnMapper.updateGenTableColumn(column);
            } else {
                genTableColumnMapper.insert(column);
            }
        });

        List<GenTableColumnPo> delColumns = tableColumns.stream().filter(column -> !dbTableColumnNames.contains(column.getColumnName())).collect(Collectors.toList());
        if (ObjectUtil.isNotEmpty(delColumns)) {
            genTableColumnMapper.selectListByIds(delColumns.stream().map(GenTableColumnPo::getColumnId).collect(Collectors.toList()));
        }
        return true;
    }

    @Override
    public void batchGenCode(HttpServletResponse response, String tables) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        String[] tableNames = tables.split(",");
        for (String tableName : tableNames) {
            generatorCode(tableName, zip);
        }
        IOUtils.closeQuietly(zip);
        byte[] byteArray = outputStream.toByteArray();
        response.reset();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename=" + UUID.randomUUID().toString().replace("-", "").substring(8) + ".zip");
        response.addHeader("Content-Length", "" + byteArray.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(byteArray, response.getOutputStream());
    }

    private void importTables(List<GenTableResultVo> list, BaseGenDto baseGenDto) {
        GenTablePo genTablePo = null;
        try {
            for (GenTableResultVo table : list) {
                String tableName = table.getTableName();
                genTablePo = BeanUtil.toBean(table, GenTablePo.class);
                GenUtils.initTable(genTablePo, baseGenDto);
                boolean flag = this.save(genTablePo);
                if (flag) {
                    // 保存列信息
                    List<GenTableColumnVo> genTableColumns = genTableColumnMapper.selectDbTableColumnsByName(tableName);
                    for (GenTableColumnVo column : genTableColumns) {
                        GenUtils.initColumnField(column, table);
                        genTableColumnMapper.insert(column);
                    }
                }
            }
        } catch (Exception e) {
            log.error("导入失败：{}", e.getMessage());
            ExceptionUtil.castServiceException(IMPORT_TABLE_ERROR);
        }
    }

    private void setSubTable(GenTableResultVo table) {
        String subTableName = table.getSubTableName();
        if (StringUtils.isNotEmpty(subTableName)) {
            table.setSubTable(genTableMapper.selectDbTableListByNames(CollectionUtil.singleton(subTableName)).get(0));
        }
    }

    private void setPkColumn(GenTableResultVo table) {
        for (GenTableColumnPo column : table.getColumns()) {
            if ("1".equals(column.getIsPk())) {
                table.setPkColumn(column);
                break;
            }
        }
        if (Objects.isNull(table.getPkColumn())) {
            table.setPkColumn(table.getColumns().get(0));
        }
        if (GenConstants.TPL_SUB.equals(table.getTplCategory())) {
            for (GenTableColumnPo column : table.getSubTable().getColumns()) {
                if ("1".equals(column.getIsPk())) {
                    table.getSubTable().setPkColumn(column);
                    break;
                }
            }
            if (Objects.isNull(table.getSubTable().getPkColumn())) {
                table.getSubTable().setPkColumn(table.getSubTable().getColumns().get(0));
            }
        }
    }

    private void validateEdit(UpdateGenTableDto genTable) {
        if (GenConstants.TPL_TREE.equals(genTable.getTplCategory())) {
            String options = JSON.toJSONString(genTable.getParams());
            JSONObject paramsObj = JSON.parseObject(options);
            if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_CODE))) {
                ExceptionUtil.castServiceException(TREE_ENCODE_IS_EMPTY);
            } else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_PARENT_CODE))) {
                ExceptionUtil.castServiceException(TREE_PARENT_ID_IS_EMPTY);
            } else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_NAME))) {
                ExceptionUtil.castServiceException(TREE_NAME_IS_NULL);
            } else if (GenConstants.TPL_SUB.equals(genTable.getTplCategory())) {
                if (StringUtils.isEmpty(genTable.getSubTableName())) {
                    ExceptionUtil.castServiceException(SUB_TABLE_NAME_IS_EMPTY);
                } else if (StringUtils.isEmpty(genTable.getSubTableFkName())) {
                    ExceptionUtil.castServiceException(SUB_TABLE_FK_IS_NULL);
                }
            }
        }
    }

    private void generatorCode(String tableName, ZipOutputStream zip) {
        // 查询表信息
        QueryGenTableDto queryGenTableDto = new QueryGenTableDto();
        queryGenTableDto.setTableName(tableName);
        GenTableResultVo table = genTableMapper.selectGenTableList(queryGenTableDto).get(0);
        // 设置主子表信息
        setSubTable(table);
        // 设置主键列信息
        setPkColumn(table);

        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory(), table.getTplWebType());
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);
            try {
                // 添加到zip
                zip.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, table)));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.flush();
                zip.closeEntry();
            } catch (IOException e) {
                log.error("渲染模板失败，表名：{}错误信息:%s", table.getTableName(), e);
                ExceptionUtil.castServiceException(RENDER_TABLE_ERROR);
            }
        }
    }

}
