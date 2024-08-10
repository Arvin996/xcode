package cn.xk.xcode.entity.po;

import com.mybatisflex.annotation.Id;
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
@Table("member_sign")
public class MemberSignPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 签到第几天 星期一到星期天1-7
     */
    @Id
    private Integer day;

    /**
     * 奖励积分
     */
    private Integer point;

    /**
     * 奖励经验
     */
    private Integer experience;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
