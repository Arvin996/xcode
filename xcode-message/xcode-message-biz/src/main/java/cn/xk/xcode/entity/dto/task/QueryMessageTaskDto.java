package cn.xk.xcode.entity.dto.task;

import cn.xk.xcode.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author xukai
 * @version 1.0
 * @date 2025/5/18 10:37
 * @description QueryMessageTaskDto
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "QueryMessageTaskDto", description = "QueryMessageTaskDto 查询任务dto")
public class QueryMessageTaskDto extends PageParam {

    /**
     * 消息类型 delay 延时发送 now立即发送 corn 定时发送
     */
    @Schema(description = "消息类型 delay 延时发送 now立即发送 corn 定时发送")
    private String msgType;

    /**
     * 发送渠道 如短信 微信公众号等
     */
    @Schema(description = "发送渠道编码 如短信 微信公众号等")
    private String channelCode;

    /**
     * 00 待发送
     * 10 部分发送成功
     * 20 全部发送失败
     * 30 全部发送成功
     * 40 取消发送（延时任务）
     * 50 暂停发送（定时任务）
     */
    @Schema(description = "00 待发送 10 部分发送成功 20 全部发送失败 30 全部发送成功 40 取消发送（延时任务） 50 暂停发送（定时任务）")
    private String status;

    /**
     * 任务创建时间
     */
    @Schema(description = "任务创建时间")
    private LocalDateTime startTime;

    /**
     * 任务结束时间
     */
    @Schema(description = "任务结束时间")
    private LocalDateTime endTime;
}
