package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author lenovo
 * @since 2024-08-05
 */
public class MemberPointRecordTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final MemberPointRecordTableDef MEMBER_POINT_RECORD_PO = new MemberPointRecordTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 变更的业务id
     */
    public final QueryColumn BIZ_ID = new QueryColumn(this, "biz_id");

    /**
     * 变更业务类型的标题
     */
    public final QueryColumn TITLE = new QueryColumn(this, "title");

    /**
     * 用户id
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    /**
     * 变更的业务类型
     */
    public final QueryColumn BIZ_TYPE = new QueryColumn(this, "biz_type");

    /**
     * 变更时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 变更后的总积分
     */
    public final QueryColumn TOTAL_POINT = new QueryColumn(this, "total_point");

    /**
     * 变更积分
     */
    public final QueryColumn CHANGE_POINT = new QueryColumn(this, "change_point");

    /**
     * 变更业务描述
     */
    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, USER_ID, BIZ_TYPE, BIZ_ID, TITLE, DESCRIPTION, CHANGE_POINT, TOTAL_POINT, CREATE_TIME};

    public MemberPointRecordTableDef() {
        super("", "member_point_record");
    }

    private MemberPointRecordTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public MemberPointRecordTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new MemberPointRecordTableDef("", "member_point_record", alias));
    }

}
