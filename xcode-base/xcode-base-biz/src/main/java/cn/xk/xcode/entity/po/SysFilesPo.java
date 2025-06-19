package cn.xk.xcode.entity.po;

import com.mybatisflex.annotation.Column;
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
 * @author Administrator
 * @since 2025-05-22
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
    private String id;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件桶
     */
    private String bucket;

    /**
     * 文件服务器中的key
     */
    private String objectName;

    /**
     * 文件访问url
     */
    private String fileUrl;

    /**
     * 文件类型
     */
    private String contentType;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件tag
     */
    private String eTag;

    /**
     * 上传人
     */
    private String createUser;

    /**
     * 上传时间
     */
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

}
