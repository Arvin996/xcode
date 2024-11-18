package cn.xk.xcode.entity.order;

import cn.xk.xcode.enums.PayOrderDisplayModeEnum;
import cn.xk.xcode.enums.PayOrderStatusEnums;
import cn.xk.xcode.exception.core.ExceptionUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static cn.xk.xcode.enums.PayErrorCodeConstants.PAY_ORDER_STATUS_NOT_VALID;

/**
 * @Author xuk
 * @Date 2024/9/3 16:30
 * @Version 1.0.0
 * @Description PayOrderResultVo 支付订单返回vo
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayOrderResultVo {

    /**
     * 支付状态
     * 枚举：{@link PayOrderStatusEnums}
     */
    private Integer status;

    /**
     * 外部订单号
     */
    private String outTradeNo;

    /**
     * 支付渠道订单编号
     */
    private String channelOrderNo;

    /**
     * 支付渠道用户编号
     */
    private String channelUserId;

    /**
     * 支付成功时间
     */
    private LocalDateTime successTime;

    /**
     * 原始的同步/异步通知结果
     */
    private Object rawData;

    /**
     * 展示模式
     * <p>
     * 枚举 {@link PayOrderDisplayModeEnum} 类
     */
    private String displayMode;
    /**
     * 展示内容
     */
    private String displayContent;

    /**
     * 调用渠道的错误码
     */
    private String channelErrorCode;

    /**
     * 调用渠道报错时，错误信息
     */
    private String channelErrorMsg;

    /**
     * 创建【WAITING】待支付状态的订单返回
     */
    public static PayOrderResultVo createWaitingOrderResultVo(String outTradeNo, Object rawData) {
        return createWaitingOrderResultVo(null, null, outTradeNo, rawData);
    }

    /**
     * 创建【WAITING】待支付状态的订单返回
     */
    public static PayOrderResultVo createWaitingOrderResultVo(String displayMode, String displayContent, String outTradeNo, Object rawData) {
        return PayOrderResultVo.builder()
                .status(PayOrderStatusEnums.PAY_WAITING.getStatus())
                .displayMode(displayMode)
                .displayContent(displayContent)
                .outTradeNo(outTradeNo)
                .rawData(rawData)
                .build();
    }

    /**
     * 创建【SUCCESS】状态的订单返回
     */
    public static PayOrderResultVo createSuccessOrderResultVo(String channelOrderNo,
                                                              String channelUserId,
                                                              LocalDateTime successTime,
                                                              String outTradeNo,
                                                              Object rawData) {
        return PayOrderResultVo.builder()
                .status(PayOrderStatusEnums.PAY_SUCCESS.getStatus())
                .channelOrderNo(channelOrderNo)
                .channelUserId(channelUserId)
                .successTime(successTime)
                .outTradeNo(outTradeNo)
                .rawData(rawData)
                .build();
    }

    /**
     * 创建【CLOSED】状态的订单返回，适合调用支付渠道失败时
     */
    public static PayOrderResultVo createClosedOrderResultVo(String channelErrorCode, String channelErrorMsg,
                                                             String outTradeNo, Object rawData) {
        return PayOrderResultVo.builder()
                .status(PayOrderStatusEnums.PAY_CLOSED.getStatus())
                .channelErrorCode(channelErrorCode)
                .channelErrorMsg(channelErrorMsg)
                .outTradeNo(outTradeNo)
                .rawData(rawData)
                .build();
    }

    /**
     * 创建指定状态的订单返回，适合支付渠道回调时
     */
    public static PayOrderResultVo createAnyStatusOrderResultVo(Integer status, String channelOrderNo, String channelUserId, LocalDateTime successTime,
                                                                String outTradeNo, Object rawData) {
        if (!PayOrderStatusEnums.isValid(status)) {
            ExceptionUtil.castServiceException(PAY_ORDER_STATUS_NOT_VALID, status);
        }
        return PayOrderResultVo.builder()
                .status(status)
                .channelOrderNo(channelOrderNo)
                .channelUserId(channelUserId)
                .successTime(successTime)
                .outTradeNo(outTradeNo)
                .rawData(rawData)
                .build();
    }
}
