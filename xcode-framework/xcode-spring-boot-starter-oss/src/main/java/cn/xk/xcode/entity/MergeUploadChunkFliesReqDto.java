package cn.xk.xcode.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Author xuk
 * @Date 2025/2/19 13:27
 * @Version 1.0.0
 * @Description MergeUploadChunkFliesReqDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "MergeUploadChunkFliesReqDto", description = "MergeUploadChunkFliesReqDto 合并切片文件")
@Builder
public class MergeUploadChunkFliesReqDto implements Serializable {

    @Schema(description = "uploadId 切片上传初始化时当前文件的唯一id")
    @NotNull(message = "uploadId 不能为空")
    private String uploadId;

    @Schema(description = "objectName 文件上传路径")
    @NotNull(message = "objectName 不能为空")
    private String objectName;

    @Schema(description = "bucketName 不传就是配置的默认桶")
    private String bucketName;

    @Schema(description = "contentType 文件类型")
    private String contentType;

    @Schema(description = "chunkFileInfos 切片文件信息")
    @NotEmpty(message = "chunkFileInfos 不能为空")
    private List<OssMergeChunkFileInfo> chunkFileInfos;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "OssMergeChunkFileInfo", description = "OssMergeChunkFileInfo 切片文件信息")
    public static class OssMergeChunkFileInfo{
        @Schema(description = "currentChunk 当前切片id")
        @NotNull(message = "currentChunk 不能为空")
        private Integer currentChunk;

        @Schema(description = "eTag 切片文件的eTag")
        @NotNull(message = "eTag 不能为空")
        private String eTag;
    }
}
