package cn.xk.xcode.entity.dto.channel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2024/9/26 14:54
 * @Version 1.0.0
 * @Description AddPayChannelDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "新增支付渠道 dto")
public class AddPayChannelDto {

    /**
     * 支付渠道编码
     */
    @NotBlank(message = "支付渠道编码不能为空")
    @Schema(description = "支付渠道编码")
    private String channelCode;

    /**
     * 渠道费率，单位：百分比
     */
    @NotBlank(message = "渠道费率不能为空")
    @Schema(description = "渠道费率，单位：百分比", example = "0.00")
    private String feeRate;

    /**
     * 渠道备注
     */
    @Schema(description = "渠道备注")
    @NotBlank(message = "渠道备注不能为空")
    private String remark;
}
