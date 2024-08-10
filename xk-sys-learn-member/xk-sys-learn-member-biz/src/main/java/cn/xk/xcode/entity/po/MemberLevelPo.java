package cn.xk.xcode.entity.po;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;


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
@Table("member_level")
public class MemberLevelPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id
    private Integer id;

    /**
     * 等级名称
     */
    private String name;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 升级所需经验
     */
    private Integer experience;

    /**
     * 享受折扣
     */
    private Integer discountPercent;

    /**
     * 等级头像
     */
    private String levelIcon;

    /**
     * 等级背景图
     */
    private String levelBackground;

}
