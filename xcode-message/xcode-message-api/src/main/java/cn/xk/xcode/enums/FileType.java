package cn.xk.xcode.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 文件类型
 */
@Getter
@ToString
@AllArgsConstructor
public enum FileType {
    /**
     * 图片
     */
    IMAGE(10, "image"),
    /**
     * 语音
     */
    VOICE(20, "voice"),
    /**
     * 普通文件
     */
    COMMON_FILE(30, "file"),
    /**
     * 视频
     */
    VIDEO(40, "video"),
    ;
    private final Integer code;
    private final String description;

}
