package cn.xk.xcode.entity.po;

import com.mybatisflex.annotation.Column;
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
 * @since 2025-05-09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("system_operate_log")
public class SystemOperateLogPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 调用的api详情
     */
    private Integer apiId;

    /**
     * 操作用户
     */
    private String username;

    /**
     * 操作结果 0成功 1失败
     */
    private String state;

    /**
     * 失败原因
     */
    private String failMsg;

    /**
     * 操作时间
     */
    @Column(onInsertValue = "now()")
    private LocalDateTime operateTime;

}
