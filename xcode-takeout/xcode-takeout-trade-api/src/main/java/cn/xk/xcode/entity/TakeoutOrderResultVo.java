package cn.xk.xcode.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/11/6 14:02
 * @Version 1.0.0
 * @Description TakeoutOrderResultVo
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(name = "订单信息返回")
public class TakeoutOrderResultVo {

    /**
     * 订单id
     */
    @Schema(description = "订单id")
    private Long id;

    /**
     * 订单状态 1待付款，2待派送，3已派送，4已完成，5已取消
     */
    @Schema(description = "订单状态 1待付款，2待派送，3已派送，4已完成，5已取消")
    private Integer status;

    /**
     * 下单用户
     */
    @Schema(description = "下单用户")
    private Long userId;

    /**
     * 地址id
     */
    @Schema(description = "地址id")
    private Long addressId;

    /**
     * 下单时间
     */
    @Schema(description = "下单时间")
    private LocalDateTime orderTime;

    /**
     * 结账时间
     */
    @Schema(description = "结账时间")
    private LocalDateTime checkoutTime;

    /**
     * 支付方式 1微信,2支付宝
     */
    @Schema(description = "支付方式 1微信,2支付宝")
    private Integer payMethod;

    /**
     * 实收金额
     */
    @Schema(description = "实收金额")
    private BigDecimal amount;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 收货人电话
     */
    @Schema(description = "收货人电话")
    private String phone;

    /**
     * 收货人地址
     */
    @Schema(description = "收货人地址")
    private String address;

    /**
     * 下单用户名
     */
    @Schema(description = "下单用户名")
    private String userName;

    /**
     * 收货人姓名
     */
    @Schema(description = "收货人姓名")
    private String consignee;

    /**
     * 订单明细
     */
    @Schema(description = "订单明细")
    private List<TakeoutOrderDetailsResultVo> orderDetailsList;
}
