package cn.xk.xcode.entity.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/10/30 14:52
 * @Version 1.0.0
 * @Description UpdateUserStatusDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "新增指定角色用户dto 管理员接口")
public class UpdateUserStatusDto extends UserBaseDto{

    @Schema(description = "用户状态 0正常1禁用")
    @NotNull(message = "用户状态不能为空")
    private Integer status;
}
