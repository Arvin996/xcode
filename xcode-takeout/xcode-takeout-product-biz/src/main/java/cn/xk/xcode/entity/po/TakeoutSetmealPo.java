package cn.xk.xcode.entity.po;

import cn.xk.xcode.entity.DataLongObjectBaseEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.*;

/**
 *  实体类。
 *
 * @author Administrator
 * @since 2024-10-31
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("takeout_setmeal")
public class TakeoutSetmealPo extends DataLongObjectBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.flexId)
    private Long id;

    /**
     * 菜品分类id
     */
    private Long categoryId;

    /**
     * 套餐名称
     */
    private String name;

    /**
     * 套餐价格
     */
    private BigDecimal price;

    /**
     * 套餐库存
     */
    private Integer stock;

    /**
     * 状态 0:启用 1:停用
     */
    private Integer status;

    /**
     * 编码
     */
    private String code;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 图片
     */
    private String image;

    /**
     * 是否删除
     */
    @Column(isLogicDelete = true)
    private Integer isDeleted;

}
