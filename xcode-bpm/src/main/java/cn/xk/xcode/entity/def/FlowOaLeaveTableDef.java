package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author xuk
 * @since 2024-11-21
 */
public class FlowOaLeaveTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final FlowOaLeaveTableDef FLOW_OA_LEAVE_PO = new FlowOaLeaveTableDef();

    /**
     * 主键
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 请假天数
     */
    public final QueryColumn DAY = new QueryColumn(this, "day");

    /**
     * 请假类型
     */
    public final QueryColumn TYPE = new QueryColumn(this, "type");

    /**
     * 请假原因
     */
    public final QueryColumn REASON = new QueryColumn(this, "reason");

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    public final QueryColumn DEL_FLAG = new QueryColumn(this, "del_flag");

    /**
     * 结束时间
     */
    public final QueryColumn END_TIME = new QueryColumn(this, "end_time");

    /**
     * 创建者
     */
    public final QueryColumn CREATE_USER = new QueryColumn(this, "create_user");

    /**
     * 节点编码
     */
    public final QueryColumn NODE_CODE = new QueryColumn(this, "node_code");

    /**
     * 流程节点名称
     */
    public final QueryColumn NODE_NAME = new QueryColumn(this, "node_name");

    /**
     * 节点类型（0开始节点 1中间节点 2结束结点 3互斥网关 4并行网关）
     */
    public final QueryColumn NODE_TYPE = new QueryColumn(this, "node_type");

    /**
     * 更新者
     */
    public final QueryColumn UPDATE_USER = new QueryColumn(this, "update_user");

    /**
     * 开始时间
     */
    public final QueryColumn START_TIME = new QueryColumn(this, "start_time");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 流程状态（0待提交 1审批中 2 审批通过 3自动通过 8已完成 9已退回 10失效）
     */
    public final QueryColumn FLOW_STATUS = new QueryColumn(this, "flow_status");

    /**
     * 流程实例的id
     */
    public final QueryColumn INSTANCE_ID = new QueryColumn(this, "instance_id");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TYPE, REASON, START_TIME, END_TIME, DAY, INSTANCE_ID, NODE_CODE, NODE_NAME, NODE_TYPE, FLOW_STATUS, CREATE_USER, CREATE_TIME, UPDATE_USER, UPDATE_TIME, DEL_FLAG};

    public FlowOaLeaveTableDef() {
        super("", "flow_oa_leave");
    }

    private FlowOaLeaveTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public FlowOaLeaveTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new FlowOaLeaveTableDef("", "flow_oa_leave", alias));
    }

}
