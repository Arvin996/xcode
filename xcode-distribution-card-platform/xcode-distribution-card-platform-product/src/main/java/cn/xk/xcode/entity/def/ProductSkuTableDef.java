package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author xuk
 * @since 2025-05-30
 */
public class ProductSkuTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final ProductSkuTableDef PRODUCT_SKU_PO = new ProductSkuTableDef();

    
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 展示图片
     */
    public final QueryColumn PIC = new QueryColumn(this, "pic");

    /**
     * 销量
     */
    public final QueryColumn SALE = new QueryColumn(this, "sale");

    
    public final QueryColumn PRICE = new QueryColumn(this, "price");

    /**
     * 商品spu_id
     */
    public final QueryColumn SPU_ID = new QueryColumn(this, "spu_id");

    /**
     * 库存
     */
    public final QueryColumn STOCK = new QueryColumn(this, "stock");

    /**
     * 0 上架 1下架 2售罄
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * sku编码
     */
    public final QueryColumn SKU_CODE = new QueryColumn(this, "sku_code");

    /**
     * 预警库存
     */
    public final QueryColumn LOW_STOCK = new QueryColumn(this, "low_stock");

    /**
     * 是否删除 0未删除 1已删除
     */
    public final QueryColumn IS_DELETED = new QueryColumn(this, "is_deleted");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 创建人
     */
    public final QueryColumn CREATE_USER = new QueryColumn(this, "create_user");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 更新人
     */
    public final QueryColumn UPDATE_USER = new QueryColumn(this, "update_user");

    /**
     * 00 待审核 10 审核通过 20 审核不通过
     */
    public final QueryColumn AUDIT_STATUS = new QueryColumn(this, "audit_status");

    /**
     * 单品促销价格
     */
    public final QueryColumn PROMOTION_PRICE = new QueryColumn(this, "promotion_price");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, SPU_ID, SKU_CODE, PRICE, STOCK, LOW_STOCK, PIC, SALE, STATUS, AUDIT_STATUS, PROMOTION_PRICE, IS_DELETED, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER};

    public ProductSkuTableDef() {
        super("", "product_sku");
    }

    private ProductSkuTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public ProductSkuTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new ProductSkuTableDef("", "product_sku", alias));
    }

}
