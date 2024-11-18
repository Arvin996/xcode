package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author lenovo
 * @since 2024-08-05
 */
public class MemberSignTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final MemberSignTableDef MEMBER_SIGN_PO = new MemberSignTableDef();

    /**
     * 签到第几天 星期一到星期天1-7
     */
    public final QueryColumn DAY = new QueryColumn(this, "day");

    /**
     * 奖励积分
     */
    public final QueryColumn POINT = new QueryColumn(this, "point");

    /**
     * 奖励经验
     */
    public final QueryColumn EXPERIENCE = new QueryColumn(this, "experience");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{DAY, POINT, EXPERIENCE, UPDATE_TIME};

    public MemberSignTableDef() {
        super("", "member_sign");
    }

    private MemberSignTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public MemberSignTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new MemberSignTableDef("", "member_sign", alias));
    }

}
