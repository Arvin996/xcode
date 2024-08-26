package cn.xk.xcode.entity.dto;

import cn.xk.xcode.enums.MinioBucketType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/6/27 15:15
 * @Version 1.0
 * @Description UploadFileDto
 */
@Data
@Schema(description = "上传文件dto")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileDto {

    @NotNull(message = "文件内容不能为空")
    @Schema(description = "文件内容")
    private MultipartFile file;

    @NotNull(message = "文件桶不能为空")
    @Schema(description = "文件桶")
    private MinioBucketType bucketType;

    @Schema(description = "如果文件时.mp3,是否需要将mp3文件转为MP4格式")
    private boolean isNeedConvertToMp4 = false;
}
