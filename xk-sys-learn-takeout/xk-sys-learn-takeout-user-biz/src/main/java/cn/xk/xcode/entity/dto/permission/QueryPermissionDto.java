package cn.xk.xcode.entity.dto.permission;

import cn.xk.xcode.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author xuk
 * @Date 2024/10/30 9:19
 * @Version 1.0.0
 * @Description QueryPermissionDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "绑定角色权限dto")
public class QueryPermissionDto extends PageParam {

    @Schema(description = "资源名")
    private String code;
}
