package cn.xk.xcode.entity.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 实体类。
 *
 * @author Administrator
 * @since 2025-05-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("message_task")
public class MessageTaskPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 接入商id
     */
    private Integer clientId;

    /**
     * 渠道账号id
     */
    private Integer accountId;

    /**
     * 消息屏蔽类型 00不屏蔽 10 夜间屏蔽 20时间区间屏蔽
     */
    private String shieldType;

    /**
     * 开始屏蔽时间 0800
     */
    private String shieldStartTime;

    /**
     * 结束屏蔽时间 1700
     */
    private String shieldEndTime;

    /**
     * 消息类型 delay 延时发送 now立即发送 corn 定时发送
     */
    private String msgType;

    /**
     * 发送渠道 如短信 微信公众号等
     */
    private Integer channelId;

    /**
     * 如果为定时任务 则是定时任务corn
     */
    private String taskCorn;

    /**
     * xxl中的任务id
     */
    private Long taskCornId;

    /**
     * 执行时间 针对延时发送而言
     */
    private LocalDateTime scheduleTime;

    /**
     * 消息内容类型 plain文本 template 模板消息
     */
    private String msgContentType;

    /**
     * 模板id
     */
    private int templateId;

    /**
     * 消息内容
     */
    private String messageContent;

    /**
     * 模板参数值 json格式
     */
    private String contentValueParams;

    /**
     * 接收人类型 00透传直接发送 10 csv文件
     */
    private String receiverType;

    /**
     * 接收人list
     */
    private String receivers;

    /**
     * 00 待发送
     * 10 部分发送成功
     * 20 全部发送失败
     * 30 全部发送成功
     * 40 取消发送（延时任务）
     * 50 暂停发送（定时任务）
     */
    private String status;

    /**
     * 任务创建时间
     */
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    /**
     * 任务执行时间
     */
    private LocalDateTime triggerTime;

}
