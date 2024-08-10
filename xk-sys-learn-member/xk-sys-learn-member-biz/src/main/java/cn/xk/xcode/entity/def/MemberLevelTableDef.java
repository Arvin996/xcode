package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author lenovo
 * @since 2024-08-05
 */
public class MemberLevelTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final MemberLevelTableDef MEMBER_LEVEL_PO = new MemberLevelTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 等级名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 等级
     */
    public final QueryColumn LEVEL = new QueryColumn(this, "level");

    /**
     * 等级头像
     */
    public final QueryColumn LEVEL_ICON = new QueryColumn(this, "level_icon");

    /**
     * 升级所需经验
     */
    public final QueryColumn EXPERIENCE = new QueryColumn(this, "experience");

    /**
     * 享受折扣
     */
    public final QueryColumn DISCOUNT_PERCENT = new QueryColumn(this, "discount_percent");

    /**
     * 等级背景图
     */
    public final QueryColumn LEVEL_BACKGROUND = new QueryColumn(this, "level_background");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, LEVEL, EXPERIENCE, DISCOUNT_PERCENT, LEVEL_ICON, LEVEL_BACKGROUND};

    public MemberLevelTableDef() {
        super("", "member_level");
    }

    private MemberLevelTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public MemberLevelTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new MemberLevelTableDef("", "member_level", alias));
    }

}
