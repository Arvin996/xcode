package cn.xk.xcode.entity.vo.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Author xuk
 * @Date 2024/9/27 10:08
 * @Version 1.0.0
 * @Description PayOrderRespVo
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "订单查询 vo")
public class PayOrderRespVo {

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private Long id;

    /**
     * 应用id
     */
    @Schema(description = "应用id")
    private Integer appId;

    /**
     * 渠道编号
     */
    @Schema(description = "渠道编号")
    private String channelCode;

    /**
     * 商户订单编号
     */
    @Schema(description = "商户订单编号")
    private String merchantOrderId;

    /**
     * 商品标题
     */
    @Schema(description = "商品标题")
    private String subject;

    /**
     * 商品描述
     */
    @Schema(description = "商品描述")
    private String body;

    /**
     * 异步通知地址
     */
    @Schema(description = "异步通知地址")
    private String notifyUrl;

    /**
     * 支付金额 单位分
     */
    @Schema(description = "支付金额 单位分")
    private Integer price;

    /**
     * 渠道费 单位分
     */
    @Schema(description = "渠道费 单位分")
    private Integer channelFeePrice;

    /**
     * 支付状态
     */
    @Schema(description = "支付状态")
    private Integer status;

    /**
     * 用户ip
     */
    @Schema(description = "用户ip")
    private String userIp;

    /**
     * 订单失效时间
     */
    @Schema(description = "订单失效时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expireTime;

    /**
     * 订单支付成功时间
     */
    @Schema(description = "订单支付成功时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime successTime;

    /**
     * 外部订单编号
     */
    @Schema(description = "外部订单编号")
    private String outTradeNo;

    /**
     * 退费金额
     */
    @Schema(description = "退费金额")
    private Integer refundPrice;

    /**
     * 渠道用户id 如openid
     */
    @Schema(description = "渠道用户id 如openid")
    private String channelUserId;

    /**
     * 渠道订单号
     */
    @Schema(description = "渠道订单号")
    private String channelOrderNo;

    /**
     * 渠道支付额外参数
     */
    @Schema(description = "渠道支付额外参数")
    private String channelExtras;

    /**
     * 渠道错误码
     */
    @Schema(description = "渠道错误码")
    private String channelErrorCode;

    /**
     * 渠道错误信息
     */
    @Schema(description = "渠道错误信息")
    private String channelErrorMsg;

    /**
     * 渠道通知内容
     */
    @Schema(description = "渠道通知内容")
    private String channelNotifyData;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
