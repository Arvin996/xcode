package cn.xk.xcode.entity.vo.notice;

import cn.xk.xcode.annotation.DictFieldTrans;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author xuk
 * @Date 2025/5/13 13:42
 * @Version 1.0.0
 * @Description SystemStationNoticeVo
 **/
@Data
@Schema(name = "SystemStationNoticeVo", description = "StationNoticeVo 系统通知查询返回")
public class SystemStationNoticeVo {

    /**
     * 自增id
     */
    @Schema(description = "id 自增id")
    private Long id;

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
     * 消息正文
     */
    @Schema(description = "message 消息正文")
    private String message;

    /**
     * 0 未读 1已读
     */
    @Schema(description = "isRead 0 未读 1已读")
    private String isRead;

    /**
     * 通知类型 0公告 1通知
     */
    @Schema(description = "type 通知类型 0公告 1通知")
    @DictFieldTrans(dictType = "system_notice_type", targetField = "typeName")
    private String type;

    /**
     * 通知类型名称
     */
    @Schema(description = "typeName 通知类型名称")
    private String typeName;

    /**
     * 发送时间
     */
    @Schema(description = "createTime 发送时间")
    private LocalDateTime createTime;
}
