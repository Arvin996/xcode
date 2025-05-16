package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author Administrator
 * @since 2025-05-15
 */
public class MessageTemplateParamsTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final MessageTemplateParamsTableDef MESSAGE_TEMPLATE_PARAMS = new MessageTemplateParamsTableDef();

    
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 参数名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 参数名称
     */
    public final QueryColumn DESC = new QueryColumn(this, "desc");

    
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");


    public final QueryColumn CREATE_USER = new QueryColumn(this, "create_user");

    /**
     * 模板id
     */
    public final QueryColumn TEMPLATE_ID = new QueryColumn(this, "template_id");

    
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");


    public final QueryColumn UPDATE_USER = new QueryColumn(this, "update_user");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TEMPLATE_ID, NAME, DESC, CREATE_USER, UPDATE_USER, CREATE_TIME, UPDATE_TIME};

    public MessageTemplateParamsTableDef() {
        super("", "message_template_params");
    }

    private MessageTemplateParamsTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public MessageTemplateParamsTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new MessageTemplateParamsTableDef("", "message_template_params", alias));
    }

}
