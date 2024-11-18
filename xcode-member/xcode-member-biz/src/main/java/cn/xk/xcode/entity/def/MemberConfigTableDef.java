package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author lenovo
 * @since 2024-08-05
 */
public class MemberConfigTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final MemberConfigTableDef MEMBER_CONFIG_PO = new MemberConfigTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 一元赠送的积分数量
     */
    public final QueryColumn GIVEN_POINT_ADD = new QueryColumn(this, "given_point_add");

    /**
     * 最大使用积分数
     */
    public final QueryColumn MAX_POINT_DEDUCT = new QueryColumn(this, "max_point_deduct");

    /**
     * 1积分抵扣多少钱 单位：分
     */
    public final QueryColumn POINT_DEDUCT_UNIT = new QueryColumn(this, "point_deduct_unit");

    /**
     * 积分抵用开关0 开启 1关闭
     */
    public final QueryColumn POINT_DEDUCT_ENABLE = new QueryColumn(this, "point_deduct_enable");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, POINT_DEDUCT_ENABLE, POINT_DEDUCT_UNIT, MAX_POINT_DEDUCT, GIVEN_POINT_ADD};

    public MemberConfigTableDef() {
        super("", "member_config");
    }

    private MemberConfigTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public MemberConfigTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new MemberConfigTableDef("", "member_config", alias));
    }

}
