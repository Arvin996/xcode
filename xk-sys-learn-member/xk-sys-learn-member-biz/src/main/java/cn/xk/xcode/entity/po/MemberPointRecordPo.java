package cn.xk.xcode.entity.po;

import cn.xk.xcode.listener.BaseEntityChangeListener;
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
 * @author lenovo
 * @since 2024-08-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "member_point_record", onInsert = BaseEntityChangeListener.class)
public class MemberPointRecordPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 变更的业务类型
     */
    private Integer bizType;

    /**
     * 变更的业务id
     */
    private String bizId;

    /**
     * 变更业务类型的标题
     */
    private String title;

    /**
     * 变更业务描述
     */
    private String description;

    /**
     * 变更积分
     */
    private Integer changePoint;

    /**
     * 变更后的总积分
     */
    private Integer totalPoint;

    /**
     * 变更时间
     */
    private LocalDateTime createTime;

}
