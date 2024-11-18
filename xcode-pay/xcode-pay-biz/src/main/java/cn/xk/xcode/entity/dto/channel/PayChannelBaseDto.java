package cn.xk.xcode.entity.dto.channel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/9/26 15:11
 * @Version 1.0.0
 * @Description PayChannelBaseDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "支付渠道base dto")
public class PayChannelBaseDto {

    @Schema(description = "支付渠道id")
    @NotNull(message = "支付渠道id不能为空")
    private Integer id;
}
