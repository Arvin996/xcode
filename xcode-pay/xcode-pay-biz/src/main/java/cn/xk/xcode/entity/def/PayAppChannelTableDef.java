package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author Administrator
 * @since 2024-09-23
 */
public class PayAppChannelTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final PayAppChannelTableDef PAY_APP_CHANNEL_PO = new PayAppChannelTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 应用id
     */
    public final QueryColumn APP_ID = new QueryColumn(this, "app_id");

    /**
     * 渠道id
     */
    public final QueryColumn CHANNEL_ID = new QueryColumn(this, "channel_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, APP_ID, CHANNEL_ID};

    public PayAppChannelTableDef() {
        super("", "pay_app_channel");
    }

    private PayAppChannelTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public PayAppChannelTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new PayAppChannelTableDef("", "pay_app_channel", alias));
    }

}
