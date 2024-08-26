package cn.xk.xcode.entity.dto.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/6/21 11:17
 * @Version 1.0
 * @Description UpdateDictDto
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Schema(description = "字典更新实体类")
public class UpdateDictDto
{
    @Schema(description = "字典code", example = "01")
    @NotNull(message = "字典code不能为空")
    private String code;

    @Schema(description = "字典名称", example = "测试环境")
    @NotBlank(message = "字典name不能为空")
    private String name;

    @Schema(description = "字典父id", example = "0")
    @NotNull(message = "字典parId不能为空")
    private String parId;

    @Schema(description = "字典备注", example = "测试环境")
    String note;

    @Schema(description = "字典排序", example = "1")
    String pad;
}
