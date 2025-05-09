package cn.xk.xcode.entity.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2025/3/10 10:01
 * @Version 1.0.0
 * @Description AddMessageAccountDto
 **/
@Data
@Schema(description = "add message account dto")
public class AddMessageChannelAccountDto {

    /**
     * 渠道账户名称
     */
    @Schema(description = "account name")
    @NotBlank(message = "account name cannot be blank")
    private String accountName;

    /**
     * 渠道code
     */
    @Schema(description = "channel code")
    @NotBlank(message = "channel code cannot be blank")
    private String channelCode;

    /**
     * 渠道配置 jsonz字符串 主要配置appid等一些必须的参数
     */
    @Schema(description = "channel config")
    @NotBlank(message = "channel config cannot be blank")
    private String channelConfig;
}
