package cn.xk.xcode.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author xuk
 * @Date 2024/6/28 09:58
 * @Version 1.0
 * @Description FileResultVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "文件上传返回视图")
public class FileResultVo
{
    @Schema(description = "文件id")
    private String fileId;

    @Schema(description = "文件访问路径, 便于直接访问文件")
    private String filePath;
}
