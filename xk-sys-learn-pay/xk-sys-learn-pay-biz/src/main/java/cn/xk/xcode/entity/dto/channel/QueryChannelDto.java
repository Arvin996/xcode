package cn.xk.xcode.entity.dto.channel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author xuk
 * @Date 2024/9/26 15:28
 * @Version 1.0.0
 * @Description QueryChannelDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "查询支付渠道 dto")
public class QueryChannelDto {

    /**
     * 支付渠道编码
     */
    @Schema(description = "支付渠道编码")
    private String channelCode;
}
