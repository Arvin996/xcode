package cn.xk.xcode.entity.po;

import com.mybatisflex.annotation.Id;
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
 * @author lenovo
 * @since 2024-06-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("infra_sys_files")
public class SysFilesPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件id  md5值
     */
    @Id
    private Long id;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件存在于minio中的桶
     */
    private String bucket;

    /**
     * 文件在minio中的访问路径
     */
    private String filePath;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 上传人
     */
    private String createUser;

    /**
     * 上传时间
     */
    private LocalDateTime createTime;

}
