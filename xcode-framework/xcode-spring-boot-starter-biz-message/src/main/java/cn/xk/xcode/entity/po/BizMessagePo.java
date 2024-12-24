package cn.xk.xcode.entity.po;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  实体类。
 *
 * @author lenovo
 * @since 2024-07-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("biz_message")
public class BizMessagePo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息id
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.flexId)
    private Long id;

    /**
     * 消息类型
     */
    private String bizType;

    /**
     * 消息主键
     */
    private String bizKey;

    /**
     * 消息状态 0待处理 1处理成功 2处理失败 3处理中
     */
    private String status;

    /**
     * 失败次数
     */
    private Integer failCount;

    /**
     * 最大失败次数
     */
    private Integer maxFailCount;

    /**
     * 错误信息
     */
    private String errMsg;

    /**
     * 创建用户
     */
    private String createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 完成时间
     */
    private LocalDateTime finishTime;

}
