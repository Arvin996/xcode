package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author Administrator
 * @since 2024-09-23
 */
public class PayRefundTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final PayRefundTableDef PAY_REFUND_PO = new PayRefundTableDef();

    /**
     * 退款订单号
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 应用编号
     */
    public final QueryColumn APP_ID = new QueryColumn(this, "app_id");

    /**
     * 退款理由
     */
    public final QueryColumn REASON = new QueryColumn(this, "reason");

    /**
     * 退款状态
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 用户ip
     */
    public final QueryColumn USER_IP = new QueryColumn(this, "user_ip");

    /**
     * 订单号
     */
    public final QueryColumn ORDER_ID = new QueryColumn(this, "order_id");

    /**
     * 支付金额 单位分
     */
    public final QueryColumn PAY_PRICE = new QueryColumn(this, "pay_price");

    /**
     * 退款异步通知url
     */
    public final QueryColumn NOTIFY_URL = new QueryColumn(this, "notify_url");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 外部订单号
     */
    public final QueryColumn OUT_TRADE_NO = new QueryColumn(this, "out_trade_no");

    /**
     * 渠道码编号
     */
    public final QueryColumn CHANNEL_CODE = new QueryColumn(this, "channel_code");

    /**
     * 外部退款订单编号
     */
    public final QueryColumn OUT_REFUND_NO = new QueryColumn(this, "out_refund_no");

    /**
     * 退款金额 单位分
     */
    public final QueryColumn REFUND_PRICE = new QueryColumn(this, "refund_price");

    /**
     * 退款成功时间
     */
    public final QueryColumn SUCCESS_TIME = new QueryColumn(this, "success_time");

    /**
     * 渠道订单编号
     */
    public final QueryColumn CHANNEL_ORDER_NO = new QueryColumn(this, "channel_order_no");

    /**
     * 渠道错误信息
     */
    public final QueryColumn CHANNEL_ERROR_MSG = new QueryColumn(this, "channel_error_msg");

    /**
     * 渠道退款编号
     */
    public final QueryColumn CHANNEL_REFUND_NO = new QueryColumn(this, "channel_refund_no");

    /**
     * 商户订单编号
     */
    public final QueryColumn MERCHANT_ORDER_ID = new QueryColumn(this, "merchant_order_id");

    /**
     * 渠道错误码
     */
    public final QueryColumn CHANNEL_ERROR_CODE = new QueryColumn(this, "channel_error_code");

    /**
     * 商户退款订单号
     */
    public final QueryColumn MERCHANT_REFUND_ID = new QueryColumn(this, "merchant_refund_id");

    /**
     * 渠道通知数据
     */
    public final QueryColumn CHANNEL_NOTIFY_DATA = new QueryColumn(this, "channel_notify_data");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, OUT_REFUND_NO, APP_ID, CHANNEL_CODE, ORDER_ID, OUT_TRADE_NO, MERCHANT_ORDER_ID, MERCHANT_REFUND_ID, NOTIFY_URL, STATUS, REFUND_PRICE, PAY_PRICE, REASON, USER_IP, CHANNEL_ORDER_NO, CHANNEL_REFUND_NO, SUCCESS_TIME, CHANNEL_ERROR_CODE, CHANNEL_ERROR_MSG, CHANNEL_NOTIFY_DATA, CREATE_TIME};

    public PayRefundTableDef() {
        super("", "pay_refund");
    }

    private PayRefundTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public PayRefundTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new PayRefundTableDef("", "pay_refund", alias));
    }

}
