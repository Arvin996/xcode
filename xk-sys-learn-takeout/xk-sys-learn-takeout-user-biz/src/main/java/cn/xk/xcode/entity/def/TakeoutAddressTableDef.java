package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author Administrator
 * @since 2024-10-29
 */
public class TakeoutAddressTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final TakeoutAddressTableDef TAKEOUT_ADDRESS_PO = new TakeoutAddressTableDef();

    /**
     * 主键
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 性别 0 女 1 男
     */
    public final QueryColumn SEX = new QueryColumn(this, "sex");

    /**
     * 标签
     */
    public final QueryColumn LABEL = new QueryColumn(this, "label");

    /**
     * 手机号
     */
    public final QueryColumn PHONE = new QueryColumn(this, "phone");

    /**
     * 详细地址
     */
    public final QueryColumn DETAIL = new QueryColumn(this, "detail");

    /**
     * 用户id
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    /**
     * 市级区划编号
     */
    public final QueryColumn CITY_CODE = new QueryColumn(this, "city_code");

    /**
     * 市级名称
     */
    public final QueryColumn CITY_NAME = new QueryColumn(this, "city_name");

    /**
     * 收货人
     */
    public final QueryColumn CONSIGNEE = new QueryColumn(this, "consignee");

    /**
     * 默认 0 否 1是
     */
    public final QueryColumn IS_DEFAULT = new QueryColumn(this, "is_default");

    /**
     * 是否删除
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
     * 修改人
     */
    public final QueryColumn UPDATE_USER = new QueryColumn(this, "update_user");

    /**
     * 区级区划编号
     */
    public final QueryColumn DISTRICT_CODE = new QueryColumn(this, "district_code");

    /**
     * 区级名称
     */
    public final QueryColumn DISTRICT_NAME = new QueryColumn(this, "district_name");

    /**
     * 省级区划编号
     */
    public final QueryColumn PROVINCE_CODE = new QueryColumn(this, "province_code");

    /**
     * 省级名称
     */
    public final QueryColumn PROVINCE_NAME = new QueryColumn(this, "province_name");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, USER_ID, CONSIGNEE, SEX, PHONE, PROVINCE_CODE, PROVINCE_NAME, CITY_CODE, CITY_NAME, DISTRICT_CODE, DISTRICT_NAME, DETAIL, LABEL, IS_DEFAULT, CREATE_TIME, UPDATE_TIME, CREATE_USER, UPDATE_USER, IS_DELETED};

    public TakeoutAddressTableDef() {
        super("", "takeout_address");
    }

    private TakeoutAddressTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public TakeoutAddressTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new TakeoutAddressTableDef("", "takeout_address", alias));
    }

}
