package cn.xk.xcode.entity.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author xuk
 * @Date 2024/10/30 14:47
 * @Version 1.0.0
 * @Description UpdateUserRoleDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "更新用户 dto")
public class UpdateUserRoleDto extends UserBaseDto{

    private Integer roleId;
    private Long UserId;
}
