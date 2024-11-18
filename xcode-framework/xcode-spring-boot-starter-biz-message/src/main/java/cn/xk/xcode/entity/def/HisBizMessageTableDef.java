package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author lenovo
 * @since 2024-07-16
 */
public class HisBizMessageTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final HisBizMessageTableDef HIS_BIZ_MESSAGE_PO = new HisBizMessageTableDef();

    /**
     * 消息id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 消息主键
     */
    public final QueryColumn BIZ_KEY = new QueryColumn(this, "biz_key");

    /**
     * 错误信息
     */
    public final QueryColumn ERR_MSG = new QueryColumn(this, "err_msg");

    /**
     * 消息状态 0待处理 1处理成功 2处理失败 3处理中
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 消息类型
     */
    public final QueryColumn BIZ_TYPE = new QueryColumn(this, "biz_type");

    /**
     * 失败次数
     */
    public final QueryColumn FAIL_COUNT = new QueryColumn(this, "fail_count");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 创建用户
     */
    public final QueryColumn CREATE_USER = new QueryColumn(this, "create_user");

    /**
     * 完成时间
     */
    public final QueryColumn FINISH_TIME = new QueryColumn(this, "finish_time");

    /**
     * 最大失败次数
     */
    public final QueryColumn MAX_FAIL_COUNT = new QueryColumn(this, "max_fail_count");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, BIZ_TYPE, BIZ_KEY, STATUS, FAIL_COUNT, MAX_FAIL_COUNT, ERR_MSG, CREATE_USER, CREATE_TIME, FINISH_TIME};

    public HisBizMessageTableDef() {
        super("", "his_biz_message");
    }

    private HisBizMessageTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public HisBizMessageTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new HisBizMessageTableDef("", "his_biz_message", alias));
    }

}
