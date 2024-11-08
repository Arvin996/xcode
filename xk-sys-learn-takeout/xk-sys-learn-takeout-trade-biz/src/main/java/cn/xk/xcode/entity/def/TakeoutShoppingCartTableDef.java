package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author xuk
 * @since 2024-11-06
 */
public class TakeoutShoppingCartTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final TakeoutShoppingCartTableDef TAKEOUT_SHOPPING_CART_PO = new TakeoutShoppingCartTableDef();

    /**
     * 主键
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 图片
     */
    public final QueryColumn IMAGE = new QueryColumn(this, "image");

    /**
     * 金额
     */
    public final QueryColumn AMOUNT = new QueryColumn(this, "amount");

    /**
     * 菜品id
     */
    public final QueryColumn DISH_ID = new QueryColumn(this, "dish_id");

    /**
     * 数量
     */
    public final QueryColumn NUMBER = new QueryColumn(this, "number");

    /**
     * 主键
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    /**
     * 套餐id
     */
    public final QueryColumn SETMEAL_ID = new QueryColumn(this, "setmeal_id");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 口味
     */
    public final QueryColumn DISH_FLAVOR = new QueryColumn(this, "dish_flavor");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, IMAGE, USER_ID, DISH_ID, SETMEAL_ID, DISH_FLAVOR, NUMBER, AMOUNT, CREATE_TIME};

    public TakeoutShoppingCartTableDef() {
        super("", "takeout_shopping_cart");
    }

    private TakeoutShoppingCartTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public TakeoutShoppingCartTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new TakeoutShoppingCartTableDef("", "takeout_shopping_cart", alias));
    }

}
