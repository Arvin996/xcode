package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author Administrator
 * @since 2024-10-31
 */
public class TakeoutDishFlavorTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final TakeoutDishFlavorTableDef TAKEOUT_DISH_FLAVOR_PO = new TakeoutDishFlavorTableDef();

    /**
     * 主键id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 菜品id
     */
    public final QueryColumn DISH_ID = new QueryColumn(this, "dish_id");

    /**
     * 口味id
     */
    public final QueryColumn FLAVOR_ID = new QueryColumn(this, "flavor_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, DISH_ID, FLAVOR_ID};

    public TakeoutDishFlavorTableDef() {
        super("", "takeout_dish_flavor");
    }

    private TakeoutDishFlavorTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public TakeoutDishFlavorTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new TakeoutDishFlavorTableDef("", "takeout_dish_flavor", alias));
    }

}
