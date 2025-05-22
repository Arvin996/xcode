package cn.xk.xcode.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2025/2/19 14:04
 * @Version 1.0.0
 * @Description CheckChunkFileExistsReqDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "CheckChunkFileExistsReqDto", description = "CheckChunkFileExistsReqDto 检查切片文件是否存在")
@Builder
public class CheckChunkFileExistsReqDto {

    @Schema(description = "uploadId 切片上传初始化时当前文件的唯一id")
    @NotBlank(message = "uploadId 不能为空")
    private String uploadId;

    @Schema(description = "currentChunk 当前切片id")
    @NotNull(message = "currentChunk 不能为空")
    private Integer currentChunk;

    @Schema(description = "objectName 文件上传路径")
    @NotBlank(message = "objectName 不能为空")
    private String objectName;

    @Schema(description = "bucketName 不传就是配置的默认桶")
    private String bucketName;
}
