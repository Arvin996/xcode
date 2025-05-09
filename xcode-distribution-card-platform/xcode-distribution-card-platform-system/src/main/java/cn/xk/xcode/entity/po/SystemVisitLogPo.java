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
 * @since 2025-05-09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("system_visit_log")
public class SystemVisitLogPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 访问ID
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 日志类型 登录 登出 踢下线等
     */
    private String logType;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 操作结果
     */
    private Integer result;

    /**
     * 用户 IP
     */
    private String userIp;

    /**
     * 浏览器 UA
     */
    private String userAgent;

    /**
     * 操作时间
     */
    private LocalDateTime createTime;

}
