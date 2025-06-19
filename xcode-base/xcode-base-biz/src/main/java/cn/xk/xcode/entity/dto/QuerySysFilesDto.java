package cn.xk.xcode.entity.dto;

import cn.xk.xcode.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @Author xuk
 * @Date 2025/5/22 14:11
 * @Version 1.0.0
 * @Description QuerySysFilesDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "QuerySysFilesDto", description = "QuerySysFilesDto 查询文件列表")
public class QuerySysFilesDto extends PageParam {

    @Schema(description = "id")
    private String fileId;

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
     * 文件类型
     */
    @Schema(description = "文件类型")
    private String contentType;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
