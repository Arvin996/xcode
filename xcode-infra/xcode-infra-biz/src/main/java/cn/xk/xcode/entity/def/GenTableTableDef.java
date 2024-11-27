package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author xuk
 * @since 2024-11-25
 */
public class GenTableTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final GenTableTableDef GEN_TABLE_PO = new GenTableTableDef();

    /**
     * 备注
     */
    public final QueryColumn REMARK = new QueryColumn(this, "remark");

    /**
     * 生成路径（不填默认项目路径）
     */
    public final QueryColumn GEN_PATH = new QueryColumn(this, "gen_path");

    /**
     * 生成代码方式（0zip压缩包 1自定义路径）
     */
    public final QueryColumn GEN_TYPE = new QueryColumn(this, "gen_type");

    /**
     * 其它生成选项
     */
    public final QueryColumn OPTIONS = new QueryColumn(this, "options");

    /**
     * 编号
     */
    public final QueryColumn TABLE_ID = new QueryColumn(this, "table_id");

    /**
     * 创建者
     */
    public final QueryColumn CREATE_USER = new QueryColumn(this, "create_user");

    /**
     * 更新者
     */
    public final QueryColumn UPDATE_USER = new QueryColumn(this, "update_user");

    /**
     * 实体类名称
     */
    public final QueryColumn CLASS_NAME = new QueryColumn(this, "class_name");

    /**
     * 表名称
     */
    public final QueryColumn TABLE_NAME = new QueryColumn(this, "table_name");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 生成模块名
     */
    public final QueryColumn MODULE_NAME = new QueryColumn(this, "module_name");

    /**
     * 前端模板类型（element-ui模版 element-plus模版）
     */
    public final QueryColumn TPL_WEB_TYPE = new QueryColumn(this, "tpl_web_type");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 生成包路径
     */
    public final QueryColumn PACKAGE_NAME = new QueryColumn(this, "package_name");

    /**
     * 使用的模板（crud单表操作 tree树表操作）
     */
    public final QueryColumn TPL_CATEGORY = new QueryColumn(this, "tpl_category");

    /**
     * 生成业务名
     */
    public final QueryColumn BUSINESS_NAME = new QueryColumn(this, "business_name");

    /**
     * 生成功能名
     */
    public final QueryColumn FUNCTION_NAME = new QueryColumn(this, "function_name");

    /**
     * 关联子表的表名
     */
    public final QueryColumn SUB_TABLE_NAME = new QueryColumn(this, "sub_table_name");

    /**
     * 表描述
     */
    public final QueryColumn TABLE_COMMENT = new QueryColumn(this, "table_comment");

    /**
     * 生成功能作者
     */
    public final QueryColumn FUNCTION_AUTHOR = new QueryColumn(this, "function_author");

    /**
     * 子表关联的外键名
     */
    public final QueryColumn SUB_TABLE_FK_NAME = new QueryColumn(this, "sub_table_fk_name");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{TABLE_ID, TABLE_NAME, TABLE_COMMENT, SUB_TABLE_NAME, SUB_TABLE_FK_NAME, CLASS_NAME, TPL_CATEGORY, TPL_WEB_TYPE, PACKAGE_NAME, MODULE_NAME, BUSINESS_NAME, FUNCTION_NAME, FUNCTION_AUTHOR, GEN_TYPE, GEN_PATH, OPTIONS, CREATE_USER, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK};

    public GenTableTableDef() {
        super("", "infra_gen_table");
    }

    private GenTableTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public GenTableTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new GenTableTableDef("", "infra_gen_table", alias));
    }

}
