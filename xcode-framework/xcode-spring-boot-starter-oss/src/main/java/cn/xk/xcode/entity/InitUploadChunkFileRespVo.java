package cn.xk.xcode.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author xuk
 * @Date 2025/2/19 10:51
 * @Version 1.0.0
 * @Description InitUploadChunkFileRespVo
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "InitUploadChunkFileRespVo", description = "InitUploadChunkFileRespVo 初始化分段上传返回")
public class InitUploadChunkFileRespVo implements Serializable {

    @Schema(description = "fileUploadId 切片上传初始化时当前文件的唯一id")
    private String fileUploadId;

    @Schema(description = "objectName 文件上传路径")
    private String objectName;
}
