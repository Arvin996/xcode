package cn.xk.xcode.entity.dto;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @Author xuk
 * @Date 2024/6/25 09:47
 * @Version 1.0
 * @Description AddDatabaseConnInfoPoDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "新增数据库连接信息实体类")
public class AddDatabaseConnInfoPoDto
{

    /**
     * 数据库名
     */
    @Schema(description = "新增数据库名")
    @NotBlank(message = "数据库名不能为空")
    private String databaseName;

    /**
     * 数据库连接地址 ip:port 只支持mysql
     */
    @NotNull(message = "数据库连接地址不能为空")
    @Schema(description = "新增数据库连接地址")
    private String url;

    /**
     * 数据库用户名
     */
    @NotBlank(message = "数据库用户名不能为空")
    @Schema(description = "新增数据库用户名")
    private String userName;

    /**
     * 数据库密码
     */
    @NotBlank(message = "数据库密码不能为空")
    @Schema(description = "新增数据库密码")
    private String password;
}
