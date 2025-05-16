package cn.xk.xcode.entity.dto.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2025/5/16 10:57
 * @Version 1.0.0
 * @Description UpdateMessageChannelParamDto
 **/
@Data
@Schema(name = "UpdateMessageChannelParamDto", description = "UpdateMessageChannelParamDto 修改渠道参数")
public class UpdateMessageChannelParamDto {

    @Schema(description = "id")
    private Integer id;

    /**
     * 参数名称
     */
    @Schema(description = "参数名称")
    @NotBlank(message = "参数名称不能为空")
    private String name;

    /**
     * 是否必须 0否 1是
     */
    @Schema(description = "是否必须 0否 1是")
    @NotBlank(message = "是否必须不能为空")
    private String required;

    /**
     * 参数描述
     */
    @Schema(description = "参数描述")
    private String desc;
}
