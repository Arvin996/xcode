package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author Administrator
 * @since 2024-10-31
 */
public class TakeoutFlavorTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final TakeoutFlavorTableDef TAKEOUT_FLAVOR_PO = new TakeoutFlavorTableDef();

    /**
     * 主键
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 口味名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 口味数据list
     */
    public final QueryColumn VALUE = new QueryColumn(this, "value");

    /**
     * 是否删除
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
     * 修改人
     */
    public final QueryColumn UPDATE_USER = new QueryColumn(this, "update_user");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, VALUE, CREATE_TIME, UPDATE_TIME, CREATE_USER, UPDATE_USER, IS_DELETED};

    public TakeoutFlavorTableDef() {
        super("", "takeout_flavor");
    }

    private TakeoutFlavorTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public TakeoutFlavorTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new TakeoutFlavorTableDef("", "takeout_flavor", alias));
    }

}
