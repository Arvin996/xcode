package cn.xk.xcode.entity.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "提交订单支付 dto")
public class PayOrderSubmitReqDto {

    @Schema(description = "支付单编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "支付单编号不能为空")
    private Long id;

    @Schema(description = "支付渠道", requiredMode = Schema.RequiredMode.REQUIRED, example = "wx_pub")
    @NotBlank(message = "支付渠道不能为空")
    private String channelCode;

    @Schema(description = "支付渠道的额外参数，例如说，微信公众号需要传递 openid 参数")
    private Map<String, String> channelExtras;

    @Schema(description = "展示模式", example = "url") // 参见 {@link PayDisplayModeEnum} 枚举。如果不传递，则每个支付渠道使用默认的方式
    private String displayMode;

    @Schema(description = "回跳地址")
    @URL(message = "回跳地址的格式必须是 URL")
    private String returnUrl;

}