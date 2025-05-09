package cn.xk.xcode.entity.dto.channel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2025/3/10 9:32
 * @Version 1.0.0
 * @Description DelMessageChannelDto
 **/
@Data
@NoArgsConstructor
@Schema(description = "删除渠道dto")
public class DelMessageChannelDto {

    @Schema(description = "id")
    @NotNull(message = "channel id cannot be null")
    private Integer id;
}
