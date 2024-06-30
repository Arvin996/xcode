package cn.xk.xcode.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/6/25 10:53
 * @Version 1.0
 * @Description GenerateCodeDto
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "代码生成")
public class GenerateCodeDto extends UpdateDatabaseConnInfoPoDto
{
    @NotNull
    @Schema(description = "表名")
    private String tableName;

    @NotNull
    @Schema(description = "生成类型")
    private String code;

    // 这里注意指定的包名 就不能指定路径了哦
    @Schema(description = "生成的包名")
    private String packageName;

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
