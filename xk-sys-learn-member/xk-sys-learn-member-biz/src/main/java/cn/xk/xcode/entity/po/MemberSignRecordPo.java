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
@Table(value = "member_sign_record", onInsert = BaseEntityChangeListener.class)
public class MemberSignRecordPo implements Serializable {

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
     * 第几天签到
     */
    private Integer day;

    /**
     * 签到获得的积分
     */
    private Integer point;

    /**
     * 签到获得的经验
     */
    private Integer experience;

    /**
     * 签到时间
     */
    private LocalDateTime createTime;

}
