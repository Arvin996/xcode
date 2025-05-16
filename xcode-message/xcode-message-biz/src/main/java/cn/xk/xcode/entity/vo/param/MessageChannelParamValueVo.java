package cn.xk.xcode.entity.vo.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author xuk
 * @Date 2025/5/16 8:22
 * @Version 1.0.0
 * @Description MessageChannelParamValueVo
 **/
@Data
@Schema(name = "MessageChannelParamValueVo", description = "MessageChannelParamValueVo 消息渠道参数值返回vo")
public class MessageChannelParamValueVo {

    /**
     * 自增id
     */
    @Schema(description = "自增id")
    private Integer id;

    /**
     * 账号id
     */
    @Schema(description = "账号id")
    private Integer accountId;

    /**
     * 渠道参数id
     */
    @Schema(description = "渠道参数id")
    private Integer channelParamId;

    /**
     * 参数值
     */
    @Schema(description = "参数值")
    private String paramValue;
}
