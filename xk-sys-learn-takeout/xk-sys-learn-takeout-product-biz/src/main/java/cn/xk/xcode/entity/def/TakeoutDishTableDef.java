package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author Administrator
 * @since 2024-10-31
 */
public class TakeoutDishTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final TakeoutDishTableDef TAKEOUT_DISH_PO = new TakeoutDishTableDef();

    /**
     * 主键
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 商品码
     */
    public final QueryColumn CODE = new QueryColumn(this, "code");

    /**
     * 菜品名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 顺序
     */
    public final QueryColumn SORT = new QueryColumn(this, "sort");

    /**
     * 图片
     */
    public final QueryColumn IMAGE = new QueryColumn(this, "image");

    /**
     * 菜品价格
     */
    public final QueryColumn PRICE = new QueryColumn(this, "price");

    /**
     * 0 停售 1 起售
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 是否删除
     */
    public final QueryColumn IS_DELETED = new QueryColumn(this, "is_deleted");

    /**
     * 菜品分类id
     */
    public final QueryColumn CATEGORY_ID = new QueryColumn(this, "category_id");

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
     * 描述信息
     */
    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, CATEGORY_ID, PRICE, CODE, IMAGE, DESCRIPTION, STATUS, SORT, CREATE_TIME, UPDATE_TIME, CREATE_USER, UPDATE_USER, IS_DELETED};

    public TakeoutDishTableDef() {
        super("", "takeout_dish");
    }

    private TakeoutDishTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public TakeoutDishTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new TakeoutDishTableDef("", "takeout_dish", alias));
    }

}
