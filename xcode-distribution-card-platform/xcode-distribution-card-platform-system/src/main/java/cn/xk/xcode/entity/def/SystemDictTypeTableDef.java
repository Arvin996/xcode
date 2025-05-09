package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author xuk
 * @since 2025-05-09
 */
public class SystemDictTypeTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final SystemDictTypeTableDef SYSTEM_DICT_TYPE_PO = new SystemDictTypeTableDef();

    /**
     * 字典主键
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 字典类型名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 字典类型码
     */
    public final QueryColumn TYPE = new QueryColumn(this, "type");

    /**
     * 备注
     */
    public final QueryColumn REMARK = new QueryColumn(this, "remark");

    /**
     * 状态（0正常 1停用）
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 是否删除 0未删除 1已删除
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
     * 更新人
     */
    public final QueryColumn UPDATE_USER = new QueryColumn(this, "update_user");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, TYPE, STATUS, REMARK, IS_DELETED, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER};

    public SystemDictTypeTableDef() {
        super("", "system_dict_type");
    }

    private SystemDictTypeTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public SystemDictTypeTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new SystemDictTypeTableDef("", "system_dict_type", alias));
    }

}
