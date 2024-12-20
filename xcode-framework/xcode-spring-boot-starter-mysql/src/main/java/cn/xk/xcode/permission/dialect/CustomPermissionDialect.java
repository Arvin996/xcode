package cn.xk.xcode.permission.dialect;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReferenceUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.StpSystemUtil;
import cn.xk.xcode.enums.DataPermissionScope;
import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.IntErrorCode;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.permission.context.DatePermissionHolder;
import cn.xk.xcode.pojo.LoginUser;
import cn.xk.xcode.pojo.StpType;
import cn.xk.xcode.utils.SaTokenLoginUtils;
import cn.xk.xcode.utils.collections.CollectionUtil;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.constant.SqlOperator;
import com.mybatisflex.core.dialect.OperateType;
import com.mybatisflex.core.dialect.impl.CommonsDialectImpl;
import com.mybatisflex.core.query.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author xuk
 * @Date 2024/12/19 13:33
 * @Version 1.0.0
 * @Description CustomPermissionDialect
 **/
public class CustomPermissionDialect extends CommonsDialectImpl {

    private final static String FIELD_CREATE_USER = "create_user";
    private final static String FIELD_DEPT_SCOPE = "dept_id";
    public final static ErrorCode TABLE_CLASS_NOT_CORRECT = new IntErrorCode(1600_0_500, "设置的数据权限持久层对象不正确");

    @Override
    public void prepareAuth(QueryWrapper queryWrapper, OperateType operateType) {
        DatePermissionHolder.DataScopeEntity dataScopeEntity = DatePermissionHolder.get();
        if (!StpSystemUtil.isLogin() || ObjectUtil.isNull(dataScopeEntity)) {
            super.prepareAuth(queryWrapper, operateType);
            return;
        }
        if (operateType != OperateType.SELECT) {
            super.prepareAuth(queryWrapper, operateType);
            return;
        }
        DataPermissionScope scope = dataScopeEntity.getScope();
        Class<?> tableClazz = dataScopeEntity.getTableClazz();
        List<QueryTable> queryTables = CPI.getQueryTables(queryWrapper);
        List<QueryTable> joinTables = CPI.getJoinTables(queryWrapper);
        LoginUser loginUser = SaTokenLoginUtils.getLoginUser(StpType.SYSTEM);
        if (ObjectUtil.isNull(loginUser)) {
            super.prepareAuth(queryWrapper, operateType);
            return;
        }
        if (loginUser.getRoles().contains("admin")) {
            super.prepareAuth(queryWrapper, operateType);
            return;
        }
        // 子查询 select from (select from)
        for (QueryTable queryTable : queryTables) {
            if (queryTable instanceof SelectQueryTable) {
                prepareAuth(((SelectQueryTable) queryTable).getQueryWrapper(), operateType);
            }
        }
        Map<String, QueryTable> map = new HashMap<>();
        if (CollectionUtil.isNotEmpty(queryTables)) {
            queryTables.forEach(queryTable -> map.put(queryTable.getName(), queryTable));
        }
        if (CollectionUtil.isNotEmpty(joinTables)) {
            joinTables.forEach(queryTable -> map.put(queryTable.getName(), queryTable));
        }
        if (map.isEmpty()) {
            super.prepareAuth(queryWrapper, operateType);
            return;
        }
        String simpleName = tableClazz.getSimpleName();
        if (!simpleName.endsWith("Po")) {
            ExceptionUtil.castServiceException(TABLE_CLASS_NOT_CORRECT);
        }
        Table tableClazzAnnotation = tableClazz.getAnnotation(Table.class);
        String tableName = Objects.nonNull(tableClazzAnnotation) ? tableClazzAnnotation.value() : StrUtil.toUnderlineCase(simpleName);
        QueryTable table = map.get(tableName);
        if (table == null) return;
        String alias = ObjectUtil.isNotNull(table.getAlias()) ? table.getAlias() : table.getName();
        switch (scope) {
            case ALL:
                super.prepareAuth(queryWrapper, operateType);
                return;
            case SELF:
                handleSelfDataScope(queryWrapper, alias, table, tableClazz);
                break;
            case DEPT:
                handleDeptDataScope(queryWrapper, tableClazz, loginUser.getDeptIds());
                break;
            case DEPT_CHILD:
                handleDeptDataScope(queryWrapper, tableClazz, loginUser.getDeptIdsAndChildren());
                break;
            default:
                super.prepareAuth(queryWrapper, operateType);
                break;
        }
    }

    private void handleSelfDataScope(QueryWrapper queryWrapper, String alias, QueryTable queryTable, Class<?> tableClazz) {
        String field = FIELD_CREATE_USER;
        field = StrUtil.toCamelCase(field);
        if (!ReflectUtil.hasField(tableClazz, field)) {
            return;
        }
        QueryCondition queryCondition = QueryCondition.create(
                new QueryColumn(queryTable.getSchema(), queryTable.getName(), FIELD_CREATE_USER, alias),
                SqlOperator.EQUALS,
                StpSystemUtil.getLoginId()
        );
        Object context = CPI.getContext(queryWrapper, field);
        if (Objects.isNull(context)){
            queryWrapper.where(queryCondition);
            CPI.putContext(queryWrapper, field, true);
        }
    }

    private void handleDeptDataScope(QueryWrapper queryWrapper, Class<?> tableClazz, Set<Long> deptIds) {
        String field = FIELD_DEPT_SCOPE;
        field = StrUtil.toCamelCase(field);
        if (!ReflectUtil.hasField(tableClazz, field)) {
            return;
        }
        if (CollectionUtil.isEmpty(deptIds)){
            return;
        }
        String sqlParams = deptIds.stream().map(String::valueOf).collect(Collectors.joining(", ", "(", ")"));
        String sql = " EXISTS ( SELECT 1 FROM `sys_dept` WHERE `sys_dept`.`dept_id` IN " + sqlParams;
        Object context = CPI.getContext(queryWrapper, field);
        if (Objects.isNull(context)){
            queryWrapper.where(sql);
            CPI.putContext(queryWrapper, field, true);
        }
    }
}
