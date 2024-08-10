package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author lenovo
 * @since 2024-08-05
 */
public class MemberLoginRecordTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final MemberLoginRecordTableDef MEMBER_LOGIN_RECORD_PO = new MemberLoginRecordTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 用户id
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    /**
     * 登录ip
     */
    public final QueryColumn LOGIN_IP = new QueryColumn(this, "login_ip");

    /**
     * 登录时间
     */
    public final QueryColumn LOGIN_TIME = new QueryColumn(this, "login_time");

    /**
     * 登录地区
     */
    public final QueryColumn LOGIN_AREA_ID = new QueryColumn(this, "login_area_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, USER_ID, LOGIN_IP, LOGIN_TIME, LOGIN_AREA_ID};

    public MemberLoginRecordTableDef() {
        super("", "member_login_record");
    }

    private MemberLoginRecordTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public MemberLoginRecordTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new MemberLoginRecordTableDef("", "member_login_record", alias));
    }

}
