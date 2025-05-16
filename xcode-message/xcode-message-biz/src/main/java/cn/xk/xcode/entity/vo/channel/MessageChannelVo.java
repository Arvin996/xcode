package cn.xk.xcode.entity.vo.channel;

import cn.xk.xcode.entity.vo.param.MessageChannelParamVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @Author xuk
 * @Date 2025/3/10 9:46
 * @Version 1.0.0
 * @Description MessageChannelVo
 **/
@Data
@Schema(name = "MessageChannelVo", description = "MessageChannelVo 渠道返回vo")
public class MessageChannelVo {

    @Schema(description = "id")
    private Integer id;

    /**
     * 渠道code 如短信sms 微信小程序等
     */
    @Schema(description = "渠道code 如短信sms 微信小程序等")
    private String code;

    /**
     * 渠道名称
     */
    @Schema(description = "渠道名称")
    private String name;

    /**
     * 是否支持负载均衡 0支持 1不支持
     */
    @Schema(description = "是否支持负载均衡 0支持 1不支持")
    private String supportLoadBalance;

    /**
     * 0 启动 1未启用
     */
    @Schema(description = "0 启动 1未启用")
    private String status;

    @Schema(description = "渠道参数")
    private List<MessageChannelParamVo> channelParamVoList;
}
