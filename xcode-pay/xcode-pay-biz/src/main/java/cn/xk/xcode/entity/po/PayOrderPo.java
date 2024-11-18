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

/**
 *  实体类。
 *
 * @author Administrator
 * @since 2024-09-23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("pay_order")
public class PayOrderPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.flexId)
    private Long id;

    /**
     * 应用id
     */
    private Integer appId;

    /**
     * 渠道编号
     */
    private String channelCode;

    /**
     * 商户订单编号
     */
    private String merchantOrderId;

    /**
     * 商品标题
     */
    private String subject;

    /**
     * 商品描述
     */
    private String body;

    /**
     * 异步通知地址
     */
    private String notifyUrl;

    /**
     * 支付金额 单位分
     */
    private Integer price;

    /**
     * 渠道费 单位分
     */
    private Integer channelFeePrice;

    /**
     * 支付状态
     */
    private Integer status;

    /**
     * 用户ip
     */
    private String userIp;

    /**
     * 订单失效时间
     */
    private LocalDateTime expireTime;

    /**
     * 订单支付成功时间
     */
    private LocalDateTime successTime;

    /**
     * 外部订单编号
     */
    private String outTradeNo;

    /**
     * 退费金额
     */
    private Integer refundPrice;

    /**
     * 渠道用户id 如openid
     */
    private String channelUserId;

    /**
     * 渠道订单号
     */
    private String channelOrderNo;

    /**
     * 渠道支付额外参数
     */
    private String channelExtras;

    /**
     * 渠道错误码
     */
    private String channelErrorCode;

    /**
     * 渠道错误信息
     */
    private String channelErrorMsg;

    /**
     * 渠道通知内容
     */
    private String channelNotifyData;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
