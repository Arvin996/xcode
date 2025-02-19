package cn.xk.xcode.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @Author xuk
 * @Date 2025/2/19 11:21
 * @Version 1.0.0
 * @Description UploadChunkFileReqDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadChunkFileReqDto implements Serializable {

    private String uploadId;
    private Integer currentChunk;
    private String objectName;
    private String bucketName;
    private MultipartFile file;
}
