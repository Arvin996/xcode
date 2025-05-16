package cn.xk.xcode.entity.dto.channel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2025/3/10 9:40
 * @Version 1.0.0
 * @Description UpdateMessageChannelDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Schema(name = "UpdateMessageChannelDto", description = "UpdateMessageChannelDto 更新渠道")
public class UpdateMessageChannelDto extends AddMessageChannelDto{

    @NotNull(message = "渠道id 不能为空")
    @Schema(description = "channel id")
    private Integer id;
}
