package cn.xk.xcode.entity.po;

import cn.xk.xcode.entity.DataStringObjectBaseEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;


import lombok.*;

/**
 *  实体类。
 *
 * @author xuk
 * @since 2025-05-09
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("system_dict_type")
public class SystemDictTypePo extends DataStringObjectBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典主键
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 字典类型名称
     */
    private String name;

    /**
     * 字典类型码
     */
    private String type;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否删除 0未删除 1已删除
     */
    @Column(isLogicDelete = true)
    private String isDeleted;

}
