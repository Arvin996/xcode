package cn.xk.xcode.entity.dto.channel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2024/9/26 15:17
 * @Version 1.0.0
 * @Description UpdatePayChannelDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "更新支付渠道 dto")
public class UpdatePayChannelDto extends PayChannelBaseDto{

    /**
     * 支付渠道编码
     */
    @NotBlank(message = "支付渠道编码不能为空")
    @Schema(description = "支付渠道编码")
    private String channelCode;

    /**
     * 渠道状态 0 可用 1不可用
     */
    @NotBlank(message = "渠道状态不能为空")
    @Schema(description = "渠道状态 0 可用 1不可用")
    private String status;

    /**
     * 渠道费率，单位：百分比
     */
    @NotBlank(message = "渠道费率不能为空")
    @Schema(description = "渠道费率，单位：百分比")
    private String feeRate;

    /**
     * 渠道备注
     */
    @Schema(description = "渠道备注")
    private String remark;
}
