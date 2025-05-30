package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author xuk
 * @since 2025-05-30
 */
public class ProductAuditTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final ProductAuditTableDef PRODUCT_AUDIT_PO = new ProductAuditTableDef();

    
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 审核类型 0 商品spu 1商品sku 2店铺
     */
    public final QueryColumn TYPE = new QueryColumn(this, "type");

    /**
     * 商品spu id或者店铺id或者商品skui_d
     */
    public final QueryColumn BIZ_ID = new QueryColumn(this, "biz_id");

    /**
     * 00待审核 10 审核通过 20 审核不通过
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 审核失败原因
     */
    public final QueryColumn FAIL_MSG = new QueryColumn(this, "fail_msg");

    /**
     * 审核时间
     */
    public final QueryColumn AUDIT_TIME = new QueryColumn(this, "audit_time");

    /**
     * 审核人
     */
    public final QueryColumn AUDIT_USER = new QueryColumn(this, "audit_user");

    /**
     * 审核创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 审核申请人
     */
    public final QueryColumn CREATE_USER = new QueryColumn(this, "create_user");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, BIZ_ID, STATUS, TYPE, FAIL_MSG, CREATE_USER, AUDIT_USER, AUDIT_TIME, CREATE_TIME};

    public ProductAuditTableDef() {
        super("", "product_audit");
    }

    private ProductAuditTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public ProductAuditTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new ProductAuditTableDef("", "product_audit", alias));
    }

}
