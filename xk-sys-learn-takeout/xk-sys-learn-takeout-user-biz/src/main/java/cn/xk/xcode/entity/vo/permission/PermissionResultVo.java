package cn.xk.xcode.entity.vo.permission;

import cn.xk.xcode.entity.DataLongObjectBaseEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import lombok.*;

/**
 * @Author xuk
 * @Date 2024/10/30 9:20
 * @Version 1.0.0
 * @Description PermissionResultVo
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionResultVo extends DataLongObjectBaseEntity {

    /**
     * 自增id
     */
    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 资源名
     */
    private String code;
}
