package cn.xk.xcode.entity.dto.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2025/5/12 11:08
 * @Version 1.0.0
 * @Description AddDcitDataDto
 **/
@Data
@Schema(name = "AddDictDataDto", description = "DictData添加实体类")
public class AddDictDataDto {

    /**
     * 字典排序
     */
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
     * 字典类型
     */
    @Schema(description = "dictType 字典类型")
    @NotBlank(message = "字典类型不能为空")
    private String dictType;

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
