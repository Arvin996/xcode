package cn.xk.xcode.entity.dto.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2025/5/12 16:08
 * @Version 1.0.0
 * @Description UpdateDictTypeDto
 **/
@Data
@Schema(name = "UpdateDictTypeDto", description = "DictType修改实体类")
public class UpdateDictTypeDto {

    /**
     * 字典主键
     */
    @Schema(description = "id 字典主键")
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 字典类型名称
     */
    @Schema(description = "name 字典类型名称")
    @NotBlank(message = "字典类型名称不为空")
    private String name;

    /**
     * 字典类型码
     */
    @Schema(description = "type 字典类型码")
    @NotBlank(message = "字典类型码不为空")
    private String type;

    /**
     * 状态（0正常 1停用）
     */
    @Schema(description = "status 状态（0正常 1停用）")
    @NotBlank(message = "status 状态 不能为空")
    private String status;

    /**
     * 备注
     */
    @Schema(description = "remark 备注")
    private String remark;
}
