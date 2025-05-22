package cn.xk.xcode.entity.vo;

import com.mybatisflex.annotation.Column;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author xuk
 * @Date 2025/5/22 14:11
 * @Version 1.0.0
 * @Description SysFilesVo
 **/
@Data
@Schema(name = "SysFilesVo", description = "SysFilesVo 查询文件列表")
public class SysFilesVo {

    @Schema(description = "id")
    private String id;

    /**
     * 文件名
     */
    @Schema(description = "文件名")
    private String fileName;

    /**
     * 文件桶
     */
    @Schema(description = "文件桶")
    private String bucket;

    /**
     * 文件服务器中的key
     */
    @Schema(description = "文件服务器中的key")
    private String objectName;

    /**
     * 文件访问url
     */
    @Schema(description = "文件访问url")
    private String fileUrl;

    /**
     * 文件类型
     */
    @Schema(description = "文件类型")
    private String contentType;

    /**
     * 文件大小
     */
    @Schema(description = "文件大小")
    private Long fileSize;

    /**
     * 文件tag
     */
    @Schema(description = "文件tag")
    private String eTag;

    /**
     * 上传人
     */
    @Schema(description = "上传人")
    private String createUser;

    /**
     * 上传时间
     */
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;
}
