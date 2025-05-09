package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author xuk
 * @since 2025-03-10
 */
public class MessageTaskDetailTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final MessageTaskDetailTableDef MESSAGE_TASK_DETAIL_PO = new MessageTaskDetailTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 执行状态 0成功 1失败
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 任务id
     */
    public final QueryColumn TASK_ID = new QueryColumn(this, "task_id");

    /**
     * 失败原因
     */
    public final QueryColumn FAIL_MSG = new QueryColumn(this, "fail_msg");

    /**
     * 执行时间
     */
    public final QueryColumn EXEC_TIME = new QueryColumn(this, "exec_time");

    /**
     * 接收人
     */
    public final QueryColumn RECEIVER = new QueryColumn(this, "receiver");

    /**
     * 重试次数
     */
    public final QueryColumn RETRY_TIMES = new QueryColumn(this, "retry_times");

    /**
     * 成功时间
     */
    public final QueryColumn SUCCESS_TIME = new QueryColumn(this, "success_time");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TASK_ID, RECEIVER, EXEC_TIME, STATUS, RETRY_TIMES, FAIL_MSG, SUCCESS_TIME};

    public MessageTaskDetailTableDef() {
        super("", "message_task_detail");
    }

    private MessageTaskDetailTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public MessageTaskDetailTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new MessageTaskDetailTableDef("", "message_task_detail", alias));
    }

}
