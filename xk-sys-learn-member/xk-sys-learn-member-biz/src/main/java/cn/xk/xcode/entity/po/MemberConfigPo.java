package cn.xk.xcode.entity.po;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
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
@Table("member_config")
public class MemberConfigPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 积分抵用开关0 开启 1关闭
     */
    private String pointDeductEnable;

    /**
     * 1积分抵扣多少钱 单位：分
     */
    private Integer pointDeductUnit;

    /**
     * 最大使用积分数
     */
    private Integer maxPointDeduct;

    /**
     * 一元赠送的积分数量
     */
    private Integer givenPointAdd;

}
