package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author xuk
 * @since 2025-05-30
 */
public class ProductMerchantTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final ProductMerchantTableDef PRODUCT_MERCHANT_PO = new ProductMerchantTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 商户邮箱
     */
    public final QueryColumn EMAIL = new QueryColumn(this, "email");

    /**
     * 商户头像
     */
    public final QueryColumn AVATAR = new QueryColumn(this, "avatar");

    /**
     * 账户状态 0 启用 1禁用
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 商户昵称
     */
    public final QueryColumn NICKNAME = new QueryColumn(this, "nickname");

    /**
     * 登录密码
     */
    public final QueryColumn PASSWORD = new QueryColumn(this, "password");

    /**
     * 登录用户名
     */
    public final QueryColumn USERNAME = new QueryColumn(this, "username");
    /**
     * 是否删除 0 已删除 1未删除
     */
    public final QueryColumn IS_DELETED = new QueryColumn(this, "is_deleted");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 注册时间
     */
    public final QueryColumn REGISTER_TIME = new QueryColumn(this, "register_time");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, USERNAME, NICKNAME, PASSWORD, EMAIL, AVATAR, STATUS, IS_DELETED, REGISTER_TIME, UPDATE_TIME};

    public ProductMerchantTableDef() {
        super("", "product_merchant");
    }

    private ProductMerchantTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public ProductMerchantTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new ProductMerchantTableDef("", "product_merchant", alias));
    }

}
