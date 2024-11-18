package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author lenovo
 * @since 2024-08-05
 */
public class MemberUserTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final MemberUserTableDef MEMBER_USER_PO = new MemberUserTableDef();

    /**
     * 用户id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 用户密码
     */

    public final QueryColumn PASSWORD = new QueryColumn(this, "password");
    /**
     * 用户性别 0男 1女
     */
    public final QueryColumn SEX = new QueryColumn(this, "sex");

    /**
     * 用户邮箱 用于绑定
     */
    public final QueryColumn EMAIL = new QueryColumn(this, "email");

    /**
     * 用户积分
     */
    public final QueryColumn POINT = new QueryColumn(this, "point");

    /**
     * 用户头像
     */
    public final QueryColumn AVATAR = new QueryColumn(this, "avatar");

    /**
     * 用户手机号
     */
    public final QueryColumn MOBILE = new QueryColumn(this, "mobile");

    /**
     * 0 正常 1禁用
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 用户标签
     */
    public final QueryColumn TAG_IDS = new QueryColumn(this, "tag_ids");

    /**
     * 用户分组id
     */
    public final QueryColumn GROUP_ID = new QueryColumn(this, "group_id");

    /**
     * 用户等级
     */
    public final QueryColumn LEVEL_ID = new QueryColumn(this, "level_id");

    /**
     * 最近一次登录ip
     */
    public final QueryColumn LOGIN_IP = new QueryColumn(this, "login_ip");

    /**
     * 用户生日
     */
    public final QueryColumn BIRTHDAY = new QueryColumn(this, "birthday");

    /**
     * 用户昵称
     */
    public final QueryColumn NICKNAME = new QueryColumn(this, "nickname");

    /**
     * 登录时间
     */
    public final QueryColumn LOGIN_TIME = new QueryColumn(this, "login_time");

    /**
     * 用户经验
     */
    public final QueryColumn EXPERIENCE = new QueryColumn(this, "experience");

    /**
     * 最近一次登录地区
     */
    public final QueryColumn LOGIN_AREA_ID = new QueryColumn(this, "login_area_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, PASSWORD, MOBILE, EMAIL, STATUS, NICKNAME, LEVEL_ID, EXPERIENCE, AVATAR, POINT, TAG_IDS, GROUP_ID, SEX, BIRTHDAY, LOGIN_IP, LOGIN_AREA_ID, LOGIN_TIME};

    public MemberUserTableDef() {
        super("", "member_user");
    }

    private MemberUserTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public MemberUserTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new MemberUserTableDef("", "member_user", alias));
    }

}
