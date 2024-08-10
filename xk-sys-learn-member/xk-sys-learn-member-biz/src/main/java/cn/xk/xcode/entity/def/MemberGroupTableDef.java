package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author lenovo
 * @since 2024-08-05
 */
public class MemberGroupTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final MemberGroupTableDef MEMBER_GROUP_PO = new MemberGroupTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 分组名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 是否启用 0启用 1未启用
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, STATUS};

    public MemberGroupTableDef() {
        super("", "member_group");
    }

    private MemberGroupTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public MemberGroupTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new MemberGroupTableDef("", "member_group", alias));
    }

}
