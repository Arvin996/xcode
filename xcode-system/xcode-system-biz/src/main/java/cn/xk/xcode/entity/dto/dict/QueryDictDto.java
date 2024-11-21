package cn.xk.xcode.entity.dto.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author xuk
 * @Date 2024/6/21 10:48
 * @Version 1.0
 * @Description QueryDictDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "字段查询实体类")
public class QueryDictDto
{
    @Schema(description = "字典code", example = "01")
    private String code;

    @Schema(description = "字典名称", example = "测试环境")
    private String name;

    @Schema(description = "父级id", example = "0")
    private String parId;
}
