package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author lenovo
 * @since 2024-08-05
 */
public class MemberLevelChangeRecordTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final MemberLevelChangeRecordTableDef MEMBER_LEVEL_CHANGE_RECORD_PO = new MemberLevelChangeRecordTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 发生变更的业务标题
     */
    public final QueryColumn TITLE = new QueryColumn(this, "title");

    /**
     * 用户id
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    /**
     * 变更后的等级id
     */
    public final QueryColumn LEVEL_ID = new QueryColumn(this, "level_id");

    /**
     * 变更后的等级id
     */
    public final QueryColumn LEVEL_NAME = new QueryColumn(this, "level_name");

    /**
     * 变更时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 发送变更的业务描述
     */
    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    /**
     * 变更后的用户总经验
     */
    public final QueryColumn TOTAL_EXPERIENCE = new QueryColumn(this, "total_experience");

    /**
     * 变更经验
     */
    public final QueryColumn CHANGE_EXPERIENCE = new QueryColumn(this, "change_experience");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, USER_ID, LEVEL_ID, LEVEL_NAME, TOTAL_EXPERIENCE, CHANGE_EXPERIENCE, TITLE, DESCRIPTION, CREATE_TIME};

    public MemberLevelChangeRecordTableDef() {
        super("", "member_level_change_record");
    }

    private MemberLevelChangeRecordTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public MemberLevelChangeRecordTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new MemberLevelChangeRecordTableDef("", "member_level_change_record", alias));
    }

}
