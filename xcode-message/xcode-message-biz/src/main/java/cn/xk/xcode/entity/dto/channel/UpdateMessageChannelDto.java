package cn.xk.xcode.entity.dto.channel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2025/3/10 9:40
 * @Version 1.0.0
 * @Description UpdateMessageChannelDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Schema(description = "update message channel dto")
public class UpdateMessageChannelDto extends AddMessageChannelDto{

    @NotBlank(message = "channel id cannot be blank")
    @Schema(description = "channel id")
    private Integer id;
}
