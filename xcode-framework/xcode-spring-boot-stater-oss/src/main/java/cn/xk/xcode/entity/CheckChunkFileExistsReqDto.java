package cn.xk.xcode.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author xuk
 * @Date 2025/2/19 14:04
 * @Version 1.0.0
 * @Description CheckChunkFileExistsReqDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckChunkFileExistsReqDto {

    private String uploadId;
    private Integer chunkName;
    private String objectName;
    private String bucketName;
    private String eTag;
}
