package cn.xk.xcode.entity.discard.task;

import cn.xk.xcode.enums.*;
import cn.xk.xcode.validation.InStrEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @Author xuk
 * @Date 2025/3/10 16:11
 * @Version 1.0.0
 * @Description MessageTask
 **/
@Data
@Schema(description = "MessageTask")
public class MessageTask {

    private Long id;

    /**
     * 接入商id
     */
    @NotNull
    private Integer clientId;

    /**
     * 渠道账号id
     */
    private Integer accountId;

    /**
     * 消息屏蔽类型 00不屏蔽 10 夜间屏蔽 20时间区间屏蔽
     */
    @NotBlank
    @InStrEnum(ShieldType.class)
    private String shieldType;

    /**
     * 开始屏蔽时间
     */
    private String shieldStartTime;

    /**
     * 结束屏蔽时间
     */
    private String shieldEndTime;

    /**
     * 消息类型 delay 延时发送 几点发送 now立即发送 corn 定时发送
     */
    @NotBlank
    @InStrEnum(MessageSendType.class)
    private String msgType;

    /**
     * 发送类型 如短信 微信公众号等
     */
    @NotBlank
    @InStrEnum(ChannelTypeEnum.class)
    private String sendType;

    /**
     * 定时任务corn
     */
    private String msgCorn;

    /**
     * xxl中的任务id
     */
    private Long taskCornId;

    /**
     * 执行时间 针对延时发送而言
     */
    private LocalDateTime scheduleTime;

    /**
     * 消息内容类型 plain 文本消息 立即发送 template 模板消息
     */
    @NotBlank
    @InStrEnum(MessageContentType.class)
    private String msgContentType;

    /**
     * 模板id
     */
    private Integer templateId;

    /**
     * 三方模板id
     */
    private String thirdTemplateId;


    /**
     * 消息内容
     */
    private String messageContent;

    /**
     * 模板参数值
     */
    private String contentValueParams;

    /**
     * 接收人类型 00 文本用逗号隔开 10 csv文件
     */
    @NotBlank
    @InStrEnum(ReceiverTypeEnum.class)
    private String receiverType;

    /**
     * 接收人
     */
    private String receivers;

    private Set<String> receiverSet;

    private Integer successCount;

    /**
     * 点击模板卡片后的跳转页面。支持带参数 微信小程序字段为page 公众号为pagepath
     */
    private String page;
}
