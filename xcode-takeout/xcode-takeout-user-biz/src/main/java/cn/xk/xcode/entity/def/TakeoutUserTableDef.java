package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author Administrator
 * @since 2024-10-29
 */
public class TakeoutUserTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final TakeoutUserTableDef TAKEOUT_USER_PO = new TakeoutUserTableDef();

    /**
     * 主键
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 性别
     */
    public final QueryColumn SEX = new QueryColumn(this, "sex");

    /**
     * 姓名
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 手机号
     */
    public final QueryColumn MOBILE = new QueryColumn(this, "mobile");

    /**
     * 密码
     */
    public final QueryColumn PASSWORD = new QueryColumn(this, "password");

    /**
     * 头像
     */
    public final QueryColumn AVATAR = new QueryColumn(this, "avatar");

    /**
     * 角色id
     */
    public final QueryColumn ROLE_ID = new QueryColumn(this, "role_id");

    /**
     * 状态 0:禁用，1:正常
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 身份证号
     */
    public final QueryColumn ID_NUMBER = new QueryColumn(this, "id_number");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, ROLE_ID, MOBILE, PASSWORD, SEX, ID_NUMBER, AVATAR, STATUS};

    public TakeoutUserTableDef() {
        super("", "takeout_user");
    }

    private TakeoutUserTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public TakeoutUserTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new TakeoutUserTableDef("", "takeout_user", alias));
    }

}
