package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author lenovo
 * @since 2024-06-26
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
     * 文件存在于minio中的桶
     */
    public final QueryColumn BUCKET = new QueryColumn(this, "bucket");

    /**
     * 文件名
     */
    public final QueryColumn FILE_NAME = new QueryColumn(this, "file_name");

    /**
     * 文件在minio中的访问路径
     */
    public final QueryColumn FILE_PATH = new QueryColumn(this, "file_path");

    /**
     * 文件大小
     */
    public final QueryColumn FILE_SIZE = new QueryColumn(this, "file_size");

    /**
     * 文件类型
     */
    public final QueryColumn FILE_TYPE = new QueryColumn(this, "file_type");

    /**
     * 上传时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 上传人
     */
    public final QueryColumn CREATE_USER = new QueryColumn(this, "create_user");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, FILE_NAME, BUCKET, FILE_PATH, FILE_TYPE, FILE_SIZE, CREATE_USER, CREATE_TIME};

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
