package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 * 表定义层。
 *
 * @author Administrator
 * @since 2025-05-22
 */
public class SysFilesTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public static final SysFilesTableDef SYS_FILES_PO = new SysFilesTableDef();

    /**
     * 文件id  md5值
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 文件tag
     */
    public final QueryColumn E_TAG = new QueryColumn(this, "e_tag");

    /**
     * 文件桶
     */
    public final QueryColumn BUCKET = new QueryColumn(this, "bucket");

    /**
     * 文件名
     */
    public final QueryColumn FILE_NAME = new QueryColumn(this, "file_name");

    /**
     * 文件大小
     */
    public final QueryColumn FILE_SIZE = new QueryColumn(this, "file_size");

    /**
     * 上传时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 上传人
     */
    public final QueryColumn CREATE_USER = new QueryColumn(this, "create_user");

    /**
     * 文件服务器中的key
     */
    public final QueryColumn OBJECT_NAME = new QueryColumn(this, "object_name");

    /**
     * 文件url
     */
    public final QueryColumn FILE_URL = new QueryColumn(this, "file_url");

    /**
     * 文件类型
     */
    public final QueryColumn CONTENT_TYPE = new QueryColumn(this, "content_type");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, FILE_NAME, BUCKET, OBJECT_NAME, FILE_URL, CONTENT_TYPE, FILE_SIZE, E_TAG, CREATE_USER, CREATE_TIME};

    public SysFilesTableDef() {
        super("", "infra_sys_files");
    }

    private SysFilesTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public SysFilesTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new SysFilesTableDef("", "infra_sys_files", alias));
    }

}
