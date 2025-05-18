package cn.xk.xcode.entity.vo.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xukai
 * @version 1.0
 * @date 2025/5/18 10:42
 * @description MessageTaskVo
 */
@Data
@Schema(name = "MessageTaskVo", description = "MessageTaskVo 消息任务vo")
public class MessageTaskVo {

    @Schema(description = "id")
    private Long id;

    /**
     * 接入商id
     */
    @Schema(description = "id")
    private Integer clientId;

    /**
     * 接入商名称
     */
    @Schema(description = "接入商名称")
    private String clientName;

    /**
     * 渠道账号id
     */
    @Schema(description = "id")
    private Integer accountId;

    /**
     * 账号名称
     */
    @Schema(description = "账号名称")
    private String accountName;

    /**
     * 消息屏蔽类型 00不屏蔽 10 夜间屏蔽 20时间区间屏蔽
     */
    @Schema(description = "消息屏蔽类型 00不屏蔽 10 夜间屏蔽 20时间区间屏蔽")
    private String shieldType;

    /**
     * 开始屏蔽时间 0800
     */
    @Schema(description = "开始屏蔽时间 0800")
    private String shieldStartTime;

    /**
     * 结束屏蔽时间 1700
     */
    @Schema(description = "结束屏蔽时间 1700")
    private String shieldEndTime;

    /**
     * 消息类型 delay 延时发送 now立即发送 corn 定时发送
     */
    @Schema(description = "消息类型 delay 延时发送 now立即发送 corn 定时发送")
    private String msgType;

    /**
     * 发送渠道 如短信 微信公众号等
     */
    @Schema(description = "发送渠道 如短信 微信公众号等")
    private Integer channelId;

    /**
     * 发送渠道编码
     */
    @Schema(description = "发送渠道编码")
    private String channelCode;

    /**
     * 发送渠道名称
     */
    @Schema(description = "发送渠道名称")
    private String channelName;

    /**
     * 如果为定时任务 则是定时任务corn
     */
    @Schema(description = "如果为定时任务 则是定时任务corn")
    private String taskCorn;

    /**
     * xxl中的任务id
     */
    @Schema(description = "xxl中的任务id")
    private Long taskCornId;

    /**
     * 执行时间 针对延时发送而言
     */
    @Schema(description = "执行时间 针对延时发送而言")
    private LocalDateTime scheduleTime;

    /**
     * 消息内容类型 plain文本 template 模板消息
     */
    @Schema(description = "消息内容类型 plain文本 template 模板消息")
    private String msgContentType;

    /**
     * 模板id
     */
    @Schema(description = "模板id")
    private Integer templateId;

    /**
     * 消息内容
     */
    @Schema(description = "消息内容")
    private String messageContent;

    /**
     * 模板参数值 json格式
     */
    @Schema(description = "模板参数值 json格式")
    private String contentValueParams;

    /**
     * 接收人类型 00透传直接发送 10 csv文件
     */
    @Schema(description = "接收人类型 00透传直接发送 10 csv文件")
    private String receiverType;

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
    private LocalDateTime createTime;

    /**
     * 任务执行时间
     */
    @Schema(description = "任务执行时间")
    private LocalDateTime triggerTime;
}
