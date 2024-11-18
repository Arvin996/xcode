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
@Table("takeout_dish")
public class TakeoutDishPo extends DataLongObjectBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.flexId)
    private Long id;

    /**
     * 菜品名称
     */
    private String name;

    /**
     * 菜品分类id
     */
    private Long categoryId;

    /**
     * 菜品价格
     */
    private BigDecimal price;

    /**
     * 菜品库存
     */
    private Integer stock;

    /**
     * 商品码
     */
    private String code;

    /**
     * 图片
     */
    private String image;

    /**
     * 描述信息
     */
    private String description;

    /**
     *  0 起售 1 停售
     */
    private Integer status;

    /**
     * 顺序
     */
    private Integer sort;

    /**
     * 是否删除
     */
    @Column(isLogicDelete = true)
    private Integer isDeleted;

}
