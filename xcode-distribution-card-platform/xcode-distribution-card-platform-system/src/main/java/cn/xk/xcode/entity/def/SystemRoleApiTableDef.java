package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author xuk
 * @since 2025-05-09
 */
public class SystemRoleApiTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final SystemRoleApiTableDef SYSTEM_ROLE_API_PO = new SystemRoleApiTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 接口id
     */
    public final QueryColumn API_ID = new QueryColumn(this, "api_id");

    /**
     * 角色id
     */
    public final QueryColumn ROLE_ID = new QueryColumn(this, "role_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, API_ID, ROLE_ID};

    public SystemRoleApiTableDef() {
        super("", "system_role_api");
    }

    private SystemRoleApiTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public SystemRoleApiTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new SystemRoleApiTableDef("", "system_role_api", alias));
    }

}
