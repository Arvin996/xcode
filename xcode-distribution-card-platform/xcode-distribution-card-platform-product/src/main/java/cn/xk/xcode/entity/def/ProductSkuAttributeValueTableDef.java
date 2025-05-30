package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author xuk
 * @since 2025-05-30
 */
public class ProductSkuAttributeValueTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final ProductSkuAttributeValueTableDef PRODUCT_SKU_ATTRIBUTE_VALUE_PO = new ProductSkuAttributeValueTableDef();

    
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 属性图片
     */
    public final QueryColumn PIC = new QueryColumn(this, "pic");

    /**
     * 商品sku属性值
     */
    public final QueryColumn SKU_ID = new QueryColumn(this, "sku_id");

    /**
     * 属性值
     */
    public final QueryColumn VALUE = new QueryColumn(this, "value");

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
     * 属性id
     */
    public final QueryColumn ATTRIBUTE_ID = new QueryColumn(this, "attribute_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, SKU_ID, ATTRIBUTE_ID, VALUE, PIC, IS_DELETED, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER};

    public ProductSkuAttributeValueTableDef() {
        super("", "product_sku_attribute_value");
    }

    private ProductSkuAttributeValueTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public ProductSkuAttributeValueTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new ProductSkuAttributeValueTableDef("", "product_sku_attribute_value", alias));
    }

}
