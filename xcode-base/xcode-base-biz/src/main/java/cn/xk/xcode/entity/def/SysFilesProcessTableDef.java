package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author Administrator
 * @since 2025-05-22
 */
public class SysFilesProcessTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final SysFilesProcessTableDef SYS_FILES_PROCESS_PO = new SysFilesProcessTableDef();

    /**
     * 任务id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * minio桶
     */
    public final QueryColumn BUCKET = new QueryColumn(this, "bucket");

    /**
     * 失败原因
     */
    public final QueryColumn ERR_MSG = new QueryColumn(this, "err_msg");

    /**
     * 对应于file表中的id 表示要进行转码
     */
    public final QueryColumn FILE_ID = new QueryColumn(this, "file_id");

    /**
     * 状态 0待处理 1处理中 2处理成功 3处理失败
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 文件名
     */
    public final QueryColumn FILE_NAME = new QueryColumn(this, "file_name");

    /**
     * 失败次数
     */
    public final QueryColumn FAIL_COUNT = new QueryColumn(this, "fail_count");

    /**
     * 完成时间
     */
    public final QueryColumn FINISH_TIME = new QueryColumn(this, "finish_time");

    /**
     * 文件在minio中的访问路劲
     */
    public final QueryColumn OBJECT_NAME = new QueryColumn(this, "object_name");

    /**
     * 上传时间
     */
    public final QueryColumn UPLOAD_TIME = new QueryColumn(this, "upload_time");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, FILE_ID, FILE_NAME, BUCKET, OBJECT_NAME, STATUS, FAIL_COUNT, UPLOAD_TIME, FINISH_TIME, ERR_MSG};

    public SysFilesProcessTableDef() {
        super("", "infra_sys_files_process");
    }

    private SysFilesProcessTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public SysFilesProcessTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new SysFilesProcessTableDef("", "infra_sys_files_process", alias));
    }

}
