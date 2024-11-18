package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author lenovo
 * @since 2024-07-08
 */
public class ThirdUserTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final ThirdUserTableDef THIRD_USER_PO = new ThirdUserTableDef();

    /**
     * 微信登录用户id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 真实姓名
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 手机号
     */
    public final QueryColumn PHONE = new QueryColumn(this, "phone");

    /**
     * 用户头像url
     */
    public final QueryColumn AVATAR = new QueryColumn(this, "avatar");

    /**
     * 绑定用户表
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    /**
     * 微信unionid
     */
    public final QueryColumn UNION_ID = new QueryColumn(this, "union_id");

    /**
     * 用户昵称
     */
    public final QueryColumn NICKNAME = new QueryColumn(this, "nickname");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, USER_ID, UNION_ID, NICKNAME, NAME, PHONE, AVATAR, CREATE_TIME};

    public ThirdUserTableDef() {
        super("", "system_third_user");
    }

    private ThirdUserTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public ThirdUserTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new ThirdUserTableDef("", "system_third_user", alias));
    }

}
