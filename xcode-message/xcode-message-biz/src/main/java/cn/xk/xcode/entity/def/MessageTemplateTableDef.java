package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author xuk
 * @since 2025-03-10
 */
public class MessageTemplateTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final MessageTemplateTableDef MESSAGE_TEMPLATE_PO = new MessageTemplateTableDef();

    
    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 0 启用 1禁用
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 模板内容信息 使用{}占位符
     */
    public final QueryColumn CONTENT = new QueryColumn(this, "content");

    /**
     * 模板所属用户id
     */
    public final QueryColumn CLIENT_ID = new QueryColumn(this, "client_id");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, CLIENT_ID, CONTENT, STATUS, CREATE_TIME, UPDATE_TIME};

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
