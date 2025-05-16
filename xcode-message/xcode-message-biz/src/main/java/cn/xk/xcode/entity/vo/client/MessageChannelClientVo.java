package cn.xk.xcode.entity.vo.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author xuk
 * @Date 2025/5/15 14:36
 * @Version 1.0.0
 * @Description MessageChannelClientVo
 **/
@Data
@Schema(name = "MessageChannelClientVo", description = "MessageChannelClientVo 消息渠道接入商返回vo")
public class MessageChannelClientVo {

    /**
     * 自增id
     */
    @Schema(description = "id")
    private Integer id;

    /**
     * 接入商名称
     */
    @Schema(description = "接入商名称")
    private String name;

    /**
     * 接入商邮箱
     */
    @Schema(description = "接入商邮箱")
    private String email;

    /**
     * 接入商手机号
     */
    @Schema(description = "接入商手机号")
    private String mobile;

    /**
     * 接入密钥，用于校验合法性
     */
    @Schema(description = "接入密钥，用于校验合法性")
    private String accessToken;

    /**
     * 0启用 1禁用
     */
    @Schema(description = "0启用 1禁用")
    private String status;

    /**
     * 接入商消息配额 默认100
     */
    @Schema(description = "接入商消息配额 默认100")
    private Integer accessCount;

    /**
     * 已用配额
     */
    @Schema(description = "已用配额")
    private Integer usedCount;

    /**
     * 剩余配额
     */
    @Schema(description = "剩余配额")
    private Integer restAccount;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    /**
     * token刷新时间
     */
    @Schema(description = "token刷新时间")
    private LocalDateTime tokenRefreshTime;

    /**
     * 接入商绑定渠道
     */
    private List<ClientChannelVo> clientChannelVoList;

    @Data
    @Schema(name = "ClientChannelVo", description = "ClientChannelDto 渠道返回")
    public static class ClientChannelVo {

        @Schema(description = "渠道id")
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
    }
}
