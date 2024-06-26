package cn.xk.xcode.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @Author xuk
 * @Date 2024/6/25 10:23
 * @Version 1.0
 * @Description BatchSaveTablesDto
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "一键批量导入表信息dto")
public class BatchSaveTablesDto extends UpdateDatabaseConnInfoPoDto
{

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
