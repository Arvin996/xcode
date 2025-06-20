package cn.xk.xcode.entity.po;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  实体类。
 *
 * @author Administrator
 * @since 2025-05-22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("infra_sys_files_process")
public class SysFilesProcessPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务id
     */
    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 对应于file表中的id 表示要进行转码
     */
    private String fileId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * minio桶
     */
    private String bucket;

    /**
     * 文件在minio中的访问路劲
     */
    private String objectName;

    /**
     * 状态 0待处理 1处理中 2处理成功 3处理失败
     */
    private String status;

    /**
     * 失败次数
     */
    private Integer failCount;

    /**
     * 上传时间
     */
    private LocalDateTime uploadTime;

    /**
     * 完成时间
     */
    private LocalDateTime finishTime;

    /**
     * 失败原因
     */
    private String errMsg;

}
