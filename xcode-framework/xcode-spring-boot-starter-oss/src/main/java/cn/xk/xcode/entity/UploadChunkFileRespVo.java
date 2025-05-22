package cn.xk.xcode.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author xuk
 * @Date 2025/2/19 11:15
 * @Version 1.0.0
 * @Description UploadChunkFileRespVo
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UploadChunkFileRespVo", description = "UploadChunkFileRespVo 上传切片文件返回")
public class UploadChunkFileRespVo implements Serializable {

    // 切片上传初始化时当前文件的唯一id
    @Schema(description = "fileUploadId 切片上传初始化时当前文件的唯一id")
    private String fileUploadId;

    // 当前切片id
    @Schema(description = "currentChunk 当前切片id")
    private Integer currentChunk;

    // 文件上传路径
    @Schema(description = "objectName 文件上传路径")
    private String ObjectName;

    // 文件名
    @Schema(description = "fileName 文件名")
    private String fileName;

    @Schema(description = "eTag 文件eTag")
    private String eTag;
}
