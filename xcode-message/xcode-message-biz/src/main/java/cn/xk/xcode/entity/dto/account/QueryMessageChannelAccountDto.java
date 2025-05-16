package cn.xk.xcode.entity.dto.account;

import cn.xk.xcode.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author xuk
 * @Date 2025/5/15 17:28
 * @Version 1.0.0
 * @Description QueryMessageChannelAccountDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "QueryMessageChannelAccountDto", description = "查询消息渠道账户")
public class QueryMessageChannelAccountDto extends PageParam {

    /**
     * 渠道账户名称
     */
    @Schema(description = "渠道账户名称")
    private String accountName;

    /**
     * 渠道code
     */
    @Schema(description = "渠道code")
    private String channelCode;
}
