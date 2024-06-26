package cn.xk.xcode.entity.dto;

import com.mybatisflex.annotation.Id;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/6/25 10:31
 * @Version 1.0
 * @Description UpdateTableInfoDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "更新查询数据库配置实体类")
public class UpdateTableInfoDto
{
    /**
     * 表所属数据库
     */
    @NotNull
    @Schema(description = "数据库id")
    private Integer databaseId;

    /**
     * 表名
     */
    @NotNull
    @Schema(description = "表名")
    private String tableName;

    /**
     * 表名前缀
     */
    @Schema(description = "表名前缀")
    private String tablePre;

    /**
     * 实体类后缀
     */
    @Schema(description = "实体类后缀")
    private String entitySuff;
}
