package cn.xk.xcode.entity.dto.channel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2025/3/10 9:14
 * @Version 1.0.0
 * @Description MessageChannelDto
 **/
@Data
@NoArgsConstructor
@Schema(description = "新增渠道dto")
public class AddMessageChannelDto {

    @Schema(description = "渠道码", example = "sms")
    @NotBlank(message = "channel code cannot be blank")
    private String code;

    /**
     * 渠道名称
     */
    @Schema(description = "渠道名称")
    @NotBlank(message = "channel name cannot be blank")
    private String name;

}
