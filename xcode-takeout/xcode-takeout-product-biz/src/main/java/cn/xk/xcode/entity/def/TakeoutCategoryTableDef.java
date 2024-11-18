package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author Administrator
 * @since 2024-10-31
 */
public class TakeoutCategoryTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final TakeoutCategoryTableDef TAKEOUT_CATEGORY_PO = new TakeoutCategoryTableDef();

    /**
     * 主键
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 分类名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 顺序
     */
    public final QueryColumn SORT = new QueryColumn(this, "sort");

    /**
     * 类型   1 菜品分类 2 套餐分类
     */
    public final QueryColumn TYPE = new QueryColumn(this, "type");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TYPE, NAME, SORT, CREATE_TIME, UPDATE_TIME, CREATE_USER, UPDATE_USER};

    public TakeoutCategoryTableDef() {
        super("", "takeout_category");
    }

    private TakeoutCategoryTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public TakeoutCategoryTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new TakeoutCategoryTableDef("", "takeout_category", alias));
    }

}
