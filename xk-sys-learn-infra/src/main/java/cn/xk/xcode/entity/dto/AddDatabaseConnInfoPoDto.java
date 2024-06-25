package cn.xk.xcode.entity.dto;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

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
    @NotNull
    private String databaseName;

    /**
     * 数据库连接地址 ip:port 只支持mysql
     */
    @NotNull
    private String url;

    /**
     * 数据库用户名
     */
    @NotNull
    private String userName;

    /**
     * 数据库密码
     */
    @NotNull
    private String password;
}
