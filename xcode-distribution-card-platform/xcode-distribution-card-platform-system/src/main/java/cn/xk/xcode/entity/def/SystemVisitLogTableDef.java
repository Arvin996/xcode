package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author xuk
 * @since 2025-05-09
 */
public class SystemVisitLogTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final SystemVisitLogTableDef SYSTEM_VISIT_LOG_PO = new SystemVisitLogTableDef();

    /**
     * 访问ID
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 操作结果
     */
    public final QueryColumn RESULT = new QueryColumn(this, "result");

    /**
     * 用户编号
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    /**
     * 用户 IP
     */
    public final QueryColumn USER_IP = new QueryColumn(this, "user_ip");

    /**
     * 日志类型 登录 登出 踢下线等
     */
    public final QueryColumn LOG_TYPE = new QueryColumn(this, "log_type");

    /**
     * 用户账号
     */
    public final QueryColumn USERNAME = new QueryColumn(this, "username");

    /**
     * 浏览器 UA
     */
    public final QueryColumn USER_AGENT = new QueryColumn(this, "user_agent");

    /**
     * 操作时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, LOG_TYPE, USER_ID, USERNAME, RESULT, USER_IP, USER_AGENT, CREATE_TIME};

    public SystemVisitLogTableDef() {
        super("", "system_visit_log");
    }

    private SystemVisitLogTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public SystemVisitLogTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new SystemVisitLogTableDef("", "system_visit_log", alias));
    }

}
