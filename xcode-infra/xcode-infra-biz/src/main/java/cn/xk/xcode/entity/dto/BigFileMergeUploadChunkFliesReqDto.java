package cn.xk.xcode.entity.dto;

import cn.xk.xcode.entity.MergeUploadChunkFliesReqDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author xuk
 * @Date 2025/5/22 13:50
 * @Version 1.0.0
 * @Description BigFileMergeUploadChunkFliesReqDto
 **/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "文件合并dto")
@Data
public class BigFileMergeUploadChunkFliesReqDto extends MergeUploadChunkFliesReqDto {

    @Schema(description = "username")
    private String username;
}
