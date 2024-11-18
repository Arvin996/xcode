package cn.xk.xcode.entity.po;

import cn.xk.xcode.listener.BaseStringEntityChangeListener;
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
@Table(value = "member_experience_record", onInsert = BaseStringEntityChangeListener.class)
public class MemberExperienceRecordPo implements Serializable {

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
     * 增加或者减少经验的业务类型
     */
    private Integer bizType;

    /**
     * 业务类型对应的业务id
     */
    private String bizId;

    /**
     * 业务类型对应的描述
     */
    private String description;

    /**
     * 业务类型对应的标题
     */
    private String title;

    /**
     * 变更经验
     */
    private Integer experience;

    /**
     * 变更后的总经验
     */
    private Integer totalExperience;

    /**
     * 记录时间
     */
    private LocalDateTime createTime;

}
