package cn.xk.xcode.entity.dto.notice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2025/5/13 13:33
 * @Version 1.0.0
 * @Description PublishSystemStationNoticeDto
 **/
@Data
@Schema(name = "PublishSystemStationNoticeDto", description = "系统通知发布dto")
public class PublishSystemStationNoticeDto {

    /**
     * 消息title
     */
    @Schema(description = "title 消息title")
    @NotBlank(message = "title不能为空")
    private String title;

    /**
     * 接收人
     */
    @Schema(description = "toUser 接收人 通知类1有值具体通知的人 公告类型0为空 表示要发公告给所有用户")
    private String toUser;

    /**
     * 消息正文
     */
    @Schema(description = "message 消息正文")
    @NotBlank(message = "message不能为空")
    private String message;

    /**
     * 通知类型 0公告 1通知
     */
    @Schema(description = "type 通知类型 0公告 1通知")
    @NotBlank(message = "type不能为空")
    private String type;
}
