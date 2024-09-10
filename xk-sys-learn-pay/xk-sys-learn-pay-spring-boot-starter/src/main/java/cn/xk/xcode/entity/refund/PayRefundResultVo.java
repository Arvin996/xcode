package cn.xk.xcode.entity.refund;

import cn.xk.xcode.enums.PayRefundStatusEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author xuk
 * @Date 2024/9/3 16:32
 * @Version 1.0.0
 * @Description PayRefundResultVo 退费结果返回
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayRefundResultVo {

    /**
     * 退款状态
     * 枚举 {@link PayRefundStatusEnums}
     */
    private Integer status;

    /**
     * 外部退款号
     * 对应 PayRefundDO 的 no 字段
     */
    private String outRefundNo;

    /**
     * 渠道退款单号
     */
    private String channelRefundNo;

    /**
     * 退款成功时间
     */
    private LocalDateTime successTime;

    /**
     * 原始的异步通知结果
     */
    private Object rawData;

    /**
     * 调用渠道的错误码
     * 注意：这里返回的是业务异常，而是不系统异常。
     */
    private String channelErrorCode;

    /**
     * 调用渠道报错时，错误信息
     */
    private String channelErrorMsg;

    /**
     * 创建【WAITING】状态的退款返回
     */
    public static PayRefundResultVo createWaitingOfRefundResultVo(String channelRefundNo,
                                             String outRefundNo, Object rawData) {
        return PayRefundResultVo.builder()
                .status(PayRefundStatusEnums.REFUND_WAITING.getStatus())
                .channelRefundNo(channelRefundNo)
                .outRefundNo(outRefundNo)
                .rawData(rawData)
                .build();
    }

    /**
     * 创建【SUCCESS】状态的退款返回
     */
    public static PayRefundResultVo createSuccessOfRefundResultVo(String channelRefundNo, LocalDateTime successTime,
                                             String outRefundNo, Object rawData) {
        return PayRefundResultVo.builder()
                .status(PayRefundStatusEnums.REFUND_SUCCESS.getStatus())
                .channelRefundNo(channelRefundNo)
                .successTime(successTime)
                .outRefundNo(outRefundNo)
                .rawData(rawData)
                .build();
    }

    /**
     * 创建【FAILURE】状态的退款返回
     */
    public static PayRefundResultVo createFailureOfRefundResultVo(String outRefundNo, Object rawData) {
        return createFailureOfRefundResultVo(null, null,
                outRefundNo, rawData);
    }

    /**
     * 创建【FAILURE】状态的退款返回
     */
    public static PayRefundResultVo createFailureOfRefundResultVo(String channelErrorCode, String channelErrorMsg,
                                             String outRefundNo, Object rawData) {
        return PayRefundResultVo.builder()
                .status(PayRefundStatusEnums.REFUND_FAILURE.getStatus())
                .channelErrorCode(channelErrorCode)
                .channelErrorMsg(channelErrorMsg)
                .outRefundNo(outRefundNo)
                .rawData(rawData)
                .build();
    }
}
