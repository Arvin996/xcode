package cn.xk.xcode.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author xuk
 * @Date 2024/11/20 16:15
 * @Version 1.0.0
 * @Description SystemRoleResultVo
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(defaultValue = "角色返回Vo")
public class SystemRoleResultVo {

    /**
     * 自增id
     */
    @Schema(description = "角色id")
    private Integer id;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    private String name;
}
