package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author Administrator
 * @since 2024-10-29
 */
public class TakeoutRolePermissionTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final TakeoutRolePermissionTableDef TAKEOUT_ROLE_PERMISSION_PO = new TakeoutRolePermissionTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 角色id
     */
    public final QueryColumn ROLE_ID = new QueryColumn(this, "role_id");

    /**
     * 权限id
     */
    public final QueryColumn PERMISSION_ID = new QueryColumn(this, "permission_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, ROLE_ID, PERMISSION_ID};

    public TakeoutRolePermissionTableDef() {
        super("", "takeout_role_permission");
    }

    private TakeoutRolePermissionTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public TakeoutRolePermissionTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new TakeoutRolePermissionTableDef("", "takeout_role_permission", alias));
    }

}
