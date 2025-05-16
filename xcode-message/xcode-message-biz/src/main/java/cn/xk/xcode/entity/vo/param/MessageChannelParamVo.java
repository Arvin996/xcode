package cn.xk.xcode.entity.vo.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author xuk
 * @Date 2025/5/16 8:21
 * @Version 1.0.0
 * @Description MessageChannelParamVo
 **/
@Data
@Schema(name = "MessageChannelParamVo", description = "MessageChannelParamVo 消息渠道参数返回vo")
public class MessageChannelParamVo {

    /**
     * 自增id
     */
    @Schema(description = "自增id")
    private Integer id;

    /**
     * 渠道id
     */
    @Schema(description = "渠道id")
    private Integer channelId;

    /**
     * 参数名称
     */
    @Schema(description = "参数名称")
    private String name;

    /**
     * 是否必须 0否 1是
     */
    @Schema(description = "是否必须 0否 1是")
    private String required;

    /**
     * 参数描述
     */
    @Schema(description = "参数描述")
    private String desc;
}
