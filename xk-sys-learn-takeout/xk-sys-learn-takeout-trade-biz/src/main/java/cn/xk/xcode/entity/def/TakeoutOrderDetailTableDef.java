package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author xuk
 * @since 2024-11-06
 */
public class TakeoutOrderDetailTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final TakeoutOrderDetailTableDef TAKEOUT_ORDER_DETAIL_PO = new TakeoutOrderDetailTableDef();

    /**
     * 主键
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 名字
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
     * 订单id
     */
    public final QueryColumn ORDER_ID = new QueryColumn(this, "order_id");

    /**
     * 套餐id
     */
    public final QueryColumn SETMEAL_ID = new QueryColumn(this, "setmeal_id");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, IMAGE, ORDER_ID, DISH_ID, SETMEAL_ID, DISH_FLAVOR, NUMBER, AMOUNT};

    public TakeoutOrderDetailTableDef() {
        super("", "takeout_order_detail");
    }

    private TakeoutOrderDetailTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public TakeoutOrderDetailTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new TakeoutOrderDetailTableDef("", "takeout_order_detail", alias));
    }

}
