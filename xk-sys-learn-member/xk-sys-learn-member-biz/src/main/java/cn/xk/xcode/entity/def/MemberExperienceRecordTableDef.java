package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author lenovo
 * @since 2024-08-05
 */
public class MemberExperienceRecordTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final MemberExperienceRecordTableDef MEMBER_EXPERIENCE_RECORD_PO = new MemberExperienceRecordTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 业务类型对应的业务id
     */
    public final QueryColumn BIZ_ID = new QueryColumn(this, "biz_id");

    /**
     * 业务类型对应的标题
     */
    public final QueryColumn TITLE = new QueryColumn(this, "title");

    /**
     * 用户id
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    /**
     * 增加或者减少经验的业务类型
     */
    public final QueryColumn BIZ_TYPE = new QueryColumn(this, "biz_type");

    /**
     * 记录时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 变更经验
     */
    public final QueryColumn EXPERIENCE = new QueryColumn(this, "experience");

    /**
     * 业务类型对应的描述
     */
    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    /**
     * 变更后的总经验
     */
    public final QueryColumn TOTAL_EXPERIENCE = new QueryColumn(this, "total_experience");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, USER_ID, BIZ_TYPE, BIZ_ID, DESCRIPTION, TITLE, EXPERIENCE, TOTAL_EXPERIENCE, CREATE_TIME};

    public MemberExperienceRecordTableDef() {
        super("", "member_experience_record");
    }

    private MemberExperienceRecordTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public MemberExperienceRecordTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new MemberExperienceRecordTableDef("", "member_experience_record", alias));
    }

}
