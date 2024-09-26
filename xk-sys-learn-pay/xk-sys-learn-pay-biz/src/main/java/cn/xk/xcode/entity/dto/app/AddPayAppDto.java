package cn.xk.xcode.entity.dto.app;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2024/9/26 14:12
 * @Version 1.0.0
 * @Description AddPayAppDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "新增支付应用 dto")
public class AddPayAppDto {

    /**
     * 应用编号
     */
    @NotBlank(message = "应用编号不能为空")
    @Schema(description = "应用编号")
    private String appCode;

    /**
     * 应用名称
     */
    @NotBlank(message = "应用名称不能为空")
    @Schema(description = "应用名称")
    private String appName;

    /**
     * 应用备注
     */
    @Schema(description = "应用备注")
    private String remark;

    /**
     * 支付结果的回调地址
     */
    @URL(message = "支付结果的回调地址格式不正确")
    @Schema(description = "支付结果的回调地址")
    private String orderNotifyUrl;

    /**
     * 退款结果的回调地址
     */
    @URL(message = "退款结果的回调地址格式不正确")
    @Schema(description = "退款结果的回调地址")
    private String refundNotifyUrl;

}
