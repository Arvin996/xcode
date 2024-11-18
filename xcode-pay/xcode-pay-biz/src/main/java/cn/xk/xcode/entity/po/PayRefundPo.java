package cn.xk.xcode.entity.po;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;


import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 *  实体类。
 *
 * @author Administrator
 * @since 2024-09-23
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("pay_refund")
public class PayRefundPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 退款订单号
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.flexId)
    private Long id;

    /**
     * 外部退款订单编号
     */
    private String outRefundNo;

    /**
     * 应用编号
     */
    private Integer appId;

    /**
     * 渠道码编号
     */
    private String channelCode;

    /**
     * 订单号
     */
    private Long orderId;

    /**
     * 外部订单号
     */
    private String outTradeNo;

    /**
     * 商户订单编号
     */
    private String merchantOrderId;

    /**
     * 商户退款订单号
     */
    private String merchantRefundId;

    /**
     * 退款异步通知url
     */
    private String notifyUrl;

    /**
     * 退款状态
     */
    private Integer status;

    /**
     * 退款金额 单位分
     */
    private Integer refundPrice;

    /**
     * 支付金额 单位分
     */
    private Integer payPrice;

    /**
     * 退款理由
     */
    private String reason;

    /**
     * 用户ip
     */
    private String userIp;

    /**
     * 渠道订单编号
     */
    private String channelOrderNo;

    /**
     * 渠道退款编号
     */
    private String channelRefundNo;

    /**
     * 退款成功时间
     */
    private LocalDateTime successTime;

    /**
     * 渠道错误码
     */
    private String channelErrorCode;

    /**
     * 渠道错误信息
     */
    private String channelErrorMsg;

    /**
     * 渠道通知数据
     */
    private String channelNotifyData;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
