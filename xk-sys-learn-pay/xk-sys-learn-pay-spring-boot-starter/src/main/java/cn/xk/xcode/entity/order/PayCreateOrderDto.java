package cn.xk.xcode.entity.order;

import cn.xk.xcode.enums.PayOrderDisplayModeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2024/9/3 16:30
 * @Version 1.0.0
 * @Description PayCreateOrderDto 下单dto实体类
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayCreateOrderDto {

    /**
     * 用户 IP
     */
    @NotBlank(message = "用户 IP 不能为空")
    private String userIp;

    /**
     * 外部订单号
     */
    @NotBlank(message = "外部订单编号不能为空")
    private String outTradeNo;

    /**
     * 商品标题
     */
    @NotBlank(message = "商品标题不能为空")
    @Length(max = 32, message = "商品标题不能超过 32")
    private String subject;

    /**
     * 商品描述信息
     */
    @Length(max = 128, message = "商品描述信息长度不能超过128")
    private String body;

    /**
     * 支付结果的 notify 回调地址 这个就是异步通知地址
     */
    @NotBlank(message = "支付结果的回调地址不能为空")
    @URL(message = "支付结果的 notify 回调地址必须是 URL 格式")
    private String notifyUrl;

    /**
     * 支付结果的 return 回调地址 这个是支付后回调地址
     */
    @URL(message = "支付结果的 return 回调地址必须是 URL 格式")
    private String returnUrl;

    /**
     * 支付金额，单位：分
     */
    @NotNull(message = "支付金额不能为空")
    @DecimalMin(value = "0", inclusive = false, message = "支付金额必须大于零")
    private Integer price;

    /**
     * 支付过期时间 这里过期后怎么处理？
     */
    @NotNull(message = "支付过期时间不能为空")
    private LocalDateTime expireTime;

    /**
     * 支付渠道的额外参数
     * 例如说，微信公众号需要传递 openid 参数
     */
    private Map<String, String> channelExtras;

    /**
     * 展示模式
     * 如果不传递，则每个支付渠道使用默认的方式
     * 枚举 {@link PayOrderDisplayModeEnum}
     */
    private String displayMode;
}
