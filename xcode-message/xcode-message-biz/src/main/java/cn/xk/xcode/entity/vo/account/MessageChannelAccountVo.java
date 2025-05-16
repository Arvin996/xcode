package cn.xk.xcode.entity.vo.account;

import cn.xk.xcode.core.annotation.FlexFieldTrans;
import cn.xk.xcode.core.entity.TransVo;
import cn.xk.xcode.entity.DataStringObjectBaseEntity;
import cn.xk.xcode.entity.po.MessageChannelPo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author xuk
 * @Date 2025/5/15 17:29
 * @Version 1.0.0
 * @Description MessageChannelAccountVo
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "MessageChannelAccountVo", description = "MessageChannelAccountVo 消息渠道账户返回vo")
public class MessageChannelAccountVo extends DataStringObjectBaseEntity implements TransVo {

    /**
     * 自增id
     */
    @Schema(description = "自增id")
    private Integer id;

    /**
     * 渠道账户名称
     */
    @Schema(description = "渠道账户名称")
    private String accountName;

    /**
     * 渠道id
     */
    @Schema(description = "渠道id")
    private Integer channelId;

    /**
     * 渠道code
     */
    @Schema(description = "渠道code")
    //  @FlexFieldTrans(ref = MessageChannelPo.class, targetField = "channelName", refConditionField = "code", refSourceFiled = "name")
    private String channelCode;

    /**
     * 渠道名称
     */
    @Schema(description = "渠道名称")
    private String channelName;

    /**
     * 账号权重
     */
    @Schema(description = "账号权重")
    private Double weight;

    /**
     * 0 启用 1弃用
     */
    @Schema(description = "0 启用 1弃用")
    private String status;


    /**
     * 账号绑定参数
     */
    @Schema(description = "账号绑定参数")
    private List<AccountChannelParams> accountChannelParams;

    @Schema(description = "账号绑定参数")
    @Data
    public static class AccountChannelParams {
        /**
         * 自增id
         */
        @Schema(description = "自增id")
        private Integer channelParamId;

        /**
         * 渠道id
         */
        @Schema(description = "渠道参数id")
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


        /**
         * 自增id
         */
        @Schema(description = "自增id")
        private Integer channelParamValueId;

        /**
         * 参数值
         */
        @Schema(description = "参数值")
        private String paramValue;
    }
}
