package cn.xk.xcode.entity.po;

import cn.xk.xcode.entity.DataLongObjectBaseEntity;
import cn.xk.xcode.typehandler.SexTypeHandler;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;


import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.*;

/**
 *  实体类。
 *
 * @author Administrator
 * @since 2024-10-29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("takeout_address")
public class TakeoutAddressPo extends DataLongObjectBaseEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.flexId)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 收货人
     */
    private String consignee;

    /**
     * 性别 0 男 1 女
     */
    @Column(typeHandler = SexTypeHandler.class)
    private String sex;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 省级区划编号
     */
    private String provinceCode;

    /**
     * 省级名称
     */
    private String provinceName;

    /**
     * 市级区划编号
     */
    private String cityCode;

    /**
     * 市级名称
     */
    private String cityName;

    /**
     * 区级区划编号
     */
    private String districtCode;

    /**
     * 区级名称
     */
    private String districtName;

    /**
     * 详细地址
     */
    private String detail;

    /**
     * 标签
     */
    private String label;

    /**
     * 默认 0 否 1是
     */
    private Integer isDefault;

    /**
     * 是否删除
     */
    @Column(isLogicDelete = true)
    private Integer isDeleted;

}
