package cn.xk.xcode.entity.vo.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "订单提交 vo")
public class PayOrderSubmitRespVO {

    @Schema(description = "支付状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "10") // 参见 PayOrderStatusEnum 枚举
    private Integer status;

    @Schema(description = "展示模式", requiredMode = Schema.RequiredMode.REQUIRED, example = "url") // 参见 PayDisplayModeEnum 枚举
    private String displayMode;

    @Schema(description = "展示内容", requiredMode = Schema.RequiredMode.REQUIRED)
    private String displayContent;

}