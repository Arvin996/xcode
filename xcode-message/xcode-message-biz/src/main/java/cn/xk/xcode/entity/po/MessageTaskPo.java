package cn.xk.xcode.entity.po;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  实体类。
 *
 * @author xuk
 * @since 2025-03-10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("message_task")
public class MessageTaskPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 渠道账号id
     */
    private Integer accountId;

    /**
     * 接入商id
     */
    private Integer clientId;

    /**
     * 消息屏蔽类型 00不屏蔽 10 夜间屏蔽 20时间区间屏蔽
     */
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
    private String msgType;

    /**
     * 发送类型 如短信 微信公众号等
     */
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
    private String msgContentType;

    /**
     * 模板id
     */
    private Integer templateId;

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
    private String receiverType;

    /**
     * 接收人 json格式
     */
    private String receivers;

    /**
     * 00 待发送 10 部分发送成功 20 发送失败 30 全部发送成功 40 取消
     */
    private String status;

}
