package cn.xk.xcode.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author xuk
 * @Date 2024/6/27 15:15
 * @Version 1.0
 * @Description UploadFileDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "上传文件dto")
public class UploadFileDto
{
    @Schema(description = "文件内容")
    private byte[] fileContent;

    @Schema(description = "文件桶")
    private String bucket;

    @Schema(description = "如果文件时.mp3,是否需要将mp3文件转为MP4格式")
    boolean isMap3ConvertToMp4 = false;
}
