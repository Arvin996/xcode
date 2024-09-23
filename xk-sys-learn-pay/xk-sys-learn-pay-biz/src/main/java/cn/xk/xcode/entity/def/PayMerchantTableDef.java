package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author Administrator
 * @since 2024-09-23
 */
public class PayMerchantTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final PayMerchantTableDef PAY_MERCHANT_PO = new PayMerchantTableDef();

    /**
     * 商户id 自增
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 商户备注
     */
    public final QueryColumn REMARK = new QueryColumn(this, "remark");

    /**
     * 商户状态 0 开启 1关闭
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 创建人
     */
    public final QueryColumn CREATE_USER = new QueryColumn(this, "create_user");

    /**
     * 商户编号
     */
    public final QueryColumn MERCHANT_NO = new QueryColumn(this, "merchant_no");

    /**
     * 商户名称
     */
    public final QueryColumn MERCHANT_NAME = new QueryColumn(this, "merchant_name");

    /**
     * 商户简称
     */
    public final QueryColumn MERCHANT_SHORT_NAME = new QueryColumn(this, "merchant_short_name");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, MERCHANT_NO, MERCHANT_NAME, MERCHANT_SHORT_NAME, STATUS, REMARK, CREATE_TIME, CREATE_USER};

    public PayMerchantTableDef() {
        super("", "pay_merchant");
    }

    private PayMerchantTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public PayMerchantTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new PayMerchantTableDef("", "pay_merchant", alias));
    }

}
