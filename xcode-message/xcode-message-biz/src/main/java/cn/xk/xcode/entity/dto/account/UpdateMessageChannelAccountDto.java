package cn.xk.xcode.entity.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2025/3/10 10:10
 * @Version 1.0.0
 * @Description UpdateMessageChannelAccountDto
 **/
@Data
@Schema(description = "UpdateMessageChannelAccountDto")
public class UpdateMessageChannelAccountDto {

    @Schema(description = "id")
    @NotNull(message = "id 不能为空")
    private Integer id;

    /**
     * 渠道账户名称
     */
    @Schema(description = "渠道账户名称")
    @NotBlank(message = "渠道账户名称不能为空")
    private String accountName;

    /**
     * 渠道code
     */
    @Schema(description = "渠道id")
    @NotBlank(message = "渠道id 不能为空")
    private String channelId;

    /**
     * 账号权重
     */
    @Schema(description = "账号权重")
    @NotNull(message = "账号权重不能为空")
    private Double weight;

    @Schema(description = "渠道参数配置详情")
    private Map<Integer, String> channelParamValueMap = new HashMap<>();
}
