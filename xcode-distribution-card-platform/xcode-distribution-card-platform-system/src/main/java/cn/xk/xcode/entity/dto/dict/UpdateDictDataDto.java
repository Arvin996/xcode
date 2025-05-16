package cn.xk.xcode.entity.dto.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2025/5/12 16:19
 * @Version 1.0.0
 * @Description UpdateDictDataDto
 **/
@Data
@Schema(name = "UpdateDictDataDto", description = "DictData修改实体类")
public class UpdateDictDataDto {


    @Schema(description = "id")
    @NotNull(message = "id 不能为空")
    private Long id;


    /**
     * 字典排序
     */
    @NotNull(message = "字典排序不能为空")
    @Schema(description = "sort 字典排序")
    private Integer sort;

    /**
     * 字典标签
     */
    @Schema(description = "label 字典标签")
    @NotBlank(message = "字典标签不能为空")
    private String label;

    /**
     * 字典键值
     */
    @Schema(description = "value 字典键值")
    @NotBlank(message = "字典键值不能为空")
    private String value;

    /**
     * 状态（0正常 1停用）
     */
    @Schema(description = "status 状态（0正常 1停用）")
    @NotBlank(message = "status 状态 不能为空")
    private String status;

    /**
     * 颜色类型
     */
    @Schema(description = "color 颜色类型")
    private String color;

    /**
     * 备注
     */
    @Schema(description = "remark 备注")
    private String remark;
}
