package cn.xk.xcode.entity.dto.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2025/5/16 10:20
 * @Version 1.0.0
 * @Description AddMessageChannelParamDto
 **/
@Data
@Schema(name = "AddMessageChannelParamDto", description = "AddMessageChannelParamDto 新增渠道参数")
public class AddMessageChannelParamDto {

    /**
     * 渠道id
     */
    @Schema(description = "渠道id")
    @NotNull(message = "渠道id不能为空")
    private Integer channelId;

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
