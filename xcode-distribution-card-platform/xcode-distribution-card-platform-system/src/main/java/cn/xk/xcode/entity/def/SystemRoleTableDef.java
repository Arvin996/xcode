package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author xuk
 * @since 2025-05-09
 */
public class SystemRoleTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final SystemRoleTableDef SYSTEM_ROLE_PO = new SystemRoleTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 角色权限名
     */
    public final QueryColumn CODE = new QueryColumn(this, "code");

    /**
     * 系统角色名
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 是否删除 0未删除 1已删除
     */
    public final QueryColumn IS_DELETED = new QueryColumn(this, "is_deleted");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 创建人
     */
    public final QueryColumn CREATE_USER = new QueryColumn(this, "create_user");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 更新人
     */
    public final QueryColumn UPDATE_USER = new QueryColumn(this, "update_user");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, CODE, NAME, IS_DELETED, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER};

    public SystemRoleTableDef() {
        super("", "system_role");
    }

    private SystemRoleTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public SystemRoleTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new SystemRoleTableDef("", "system_role", alias));
    }

}
