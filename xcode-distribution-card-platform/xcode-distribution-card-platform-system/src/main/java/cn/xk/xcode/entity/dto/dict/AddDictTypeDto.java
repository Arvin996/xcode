package cn.xk.xcode.entity.dto.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author xuk
 * @Date 2025/5/12 10:56
 * @Version 1.0.0
 * @Description AddDictDto
 **/
@Data
@Schema(name = "AddDictTypeDto", description = "DictType添加实体类")
public class AddDictTypeDto {

    /**
     * 字典类型名称
     */
    @Schema(description = "name 字典类型名称")
    private String name;

    /**
     * 字典类型码
     */
    @Schema(description = "type 字典类型码")
    private String type;

    /**
     * 备注
     */
    @Schema(description = "remark 备注")
    private String remark;
}
