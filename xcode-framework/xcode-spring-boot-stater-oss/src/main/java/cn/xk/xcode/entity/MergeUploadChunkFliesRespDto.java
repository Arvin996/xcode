package cn.xk.xcode.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author xuk
 * @Date 2025/2/19 13:44
 * @Version 1.0.0
 * @Description MergeUploadChunkFliesRespDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MergeUploadChunkFliesRespDto {

    private String url;
    private String eTag;
}
