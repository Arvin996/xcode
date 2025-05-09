package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author xuk
 * @since 2025-05-09
 */
public class SystemMenuTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final SystemMenuTableDef SYSTEM_MENU_PO = new SystemMenuTableDef();

    /**
     * 菜单ID
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 菜单图标
     */
    public final QueryColumn ICON = new QueryColumn(this, "icon");

    /**
     * 菜单名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 路由地址
     */
    public final QueryColumn PATH = new QueryColumn(this, "path");

    /**
     * 显示顺序
     */
    public final QueryColumn SORT = new QueryColumn(this, "sort");

    /**
     * 是否可见 0可见 1不可见
     */
    public final QueryColumn VISIBLE = new QueryColumn(this, "visible");

    /**
     * 父菜单ID
     */
    public final QueryColumn PARENT_ID = new QueryColumn(this, "parent_id");

    /**
     * 组件路径
     */
    public final QueryColumn COMPONENT = new QueryColumn(this, "component");

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
     * 组件名
     */
    public final QueryColumn COMPONENT_NAME = new QueryColumn(this, "component_name");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, SORT, PARENT_ID, PATH, ICON, COMPONENT, COMPONENT_NAME, VISIBLE, IS_DELETED, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER};

    public SystemMenuTableDef() {
        super("", "system_menu");
    }

    private SystemMenuTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public SystemMenuTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new SystemMenuTableDef("", "system_menu", alias));
    }

}
