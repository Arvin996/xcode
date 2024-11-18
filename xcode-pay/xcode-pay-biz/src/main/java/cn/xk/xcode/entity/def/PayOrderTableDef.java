package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author Administrator
 * @since 2024-09-23
 */
public class PayOrderTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final PayOrderTableDef PAY_ORDER_PO = new PayOrderTableDef();

    /**
     * 订单号
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 商品描述
     */
    public final QueryColumn BODY = new QueryColumn(this, "body");

    /**
     * 应用id
     */
    public final QueryColumn APP_ID = new QueryColumn(this, "app_id");

    /**
     * 支付金额 单位分
     */
    public final QueryColumn PRICE = new QueryColumn(this, "price");

    /**
     * 支付状态
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 用户ip
     */
    public final QueryColumn USER_IP = new QueryColumn(this, "user_ip");

    /**
     * 商品标题
     */
    public final QueryColumn SUBJECT = new QueryColumn(this, "subject");

    /**
     * 异步通知地址
     */
    public final QueryColumn NOTIFY_URL = new QueryColumn(this, "notify_url");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 订单失效时间
     */
    public final QueryColumn EXPIRE_TIME = new QueryColumn(this, "expire_time");

    /**
     * 外部订单编号
     */
    public final QueryColumn OUT_TRADE_NO = new QueryColumn(this, "out_trade_no");

    /**
     * 渠道编号
     */
    public final QueryColumn CHANNEL_CODE = new QueryColumn(this, "channel_code");

    /**
     * 退费金额
     */
    public final QueryColumn REFUND_PRICE = new QueryColumn(this, "refund_price");

    /**
     * 订单支付成功时间
     */
    public final QueryColumn SUCCESS_TIME = new QueryColumn(this, "success_time");

    /**
     * 渠道支付额外参数
     */
    public final QueryColumn CHANNEL_EXTRAS = new QueryColumn(this, "channel_extras");

    /**
     * 渠道用户id 如openid
     */
    public final QueryColumn CHANNEL_USER_ID = new QueryColumn(this, "channel_user_id");

    /**
     * 渠道订单号
     */
    public final QueryColumn CHANNEL_ORDER_NO = new QueryColumn(this, "channel_order_no");

    /**
     * 渠道错误信息
     */
    public final QueryColumn CHANNEL_ERROR_MSG = new QueryColumn(this, "channel_error_msg");

    /**
     * 渠道费 单位分
     */
    public final QueryColumn CHANNEL_FEE_PRICE = new QueryColumn(this, "channel_fee_price");

    /**
     * 商户订单编号
     */
    public final QueryColumn MERCHANT_ORDER_ID = new QueryColumn(this, "merchant_order_id");

    /**
     * 渠道错误码
     */
    public final QueryColumn CHANNEL_ERROR_CODE = new QueryColumn(this, "channel_error_code");

    /**
     * 渠道通知内容
     */
    public final QueryColumn CHANNEL_NOTIFY_DATA = new QueryColumn(this, "channel_notify_data");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, APP_ID, CHANNEL_CODE, MERCHANT_ORDER_ID, SUBJECT, BODY, NOTIFY_URL, PRICE, CHANNEL_FEE_PRICE, STATUS, USER_IP, EXPIRE_TIME, SUCCESS_TIME, OUT_TRADE_NO, REFUND_PRICE, CHANNEL_USER_ID, CHANNEL_ORDER_NO, CHANNEL_EXTRAS, CHANNEL_ERROR_CODE, CHANNEL_ERROR_MSG, CHANNEL_NOTIFY_DATA, CREATE_TIME};

    public PayOrderTableDef() {
        super("", "pay_order");
    }

    private PayOrderTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public PayOrderTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new PayOrderTableDef("", "pay_order", alias));
    }

}
