package cn.xk.xcode.entity.po;

import cn.xk.xcode.core.entity.TransPo;
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
 * @since 2025-05-09
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("system_role")
public class SystemRolePo extends DataStringObjectBaseEntity implements Serializable, TransPo {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 角色权限名
     */
    private String code;

    /**
     * 系统角色名
     */
    private String name;

    /**
     * 是否删除 0未删除 1已删除
     */
    @Column(isLogicDelete = true)
    private String isDeleted;

}
