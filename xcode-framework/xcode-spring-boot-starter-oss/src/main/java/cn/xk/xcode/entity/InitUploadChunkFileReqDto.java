package cn.xk.xcode.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @Author xuk
* @Date 2025/2/19 13:50
* @Version 1.0.0
* @Description InitUploadChunkFileReqDto
**/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitUploadChunkFileReqDto {

    private String bucketName;
    private String dirTag;
    private String filename;
}

