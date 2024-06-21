package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author lenovo
 * @since 2024-06-21
 */
public class DictTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final DictTableDef DICT_PO = new DictTableDef();

    /**
     * 填充字段
     */
    public final QueryColumn PAD = new QueryColumn(this, "pad");

    /**
     * 字典id
     */
    public final QueryColumn CODE = new QueryColumn(this, "code");

    /**
     * 字典值
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 字典表备注，如排序
     */
    public final QueryColumn NOTE = new QueryColumn(this, "note");

    /**
     * 父字典id,一级为##
     */
    public final QueryColumn PAR_ID = new QueryColumn(this, "par_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{CODE, NAME, PAR_ID, NOTE, PAD};

    public DictTableDef() {
        super("", "system_dict");
    }

    private DictTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public DictTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new DictTableDef("", "system_dict", alias));
    }

}
