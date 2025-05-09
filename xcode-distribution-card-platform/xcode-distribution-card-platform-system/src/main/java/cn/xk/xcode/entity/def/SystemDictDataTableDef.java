package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author xuk
 * @since 2025-05-09
 */
public class SystemDictDataTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final SystemDictDataTableDef SYSTEM_DICT_DATA_PO = new SystemDictDataTableDef();

    /**
     * 字典编码
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 字典排序
     */
    public final QueryColumn SORT = new QueryColumn(this, "sort");

    /**
     * 颜色类型
     */
    public final QueryColumn COLOR = new QueryColumn(this, "color");

    /**
     * 字典标签
     */
    public final QueryColumn LABEL = new QueryColumn(this, "label");

    /**
     * 字典键值
     */
    public final QueryColumn VALUE = new QueryColumn(this, "value");

    /**
     * 备注
     */
    public final QueryColumn REMARK = new QueryColumn(this, "remark");

    /**
     * 状态（0正常 1停用）
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 字典类型
     */
    public final QueryColumn DICT_TYPE = new QueryColumn(this, "dict_type");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, SORT, LABEL, VALUE, DICT_TYPE, STATUS, COLOR, REMARK, IS_DELETED, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER};

    public SystemDictDataTableDef() {
        super("", "system_dict_data");
    }

    private SystemDictDataTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public SystemDictDataTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new SystemDictDataTableDef("", "system_dict_data", alias));
    }

}
