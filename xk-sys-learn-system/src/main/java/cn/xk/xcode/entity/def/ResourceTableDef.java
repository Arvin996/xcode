package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author Arvin
 * @since 2024-06-21
 */
public class ResourceTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final ResourceTableDef RESOURCE_PO = new ResourceTableDef();

    /**
     * 资源自增主键
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 资源是否启用，0启用 1未启用
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 资源名称
     */
    public final QueryColumn RESOURCE_NAME = new QueryColumn(this, "resource_name");

    /**
     * 资源路径 唯一
     */
    public final QueryColumn RESOURCE_CODE = new QueryColumn(this, "resource_code");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, RESOURCE_CODE, RESOURCE_NAME, STATUS, UPDATE_TIME, CREATE_TIME};

    public ResourceTableDef() {
        super("", "system_resource");
    }

    private ResourceTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public ResourceTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new ResourceTableDef("", "system_resource", alias));
    }

}
