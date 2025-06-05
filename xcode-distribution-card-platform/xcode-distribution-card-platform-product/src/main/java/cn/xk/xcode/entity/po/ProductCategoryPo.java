package cn.xk.xcode.entity.po;

import cn.xk.xcode.entity.DataStringObjectBaseEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;


import lombok.*;

/**
 *  实体类。
 *
 * @author xuk
 * @since 2025-05-30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("product_category")
public class ProductCategoryPo extends DataStringObjectBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 父分类
     */
    private Long parentId;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 排序字段
     */
    private Long sort;

    /**
     * 分类图片
     */
    private String pic;

    /**
     * 是否删除 0未删除 1已删除
     */
    @Column(isLogicDelete = true)
    private String isDeleted;

}
