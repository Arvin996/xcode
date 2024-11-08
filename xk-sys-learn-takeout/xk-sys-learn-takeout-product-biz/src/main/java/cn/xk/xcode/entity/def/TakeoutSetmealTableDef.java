package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author Administrator
 * @since 2024-10-31
 */
public class TakeoutSetmealTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final TakeoutSetmealTableDef TAKEOUT_SETMEAL_PO = new TakeoutSetmealTableDef();

    /**
     * 主键
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 编码
     */
    public final QueryColumn CODE = new QueryColumn(this, "code");

    /**
     * 套餐名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 菜品库存
     */
    public final QueryColumn STOCK = new QueryColumn(this, "stock");

    /**
     * 图片
     */
    public final QueryColumn IMAGE = new QueryColumn(this, "image");

    /**
     * 套餐价格
     */
    public final QueryColumn PRICE = new QueryColumn(this, "price");

    /**
     * 状态 0:停用 1:启用
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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, CATEGORY_ID, NAME, PRICE, STOCK, STATUS, CODE, DESCRIPTION, IMAGE, CREATE_TIME, UPDATE_TIME, CREATE_USER, UPDATE_USER, IS_DELETED};

    public TakeoutSetmealTableDef() {
        super("", "takeout_setmeal");
    }

    private TakeoutSetmealTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public TakeoutSetmealTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new TakeoutSetmealTableDef("", "takeout_setmeal", alias));
    }

}
