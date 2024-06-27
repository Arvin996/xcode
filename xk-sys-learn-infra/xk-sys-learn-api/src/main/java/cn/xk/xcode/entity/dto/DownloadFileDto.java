package cn.xk.xcode.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * @Author xuk
 * @Date 2024/6/27 15:30
 * @Version 1.0
 * @Description DownloadFileDto
 */
@Data
@Builder
@Schema(description = "下载文件的Dto")
public class DownloadFileDto
{
    private String fileId;
}
