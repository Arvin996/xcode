package cn.xk.xcode.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @Author xuk
 * @Date 2025/2/18 13:42
 * @Version 1.0.0
 * @Description UploadResult
 **/
@Data
@Builder
public class UploadResult {

    // URL
    private String url;

    // 文件名
    private String filename;

    // 对象名称 (带路径)
    private String objectName;

    // 标记
    private String eTag;

    // 标记
    private String dirTag;

    // 文件类型
    private String contextType;

    // 文件大小
    private Long size;
}
