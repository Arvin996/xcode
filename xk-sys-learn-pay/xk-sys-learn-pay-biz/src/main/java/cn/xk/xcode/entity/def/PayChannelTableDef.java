package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author Administrator
 * @since 2024-09-23
 */
public class PayChannelTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final PayChannelTableDef PAY_CHANNEL_PO = new PayChannelTableDef();

    /**
     * 渠道id 自增
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 渠道备注
     */
    public final QueryColumn REMARK = new QueryColumn(this, "remark");

    /**
     * 渠道状态 0 可用 1不可用
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 渠道费率，单位：百分比
     */
    public final QueryColumn FEE_RATE = new QueryColumn(this, "fee_rate");

    /**
     * 支付渠道编码
     */
    public final QueryColumn CHANNEL_CODE = new QueryColumn(this, "channel_code");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, CHANNEL_CODE, STATUS, FEE_RATE, REMARK};

    public PayChannelTableDef() {
        super("", "pay_channel");
    }

    private PayChannelTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public PayChannelTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new PayChannelTableDef("", "pay_channel", alias));
    }

}
