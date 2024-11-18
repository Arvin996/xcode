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
@Table("member_address")
public class MemberAddressPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增
     */
    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 收件人姓名
     */
    private String name;

    /**
     * 收件人手机号
     */
    private String phone;

    /**
     * 地区id
     */
    private Integer areaId;

    /**
     * 详细的收件地址 如楼层+门牌号
     */
    private String detailAddress;

    /**
     * 是否是默认地址
     */
    private String defaultAddress;

}
