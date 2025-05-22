package cn.xk.xcode.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author xuk
 * @Date 2025/2/19 13:44
 * @Version 1.0.0
 * @Description MergeUploadChunkFliesRespVo
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "MergeUploadChunkFliesRespVo", description = "MergeUploadChunkFliesRespVo 合并切片文件返回")
public class MergeUploadChunkFliesRespVo {

    @Schema(description = "url 文件url")
    private String url;

    @Schema(description = "eTag 文件eTag")
    private String eTag;

    // 文件名
    @Schema(description = "filename 文件名")
    private String filename;

    // 对象名称 (带路径)
    @Schema(description = "objectName 文件上传路径")
    private String objectName;

    // 文件类型
    @Schema(description = "contextType 文件类型")
    private String contextType;
}
