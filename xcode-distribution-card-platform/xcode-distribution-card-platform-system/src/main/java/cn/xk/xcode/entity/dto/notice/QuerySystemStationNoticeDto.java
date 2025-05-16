package cn.xk.xcode.entity.dto.notice;

import cn.xk.xcode.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author xuk
 * @Date 2025/5/13 13:39
 * @Version 1.0.0
 * @Description QuerySystemStationNoticeDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "QuerySystemStationNoticeDto", description = "StationNotice查询实体类")
public class QuerySystemStationNoticeDto extends PageParam {

    /**
     * 消息title
     */
    @Schema(description = "title 消息title")
    private String title;

    /**
     * 接收人
     */
    @Schema(description = "toUser 接收人")
    private String toUser;

    /**
     * 通知类型 0公告 1通知
     */
    @Schema(description = "type 通知类型 0公告 1通知")
    private String type;

    /**
     * 0 未读 1已读
     */
    @Schema(description = "isRead 0 未读 1已读")
    private String isRead;
}
