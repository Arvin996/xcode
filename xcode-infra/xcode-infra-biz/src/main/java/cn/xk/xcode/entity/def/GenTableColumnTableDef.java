package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author xuk
 * @since 2024-11-25
 */
public class GenTableColumnTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final GenTableColumnTableDef GEN_TABLE_COLUMN_PO = new GenTableColumnTableDef();

    /**
     * 是否主键（1是）
     */
    public final QueryColumn IS_PK = new QueryColumn(this, "is_pk");

    /**
     * 排序
     */
    public final QueryColumn SORT = new QueryColumn(this, "sort");

    /**
     * 是否编辑字段（1是）
     */
    public final QueryColumn IS_EDIT = new QueryColumn(this, "is_edit");

    /**
     * 是否列表字段（1是）
     */
    public final QueryColumn IS_LIST = new QueryColumn(this, "is_list");

    /**
     * 是否查询字段（1是）
     */
    public final QueryColumn IS_QUERY = new QueryColumn(this, "is_query");

    /**
     * 归属表编号
     */
    public final QueryColumn TABLE_ID = new QueryColumn(this, "table_id");

    /**
     * 编号
     */
    public final QueryColumn COLUMN_ID = new QueryColumn(this, "column_id");

    /**
     * 创建者
     */
    public final QueryColumn CREATE_USER = new QueryColumn(this, "create_user");

    /**
     * 字典类型
     */
    public final QueryColumn DICT_TYPE = new QueryColumn(this, "dict_type");

    /**
     * 显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）
     */
    public final QueryColumn HTML_TYPE = new QueryColumn(this, "html_type");

    /**
     * 是否为插入字段（1是）
     */
    public final QueryColumn IS_INSERT = new QueryColumn(this, "is_insert");

    /**
     * JAVA类型
     */
    public final QueryColumn JAVA_TYPE = new QueryColumn(this, "java_type");

    /**
     * 更新者
     */
    public final QueryColumn UPDATE_USER = new QueryColumn(this, "update_user");

    /**
     * JAVA字段名
     */
    public final QueryColumn JAVA_FIELD = new QueryColumn(this, "java_field");

    /**
     * 查询方式（等于、不等于、大于、小于、范围）
     */
    public final QueryColumn QUERY_TYPE = new QueryColumn(this, "query_type");

    /**
     * 列名称
     */
    public final QueryColumn COLUMN_NAME = new QueryColumn(this, "column_name");

    /**
     * 列类型
     */
    public final QueryColumn COLUMN_TYPE = new QueryColumn(this, "column_type");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 是否必填（1是）
     */
    public final QueryColumn IS_REQUIRED = new QueryColumn(this, "is_required");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 是否自增（1是）
     */
    public final QueryColumn IS_INCREMENT = new QueryColumn(this, "is_increment");

    /**
     * 列描述
     */
    public final QueryColumn COLUMN_COMMENT = new QueryColumn(this, "column_comment");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{COLUMN_ID, TABLE_ID, COLUMN_NAME, COLUMN_COMMENT, COLUMN_TYPE, JAVA_TYPE, JAVA_FIELD, IS_PK, IS_INCREMENT, IS_REQUIRED, IS_INSERT, IS_EDIT, IS_LIST, IS_QUERY, QUERY_TYPE, HTML_TYPE, DICT_TYPE, SORT, CREATE_USER, CREATE_TIME, UPDATE_USER, UPDATE_TIME};

    public GenTableColumnTableDef() {
        super("", "infra_gen_table_column");
    }

    private GenTableColumnTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public GenTableColumnTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new GenTableColumnTableDef("", "infra_gen_table_column", alias));
    }

}
