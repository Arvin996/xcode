package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author Administrator
 * @since 2024-10-31
 */
public class TakeoutSetmealDishTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final TakeoutSetmealDishTableDef TAKEOUT_SETMEAL_DISH_PO = new TakeoutSetmealDishTableDef();

    /**
     * 主键
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 菜品名称 （冗余字段）
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 排序
     */
    public final QueryColumn SORT = new QueryColumn(this, "sort");

    /**
     * 菜品原价（冗余字段）
     */
    public final QueryColumn PRICE = new QueryColumn(this, "price");

    /**
     * 份数
     */
    public final QueryColumn COPIES = new QueryColumn(this, "copies");

    /**
     * 菜品id
     */
    public final QueryColumn DISH_ID = new QueryColumn(this, "dish_id");

    /**
     * 是否删除
     */
    public final QueryColumn IS_DELETED = new QueryColumn(this, "is_deleted");

    /**
     * 套餐id 
     */
    public final QueryColumn SETMEAL_ID = new QueryColumn(this, "setmeal_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, SETMEAL_ID, DISH_ID, NAME, PRICE, COPIES, SORT, IS_DELETED};

    public TakeoutSetmealDishTableDef() {
        super("", "takeout_setmeal_dish");
    }

    private TakeoutSetmealDishTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public TakeoutSetmealDishTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new TakeoutSetmealDishTableDef("", "takeout_setmeal_dish", alias));
    }

}
