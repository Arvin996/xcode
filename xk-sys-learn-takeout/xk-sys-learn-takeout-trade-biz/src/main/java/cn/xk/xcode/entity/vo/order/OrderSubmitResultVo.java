package cn.xk.xcode.entity.vo.order;

import cn.xk.xcode.entity.po.TakeoutOrderDetailPo;
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
 * @Date 2024/11/7 14:08
 * @Version 1.0.0
 * @Description OrderSubmitResultVo
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "提交购物车订单dto")
public class OrderSubmitResultVo {

    @Schema(description = "订单id")
    private Long orderId;

    @Schema(description = "支付金额")
    private BigDecimal payAmount;

    /**
     * 收货人电话
     */
    private String mobile;

    /**
     * 收货人地址
     */
    private String address;

    /**
     * 收货人姓名
     */
    private String consignee;

    /**
     * 下单时间
     */
    private LocalDateTime orderTime;

    /**
     * 支付过期时间
     */
    private LocalDateTime payDeadlineTime;

    /**
     * 订单明细
     */
    private List<TakeoutOrderDetailPo> orderDetailList;

}
