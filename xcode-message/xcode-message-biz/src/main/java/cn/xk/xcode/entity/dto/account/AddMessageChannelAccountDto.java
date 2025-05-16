package cn.xk.xcode.entity.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2025/3/10 10:01
 * @Version 1.0.0
 * @Description AddMessageAccountDto
 **/
@Data
@Schema(name = "AddMessageChannelAccountDto", description = "新增消息渠道账户")
public class AddMessageChannelAccountDto {

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
    @NotNull(message = "渠道id 不能为空")
    private Integer channelId;

    /**
     * 账号权重
     */
    @Schema(description = "账号权重")
    private Double weight;

    @Schema(description = "渠道参数配置详情")
    private Map<Integer, String> channelParamValueMap = new HashMap<>();
}
