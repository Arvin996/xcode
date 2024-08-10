package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author lenovo
 * @since 2024-08-05
 */
public class MemberSignRecordTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final MemberSignRecordTableDef MEMBER_SIGN_RECORD_PO = new MemberSignRecordTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 第几天签到
     */
    public final QueryColumn DAY = new QueryColumn(this, "day");

    /**
     * 签到获得的积分
     */
    public final QueryColumn POINT = new QueryColumn(this, "point");

    /**
     * 用户id
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    /**
     * 签到时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 签到获得的经验
     */
    public final QueryColumn EXPERIENCE = new QueryColumn(this, "experience");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, USER_ID, DAY, POINT, EXPERIENCE, CREATE_TIME};

    public MemberSignRecordTableDef() {
        super("", "member_sign_record");
    }

    private MemberSignRecordTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public MemberSignRecordTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new MemberSignRecordTableDef("", "member_sign_record", alias));
    }

}
