package cn.xk.xcode.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/6/25 09:53
 * @Version 1.0
 * @Description UpdateDatabaseConnInfoPoDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "修改查询数据库配置实体类")
public class UpdateDatabaseConnInfoPoDto
{

    @NotNull(message = "id不能为空")
    @Schema(description = "数据库配置id")
    private Integer id;

    /**
     * 数据库名
     */
    @Schema(description = "数据库名")
    private String databaseName;

    /**
     * todo ip和端口单独维护会更好一点
     * 数据库连接地址 ip:port 只支持mysql
     */
    @Schema(description = "数据库连接地址 ip:port 只支持mysql")
    private String url;

    /**
     * 数据库用户名
     */
    @Schema(description = "数据库用户名")
    private String userName;

    /**
     * 数据库密码
     */
    @Schema(description = "数据库密码")
    private String password;

}
