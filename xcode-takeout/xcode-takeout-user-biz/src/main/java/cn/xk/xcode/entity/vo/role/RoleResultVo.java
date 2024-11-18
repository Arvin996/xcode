package cn.xk.xcode.entity.vo.role;

import cn.xk.xcode.entity.DataLongObjectBaseEntity;
import lombok.*;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/10/29 17:01
 * @Version 1.0.0
 * @Description RoleResultVo
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResultVo extends DataLongObjectBaseEntity {

    private Integer id;

    /**
     * 角色名称
     */
    private String name;

    private List<String> permissionList;
}
