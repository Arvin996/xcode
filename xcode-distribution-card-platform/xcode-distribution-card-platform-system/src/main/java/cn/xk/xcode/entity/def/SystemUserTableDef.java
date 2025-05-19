package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 * 表定义层。
 *
 * @author xuk
 * @since 2025-05-09
 */
public class SystemUserTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public static final SystemUserTableDef SYSTEM_USER_PO = new SystemUserTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 角色id
     */
    public final QueryColumn ROLE_ID = new QueryColumn(this, "role_id");

    /**
     * 年龄
     */
    public final QueryColumn AGE = new QueryColumn(this, "age");

    /**
     * 0 男  1女 2未知
     */
    public final QueryColumn SEX = new QueryColumn(this, "sex");

    /**
     * 头像
     */
    public final QueryColumn AVATAR = new QueryColumn(this, "avatar");

    /**
     * 手机号
     */
    public final QueryColumn MOBILE = new QueryColumn(this, "mobile");

    /**
     * 邮箱
     */
    public final QueryColumn EMAIL = new QueryColumn(this, "email");

    /**
     * ding_talk_webhook_token
     */
    public final QueryColumn DING_TALK_WEBHOOK_TOKEN = new QueryColumn(this, "ding_talk_webhook_token");

    /**
     * feishu_webhook_token
     */
    public final QueryColumn FEISHU_WEBHOOK_TOKEN = new QueryColumn(this, "feishu_webhook_token");

    /**
     * ding_talk_webhook_sign
     */
    public final QueryColumn DING_TALK_WEBHOOK_SECRET = new QueryColumn(this, "ding_talk_webhook_secret");

    /**
     * feishu_webhook_sign
     */
    public final QueryColumn FEISHU_WEBHOOK_SECRET = new QueryColumn(this, "feishu_webhook_secret");

    /**
     * 账号状态 0正常 1停用
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 用户昵称
     */
    public final QueryColumn NICKNAME = new QueryColumn(this, "nickname");

    /**
     * 登录密码
     */
    public final QueryColumn PASSWORD = new QueryColumn(this, "password");

    /**
     * 登录用户名
     */
    public final QueryColumn USERNAME = new QueryColumn(this, "username");

    /**
     * 是否删除 0未删除 1已删除
     */
    public final QueryColumn IS_DELETED = new QueryColumn(this, "is_deleted");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 创建人
     */
    public final QueryColumn CREATE_USER = new QueryColumn(this, "create_user");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 更新人
     */
    public final QueryColumn UPDATE_USER = new QueryColumn(this, "update_user");

    /**
     * 最后登录ip
     */
    public final QueryColumn LAST_LOGIN_IP = new QueryColumn(this, "last_login_ip");

    /**
     * 最后登录时间
     */
    public final QueryColumn LAST_LOGIN_TIME = new QueryColumn(this, "last_login_time");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, ROLE_ID, USERNAME, PASSWORD, NICKNAME, SEX, AGE, MOBILE, EMAIL, DING_TALK_WEBHOOK_SECRET, FEISHU_WEBHOOK_SECRET, DING_TALK_WEBHOOK_TOKEN, FEISHU_WEBHOOK_TOKEN, AVATAR, STATUS, LAST_LOGIN_IP, LAST_LOGIN_TIME, IS_DELETED, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER};

    public SystemUserTableDef() {
        super("", "system_user");
    }

    private SystemUserTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public SystemUserTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new SystemUserTableDef("", "system_user", alias));
    }

}
