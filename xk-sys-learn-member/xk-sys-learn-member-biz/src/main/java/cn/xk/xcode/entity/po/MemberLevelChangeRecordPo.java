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
@Table(value = "member_level_change_record", onInsert = BaseEntityChangeListener.class)
public class MemberLevelChangeRecordPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 变更后的等级id
     */
    private Integer levelId;

    /**
     * 变更后的等级id
     */
    private String levelName;

    /**
     * 变更后的用户总经验
     */
    private Integer totalExperience;

    /**
     * 变更经验
     */
    private Integer changeExperience;

    /**
     * 发生变更的业务标题
     */
    private String title;

    /**
     * 发送变更的业务描述
     */
    private String description;

    /**
     * 变更时间
     */
    private LocalDateTime createTime;

}
