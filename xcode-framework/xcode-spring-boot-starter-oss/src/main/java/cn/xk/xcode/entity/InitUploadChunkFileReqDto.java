package cn.xk.xcode.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
* @Author xuk
* @Date 2025/2/19 13:50
* @Version 1.0.0
* @Description InitUploadChunkFileReqDto
**/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "InitUploadChunkFileReqDto", description = "InitUploadChunkFileReqDto 初始化分段上传")
@Builder
public class InitUploadChunkFileReqDto {

    @Schema(description = "bucketName 不传就是配置的默认桶")
    private String bucketName;

    @NotBlank(message = "文件名不能为空")
    @Schema(description = "filename 文件名")
    private String filename;

    @Schema(description = "文件类型")
    private String contentType;
}

