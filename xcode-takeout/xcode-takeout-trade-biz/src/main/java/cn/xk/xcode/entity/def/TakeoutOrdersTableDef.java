package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author xuk
 * @since 2024-11-06
 */
public class TakeoutOrdersTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final TakeoutOrdersTableDef TAKEOUT_ORDERS_PO = new TakeoutOrdersTableDef();

    /**
     * 订单id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    
    public final QueryColumn MOBILE = new QueryColumn(this, "mobile");

    /**
     * 实收金额
     */
    public final QueryColumn AMOUNT = new QueryColumn(this, "amount");

    /**
     * 备注
     */
    public final QueryColumn REMARK = new QueryColumn(this, "remark");

    /**
     * 订单状态 1待付款，2待派送，3已派送，4已完成，5已取消
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 下单用户
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    
    public final QueryColumn ADDRESS = new QueryColumn(this, "address");

    
    public final QueryColumn USER_NAME = new QueryColumn(this, "user_name");

    /**
     * 地址id
     */
    public final QueryColumn ADDRESS_ID = new QueryColumn(this, "address_id");

    
    public final QueryColumn CONSIGNEE = new QueryColumn(this, "consignee");

    /**
     * 下单时间
     */
    public final QueryColumn ORDER_TIME = new QueryColumn(this, "order_time");

    /**
     * 支付方式 1微信,2支付宝
     */
    public final QueryColumn PAY_METHOD = new QueryColumn(this, "pay_method");

    /**
     * 结账时间
     */
    public final QueryColumn CHECKOUT_TIME = new QueryColumn(this, "checkout_time");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, STATUS, USER_ID, ADDRESS_ID, ORDER_TIME, CHECKOUT_TIME, PAY_METHOD, AMOUNT, REMARK, MOBILE, ADDRESS, USER_NAME, CONSIGNEE};

    public TakeoutOrdersTableDef() {
        super("", "takeout_orders");
    }

    private TakeoutOrdersTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public TakeoutOrdersTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new TakeoutOrdersTableDef("", "takeout_orders", alias));
    }

}
