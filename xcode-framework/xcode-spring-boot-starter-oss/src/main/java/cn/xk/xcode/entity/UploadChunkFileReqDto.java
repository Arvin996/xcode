package cn.xk.xcode.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author xuk
 * @Date 2025/2/19 11:21
 * @Version 1.0.0
 * @Description UploadChunkFileReqDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UploadChunkFileReqDto", description = "UploadChunkFileReqDto 上传切片文件")
@Builder
public class UploadChunkFileReqDto implements Serializable {

    @Schema(description = "uploadId 切片上传初始化时当前文件的唯一id")
    @NotBlank(message = "uploadId 不能为空")
    private String uploadId;

    @Schema(description = "currentChunk 当前切片id")
    @NotNull(message = "当前切片编号不能为空")
    private Integer currentChunk;

    @Schema(description = "objectName 文件上传路径")
    @NotNull(message = "objectName 不能为空")
    private String objectName;

    @Schema(description = "bucketName 不传就是配置的默认桶")
    private String bucketName;

    @Schema(description = "file 切片文件")
    @NotNull(message = "文件不能为空")
    private MultipartFile file;
}
