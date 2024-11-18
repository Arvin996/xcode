package cn.xk.xcode.entity.po;

import cn.xk.xcode.entity.DataLongObjectBaseEntity;
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
@Table("takeout_permission")
public class TakeoutPermissionPo extends DataLongObjectBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 资源名
     */
    private String code;

    /**
     * 是否删除 0否 1是
     */
    @Column(isLogicDelete = true)
    private Integer isDeleted;

}
