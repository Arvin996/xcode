package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author Administrator
 * @since 2024-09-23
 */
public class PayAppTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final PayAppTableDef PAY_APP_PO = new PayAppTableDef();

    /**
     * 应用id 自增
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 应用备注
     */
    public final QueryColumn REMARK = new QueryColumn(this, "remark");

    /**
     * 应用状态 0开启 1关闭
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 应用编号
     */
    public final QueryColumn APP_CODE = new QueryColumn(this, "app_code");

    /**
     * 应用名称
     */
    public final QueryColumn APP_NAME = new QueryColumn(this, "app_name");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 创建人
     */
    public final QueryColumn CREATE_USER = new QueryColumn(this, "create_user");

    /**
     * 支付结果的回调地址
     */
    public final QueryColumn ORDER_NOTIFY_URL = new QueryColumn(this, "order_notify_url");

    /**
     * 退款结果的回调地址
     */
    public final QueryColumn REFUND_NOTIFY_URL = new QueryColumn(this, "refund_notify_url");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, APP_CODE, APP_NAME, STATUS, REMARK, ORDER_NOTIFY_URL, REFUND_NOTIFY_URL, CREATE_USER, CREATE_TIME};

    public PayAppTableDef() {
        super("", "pay_app");
    }

    private PayAppTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public PayAppTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new PayAppTableDef("", "pay_app", alias));
    }

}
