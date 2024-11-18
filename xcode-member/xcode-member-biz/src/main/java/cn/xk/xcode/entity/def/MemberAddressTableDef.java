package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author lenovo
 * @since 2024-08-05
 */
public class MemberAddressTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final MemberAddressTableDef MEMBER_ADDRESS_PO = new MemberAddressTableDef();

    /**
     * 主键自增
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 收件人姓名
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 收件人手机号
     */
    public final QueryColumn PHONE = new QueryColumn(this, "phone");

    /**
     * 地区id
     */
    public final QueryColumn AREA_ID = new QueryColumn(this, "area_id");

    /**
     * 用户id
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    /**
     * 详细的收件地址 如楼层+门牌号
     */
    public final QueryColumn DETAIL_ADDRESS = new QueryColumn(this, "detail_address");

    /**
     * 是否是默认地址
     */
    public final QueryColumn DEFAULT_ADDRESS = new QueryColumn(this, "default_address");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, USER_ID, NAME, PHONE, AREA_ID, DETAIL_ADDRESS, DEFAULT_ADDRESS};

    public MemberAddressTableDef() {
        super("", "member_address");
    }

    private MemberAddressTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public MemberAddressTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new MemberAddressTableDef("", "member_address", alias));
    }

}
