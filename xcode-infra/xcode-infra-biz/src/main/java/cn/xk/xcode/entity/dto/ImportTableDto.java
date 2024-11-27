package cn.xk.xcode.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2024/11/26 11:39
 * @Version 1.0.0
 * @Description ImportTableDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "导入表数据dto")
public class ImportTableDto extends BaseGenDto{

    @Schema(description = "表名称 用逗号隔开")
    @NotBlank(message = "表名称不能为空")
    private String tableNames;
}
