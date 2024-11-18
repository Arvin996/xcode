package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author Administrator
 * @since 2024-09-23
 */
public class PayNotifyLogTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final PayNotifyLogTableDef PAY_NOTIFY_LOG_PO = new PayNotifyLogTableDef();

    /**
     * 日志id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 通知结果
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 任务id
     */
    public final QueryColumn TASK_ID = new QueryColumn(this, "task_id");

    /**
     * 通知响应结果
     */
    public final QueryColumn RESPONSE = new QueryColumn(this, "response");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 当前通知次数
     */
    public final QueryColumn NOTIFY_TIMES = new QueryColumn(this, "notify_times");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TASK_ID, NOTIFY_TIMES, RESPONSE, STATUS, CREATE_TIME};

    public PayNotifyLogTableDef() {
        super("", "pay_notify_log");
    }

    private PayNotifyLogTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public PayNotifyLogTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new PayNotifyLogTableDef("", "pay_notify_log", alias));
    }

}
