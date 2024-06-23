package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author Arvin
 * @since 2024-06-21
 */
public class UserTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final UserTableDef USER_PO = new UserTableDef();

    /**
     * 用户id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * qq
     */
    public final QueryColumn QQ = new QueryColumn(this, "qq");

    /**
     * 微信
     */
    public final QueryColumn WX = new QueryColumn(this, "wx");

    /**
     * 手机号
     */
    public final QueryColumn MOBILE = new QueryColumn(this, "mobile");

    /**
     * 用户状态 0在线 1离线 2封禁
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 用户头像
     */
    public final QueryColumn USERPIC = new QueryColumn(this, "userpic");

    /**
     * 用户昵称
     */
    public final QueryColumn NICKNAME = new QueryColumn(this, "nickname");

    /**
     * 用户登录密码
     */
    public final QueryColumn PASSWORD = new QueryColumn(this, "password");

    /**
     * 用户名 不能重复
     */
    public final QueryColumn USERNAME = new QueryColumn(this, "username");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, USERNAME, PASSWORD, NICKNAME, USERPIC, QQ, WX, MOBILE, STATUS, CREATE_TIME, UPDATE_TIME};

    public UserTableDef() {
        super("", "system_user");
    }

    private UserTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public UserTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new UserTableDef("", "system_user", alias));
    }

}
