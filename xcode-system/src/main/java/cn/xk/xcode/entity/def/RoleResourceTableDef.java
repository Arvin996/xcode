package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author Arvin
 * @since 2024-06-21
 */
public class RoleResourceTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final RoleResourceTableDef ROLE_RESOURCE_PO = new RoleResourceTableDef();

    /**
     * 自增主键
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 角色id
     */
    public final QueryColumn ROLE_ID = new QueryColumn(this, "role_id");

    /**
     * 资源id
     */
    public final QueryColumn RESOURCE_ID = new QueryColumn(this, "resource_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, RESOURCE_ID, ROLE_ID};

    public RoleResourceTableDef() {
        super("", "system_role_resource");
    }

    private RoleResourceTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public RoleResourceTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new RoleResourceTableDef("", "system_role_resource", alias));
    }

}
