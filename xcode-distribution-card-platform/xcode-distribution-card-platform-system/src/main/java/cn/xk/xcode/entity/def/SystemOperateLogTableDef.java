package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author xuk
 * @since 2025-05-09
 */
public class SystemOperateLogTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final SystemOperateLogTableDef SYSTEM_OPERATE_LOG_PO = new SystemOperateLogTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 调用的api详情
     */
    public final QueryColumn API_ID = new QueryColumn(this, "api_id");

    /**
     * 操作结果 0成功 1失败
     */
    public final QueryColumn STATE = new QueryColumn(this, "state");

    /**
     * 失败原因
     */
    public final QueryColumn FAIL_MSG = new QueryColumn(this, "fail_msg");

    /**
     * 操作用户
     */
    public final QueryColumn USERNAME = new QueryColumn(this, "username");

    /**
     * 操作时间
     */
    public final QueryColumn OPERATE_TIME = new QueryColumn(this, "operate_time");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, API_ID, USERNAME, STATE, FAIL_MSG, OPERATE_TIME};

    public SystemOperateLogTableDef() {
        super("", "system_operate_log");
    }

    private SystemOperateLogTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public SystemOperateLogTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new SystemOperateLogTableDef("", "system_operate_log", alias));
    }

}
