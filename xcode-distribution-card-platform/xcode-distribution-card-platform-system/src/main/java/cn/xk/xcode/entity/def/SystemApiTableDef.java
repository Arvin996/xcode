package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author xuk
 * @since 2025-05-09
 */
public class SystemApiTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final SystemApiTableDef SYSTEM_API_PO = new SystemApiTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 接口权限标识
     */
    public final QueryColumn API_CODE = new QueryColumn(this, "api_code");

    /**
     * 接口描述
     */
    public final QueryColumn API_DESC = new QueryColumn(this, "api_desc");

    /**
     * 接口路径
     */
    public final QueryColumn API_PATH = new QueryColumn(this, "api_path");

    
    public final QueryColumn PRODUCT_NAME = new QueryColumn(this, "product_name");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, PRODUCT_NAME, API_CODE, API_PATH, API_DESC};

    public SystemApiTableDef() {
        super("", "system_api");
    }

    private SystemApiTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public SystemApiTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new SystemApiTableDef("", "system_api", alias));
    }

}
