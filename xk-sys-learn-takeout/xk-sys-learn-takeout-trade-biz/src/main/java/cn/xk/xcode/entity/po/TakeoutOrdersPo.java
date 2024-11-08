package cn.xk.xcode.entity.po;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  实体类。
 *
 * @author xuk
 * @since 2024-11-06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("takeout_orders")
public class TakeoutOrdersPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    @Id
    private Long id;

    /**
     * 订单状态 1待付款，2待派送，3已派送，4已完成，5已取消
     */
    private Integer status;

    /**
     * 下单用户
     */
    private Long userId;

    /**
     * 地址id
     */
    private Long addressId;

    /**
     * 下单时间
     */
    private LocalDateTime orderTime;

    /**
     * 结账时间
     */
    private LocalDateTime checkoutTime;

    /**
     * 支付方式 1微信,2支付宝
     */
    private Integer payMethod;

    /**
     * 实收金额
     */
    private BigDecimal amount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 收货人电话
     */
    private String mobile;

    /**
     * 收货人地址
     */
    private String address;

    /**
     * 下单用户名
     */
    private String userName;

    /**
     * 收货人姓名
     */
    private String consignee;

}
