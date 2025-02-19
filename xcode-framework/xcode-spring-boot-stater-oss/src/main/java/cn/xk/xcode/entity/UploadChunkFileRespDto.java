package cn.xk.xcode.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author xuk
 * @Date 2025/2/19 11:15
 * @Version 1.0.0
 * @Description UploadChunkFileResp
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadChunkFileRespDto implements Serializable {

    // 切片上传初始化时当前文件的唯一id
    private String fileUploadId;

    // 当前切片id
    private Integer currentChunk;

    // 文件上传路径
    private String ObjectName;

    // 文件名
    private String fileName;

    private String eTag;
}
