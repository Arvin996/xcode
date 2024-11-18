package cn.xk.xcode.mq;

import cn.xk.xcode.consumer.AbstractEnhanceMessageConsumer;
import cn.xk.xcode.entity.TakeoutOrderResultVo;
import cn.xk.xcode.entity.enums.OrderStatusEnum;
import cn.xk.xcode.entity.message.OrderCancelMessage;
import cn.xk.xcode.entity.po.TakeoutOrdersPo;
import cn.xk.xcode.service.TakeoutOrdersService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.Objects;

import static cn.xk.xcode.service.impl.TakeoutOrdersServiceImpl.ORDER_CANCEL_TOPIC;

/**
 * @Author xuk
 * @Date 2024/11/8 13:30
 * @Version 1.0.0
 * @Description OrderTimeoutCancelConsumer 订单超时取消
 **/
@RocketMQMessageListener(consumerGroup = "takeoutOrderConsumerGroup", topic = ORDER_CANCEL_TOPIC)
@Component
@Slf4j
public class OrderTimeoutCancelConsumer extends AbstractEnhanceMessageConsumer<OrderCancelMessage> {

    @Resource
    private TakeoutOrdersService takeoutOrdersService;

    @Override
    protected void handleMessage(OrderCancelMessage message) throws Exception {
        long orderId = Long.parseLong(message.getBizKey());
        // 校验订单是否存在
        TakeoutOrdersPo takeoutOrdersPo = takeoutOrdersService.getById(orderId);
        if (Objects.isNull(takeoutOrdersPo)){
            log.info("订单号:{}不存在", orderId);
            return;
        }
        // 查看订单状态
        Integer status = takeoutOrdersPo.getStatus();
        if (!OrderStatusEnum.isWaitingPay(status)){
            log.info("订单号:{}状态不是待支付状态", orderId);
            return;
        }
        // 修改订单状态(已取消)
        takeoutOrdersPo.setStatus(OrderStatusEnum.CANCEL.getStatus());
        takeoutOrdersService.updateById(takeoutOrdersPo);
    }

    @Override
    protected void handleMaxRetriesExceeded(OrderCancelMessage message) {
        log.info("订单号：{}超时取消达到最大重试次数", message.getBizKey());
    }

    @Override
    protected boolean isRetry() {
        return true;
    }

    @Override
    protected boolean throwException() {
        return true;
    }
}
