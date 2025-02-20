package cn.xk.xcode.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author xuk
 * @Date 2025/2/19 10:51
 * @Version 1.0.0
 * @Description InitUploadChunkFileRespDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitUploadChunkFileRespDto implements Serializable {

    private String fileUploadId;
    private String objectName;
}
