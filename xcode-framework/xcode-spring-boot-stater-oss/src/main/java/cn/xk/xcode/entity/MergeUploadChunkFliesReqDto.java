package cn.xk.xcode.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author xuk
 * @Date 2025/2/19 13:27
 * @Version 1.0.0
 * @Description MergeUploadChunkFliesReqDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MergeUploadChunkFliesReqDto implements Serializable {

    private String uploadId;
    private String objectName;
    private String bucketName;
    private List<OssMergeChunkFileInfo> chunkFileInfos;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OssMergeChunkFileInfo{
        private Integer currentChunk;
        private String eTag;
    }
}
