package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 * 表定义层。
 *
 * @author xuk
 * @since 2025-06-10
 */
public class ProductMerchantThirdTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public static final ProductMerchantThirdTableDef PRODUCT_MERCHANT_THIRD_PO = new ProductMerchantThirdTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 三方登录方式 如feishu  qq dingding等
     */
    public final QueryColumn TYPE = new QueryColumn(this, "type");

    /**
     * 三方平台用户的user_id
     */
    public final QueryColumn UNION_ID = new QueryColumn(this, "union_id");

    /**
     * 是否删除 0未删除 1已删除
     */
    public final QueryColumn IS_DELETED = new QueryColumn(this, "is_deleted");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 对应的商户id
     */
    public final QueryColumn MERCHANT_ID = new QueryColumn(this, "merchant_id");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, MERCHANT_ID, TYPE, UNION_ID, IS_DELETED, CREATE_TIME, UPDATE_TIME};

    public ProductMerchantThirdTableDef() {
        super("", "product_merchant_third");
    }

    private ProductMerchantThirdTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public ProductMerchantThirdTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new ProductMerchantThirdTableDef("", "product_merchant_third", alias));
    }

}
