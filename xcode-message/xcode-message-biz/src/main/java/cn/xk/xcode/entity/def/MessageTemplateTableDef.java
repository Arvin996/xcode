package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 * 表定义层。
 *
 * @author Administrator
 * @since 2025-05-15
 */
public class MessageTemplateTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public static final MessageTemplateTableDef MESSAGE_TEMPLATE = new MessageTemplateTableDef();


    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 模板名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 0自定义模板 1 三方平台的模板
     */
    public final QueryColumn TYPE = new QueryColumn(this, "type");

    /**
     * 模板id
     */
    public final QueryColumn TEMPLATE_ID = new QueryColumn(this, "template_id");

    /**
     * 0 启用 1禁用
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 模板描述
     */
    public final QueryColumn DESC = new QueryColumn(this, "desc");

    /**
     * 模板内容信息 使用{}占位符
     */
    public final QueryColumn CONTENT = new QueryColumn(this, "content");

    /**
     * 0 已删除 1 未删除
     */
    public final QueryColumn IS_DELETED = new QueryColumn(this, "is_deleted");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 创建用户
     */
    public final QueryColumn CREATE_USER = new QueryColumn(this, "create_user");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 更新用户
     */
    public final QueryColumn UPDATE_USER = new QueryColumn(this, "update_user");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, TYPE, TEMPLATE_ID, CONTENT, STATUS, DESC, IS_DELETED, CREATE_USER, UPDATE_USER, CREATE_TIME, UPDATE_TIME};

    public MessageTemplateTableDef() {
        super("", "message_template");
    }

    private MessageTemplateTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public MessageTemplateTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new MessageTemplateTableDef("", "message_template", alias));
    }

}
