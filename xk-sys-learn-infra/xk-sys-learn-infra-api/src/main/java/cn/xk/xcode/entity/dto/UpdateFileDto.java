package cn.xk.xcode.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/6/27 15:30
 * @Version 1.0
 * @Description DownloadFileDto
 */
@Data
@Schema(description = "更新文件的Dto")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFileDto
{
    @NotBlank(message = "文件Id不能为空")
    @Schema(description = "文件id")
    private String fileId;

    @Schema(description = "文件内容")
    private MultipartFile file;
}
