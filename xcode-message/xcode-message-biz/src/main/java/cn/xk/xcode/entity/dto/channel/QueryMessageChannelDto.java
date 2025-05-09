package cn.xk.xcode.entity.dto.channel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author xuk
 * @Date 2025/3/10 9:48
 * @Version 1.0.0
 * @Description QueryMessageChannelDto
 **/
@Data
@Schema(description = "query message channel dto")
public class QueryMessageChannelDto {

    @Schema(description = "channel id")
    private Integer id;

    @Schema(description = "channel code")
    private String code;

    @Schema(description = "channel name")
    private String name;
}
