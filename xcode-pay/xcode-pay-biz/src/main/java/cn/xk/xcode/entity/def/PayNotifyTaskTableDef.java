package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author Administrator
 * @since 2024-09-23
 */
public class PayNotifyTaskTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final PayNotifyTaskTableDef PAY_NOTIFY_TASK_PO = new PayNotifyTaskTableDef();

    /**
     * 任务id 自增
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 应用编号
     */
    public final QueryColumn APP_ID = new QueryColumn(this, "app_id");

    /**
     * 业务id 订单或者退费id
     */
    public final QueryColumn BIZ_ID = new QueryColumn(this, "bizId");

    /**
     * 通知状态
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 通知地址
     */
    public final QueryColumn NOTIFY_URL = new QueryColumn(this, "notify_url");

    /**
     * 通知类型 支付或者退款
     */
    public final QueryColumn NOTIFY_TYPE = new QueryColumn(this, "notify_type");

    /**
     * 默认通知次数
     */
    public final QueryColumn MAX_NOTIFY_TIMES = new QueryColumn(this, "max_notify_times");

    /**
     * 下一次通知时间
     */
    public final QueryColumn NEXT_NOTIFY_TIME = new QueryColumn(this, "next_notify_time");

    /**
     * 最后一次执行的时间
     */
    public final QueryColumn LAST_EXECUTE_TIME = new QueryColumn(this, "last_execute_time");

    /**
     * 当前通知次数
     */
    public final QueryColumn CURRENT_NOTIFY_TIMES = new QueryColumn(this, "current_notify_times");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, APP_ID, NOTIFY_TYPE, BIZ_ID, STATUS, NEXT_NOTIFY_TIME, LAST_EXECUTE_TIME, CURRENT_NOTIFY_TIMES, MAX_NOTIFY_TIMES, NOTIFY_URL};

    public PayNotifyTaskTableDef() {
        super("", "pay_notify_task");
    }

    private PayNotifyTaskTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public PayNotifyTaskTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new PayNotifyTaskTableDef("", "pay_notify_task", alias));
    }

}
