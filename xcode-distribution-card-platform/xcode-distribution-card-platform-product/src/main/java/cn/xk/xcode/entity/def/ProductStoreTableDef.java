package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author xuk
 * @since 2025-05-30
 */
public class ProductStoreTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final ProductStoreTableDef PRODUCT_STORE_PO = new ProductStoreTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 店铺简介
     */
    public final QueryColumn DESC = new QueryColumn(this, "desc");

    /**
     * 店铺logo
     */
    public final QueryColumn LOGO = new QueryColumn(this, "logo");

    /**
     * 店铺名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 0 正常 1封禁
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 成交率
     */
    public final QueryColumn CLOSE_RATE = new QueryColumn(this, "close_rate");

    /**
     * 店铺客服qq
     */
    public final QueryColumn CONTACT_QQ = new QueryColumn(this, "contact_qq");

    /**
     * 店铺客户微信
     */
    public final QueryColumn CONTACT_WX = new QueryColumn(this, "contact_wx");

    /**
     * 是否删除 0未删除 1已删除
     */
    public final QueryColumn IS_DELETED = new QueryColumn(this, "is_deleted");

    /**
     * 总销售量
     */
    public final QueryColumn SALE_COUNT = new QueryColumn(this, "sale_count");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 创建人
     */
    public final QueryColumn CREATE_USER = new QueryColumn(this, "create_user");

    /**
     * 所属商户id
     */
    public final QueryColumn MERCHANT_ID = new QueryColumn(this, "merchant_id");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 更新人
     */
    public final QueryColumn UPDATE_USER = new QueryColumn(this, "update_user");

    /**
     * 审核状态 00 待审核 10审核通过 20审核不通过
     */
    public final QueryColumn AUDIT_STATUS = new QueryColumn(this, "audit_status");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, MERCHANT_ID, NAME, DESC, LOGO, CLOSE_RATE, SALE_COUNT, CONTACT_QQ, CONTACT_WX, AUDIT_STATUS, STATUS, IS_DELETED, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER};

    public ProductStoreTableDef() {
        super("", "product_store");
    }

    private ProductStoreTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public ProductStoreTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new ProductStoreTableDef("", "product_store", alias));
    }

}
