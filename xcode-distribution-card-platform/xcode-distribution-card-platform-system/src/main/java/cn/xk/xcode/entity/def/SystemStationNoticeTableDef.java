package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author xuk
 * @since 2025-05-09
 */
public class SystemStationNoticeTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final SystemStationNoticeTableDef SYSTEM_STATION_NOTICE_PO = new SystemStationNoticeTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 通知类型 0公告 1通知
     */
    public final QueryColumn TYPE = new QueryColumn(this, "type");

    /**
     * 消息titile
     */
    public final QueryColumn TITLE = new QueryColumn(this, "title");

    /**
     * 0 未读 1已读
     */
    public final QueryColumn IS_READ = new QueryColumn(this, "is_read");

    /**
     * 接收人
     */
    public final QueryColumn TO_USER = new QueryColumn(this, "to_user");

    /**
     * 消息正文
     */
    public final QueryColumn MESSAGE = new QueryColumn(this, "message");

    /**
     * 发送时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TITLE, TO_USER, MESSAGE, IS_READ, TYPE, CREATE_TIME};

    public SystemStationNoticeTableDef() {
        super("", "system_station_notice");
    }

    private SystemStationNoticeTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public SystemStationNoticeTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new SystemStationNoticeTableDef("", "system_station_notice", alias));
    }

}
