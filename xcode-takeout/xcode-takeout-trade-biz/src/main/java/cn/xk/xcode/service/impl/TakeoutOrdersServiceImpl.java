package cn.xk.xcode.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.xk.xcode.client.TakeoutAddressClient;
import cn.xk.xcode.client.TakeoutUserClient;
import cn.xk.xcode.config.TakeoutOrderProperties;
import cn.xk.xcode.core.RocketMQEnhanceTemplate;
import cn.xk.xcode.entity.TakeoutOrderResultVo;
import cn.xk.xcode.entity.dto.order.CancelOrderDto;
import cn.xk.xcode.entity.dto.order.PayOrderDto;
import cn.xk.xcode.entity.dto.order.SubmitOrderDto;
import cn.xk.xcode.entity.enums.OrderStatusEnum;
import cn.xk.xcode.entity.message.OrderCancelMessage;
import cn.xk.xcode.entity.po.TakeoutOrderDetailPo;
import cn.xk.xcode.entity.vo.TakeoutAddressResultVo;
import cn.xk.xcode.entity.vo.TakeoutUserResultVo;
import cn.xk.xcode.entity.vo.cart.ShoppingCartVo;
import cn.xk.xcode.entity.vo.order.OrderSubmitResultVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.service.TakeoutOrderDetailService;
import cn.xk.xcode.service.TakeoutShoppingCartService;
import cn.xk.xcode.utils.collections.CollectionUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.TakeoutOrdersPo;
import cn.xk.xcode.mapper.TakeoutOrdersMapper;
import cn.xk.xcode.service.TakeoutOrdersService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static cn.xk.xcode.GlobalTakeoutTradeConstants.*;
import static org.apache.rocketmq.client.producer.SendStatus.SEND_OK;

/**
 * 服务层实现。
 *
 * @author xuk
 * @since 2024-11-06
 */
@Service
@Slf4j
public class TakeoutOrdersServiceImpl extends ServiceImpl<TakeoutOrdersMapper, TakeoutOrdersPo> implements TakeoutOrdersService {

    @Resource
    private TakeoutAddressClient takeoutAddressClient;

    @Resource
    private TakeoutOrdersMapper takeoutOrdersMapper;

    @Resource
    private TakeoutShoppingCartService takeoutShoppingCartService;

    @Resource
    private TakeoutUserClient takeoutUserClient;

    @Resource
    private TakeoutOrderDetailService takeoutOrderDetailService;

    @Resource
    private TakeoutOrderProperties takeoutOrderProperties;

    @Resource
    private RocketMQEnhanceTemplate rocketMQEnhanceTemplate;

    public static final String ORDER_CANCEL_TOPIC = "order-timeout-cancel";

    public static final int RETRY_TIMES = 3;

    @Override
    public TakeoutOrderResultVo getOrder(Long orderId) {
        List<TakeoutOrderResultVo> takeoutOrderResultVos = takeoutOrdersMapper.getTakeoutOrderResult(MapUtil.of("id", orderId));
        if (CollectionUtil.isEmpty(takeoutOrderResultVos)) {
            return null;
        }
        return takeoutOrderResultVos.get(0);
    }

    @Override
    public List<TakeoutOrderResultVo> getOrderList(Long userId) {
        List<TakeoutOrderResultVo> takeoutOrderResultVos = takeoutOrdersMapper.getTakeoutOrderResult(MapUtil.of("userId", userId));
        if (CollectionUtil.isEmpty(takeoutOrderResultVos)) {
            return CollectionUtil.createEmptyList();
        }
        return takeoutOrderResultVos;
    }

    @Override
    public TakeoutOrderResultVo getUserOrderId(Long userId, Long orderId) {
        Map<String, Object> map = MapUtil.of("userId", userId);
        map.put("id", orderId);
        List<TakeoutOrderResultVo> takeoutOrderResultVos = takeoutOrdersMapper.getTakeoutOrderResult(map);
        if (CollectionUtil.isEmpty(takeoutOrderResultVos)) {
            return null;
        }
        return takeoutOrderResultVos.get(0);
    }

    @Transactional
    @Override
    public OrderSubmitResultVo submitOrder(SubmitOrderDto submitOrderDto) {
        // 1. 获取用户收货地址
        List<TakeoutAddressResultVo> takeoutAddressResultVos = takeoutAddressClient.getTakeoutAddressList(submitOrderDto.getUserId()).getData();
        if (CollectionUtil.isEmpty(takeoutAddressResultVos)) {
            ExceptionUtil.castServiceException(USER_ADDRESS_NOT_EXISTS);
        }
        TakeoutAddressResultVo takeoutAddressResultVo = null;
        for (TakeoutAddressResultVo v : takeoutAddressResultVos) {
            if (v.getId().equals(submitOrderDto.getAddressId())) {
                takeoutAddressResultVo = v;
            }
        }
        if (Objects.isNull(takeoutAddressResultVo)) {
            ExceptionUtil.castServiceException(USER_ADDRESS_NOT_EXISTS);
        }
        // 2. 处理订单信息
        // 2.1 根据用户id获取购物车数据
        ShoppingCartVo shoppingCart = takeoutShoppingCartService.getShoppingCart(submitOrderDto.getUserId());
        if (CollectionUtil.isEmpty(shoppingCart.getTakeoutShoppingCartPoList())) {
            ExceptionUtil.castServiceException(SHOPPING_CART_NOT_CONTAINS_ITEM);
        }
        // 2.2 生成订单
        TakeoutUserResultVo takeoutUserResultVo = takeoutUserClient.getTakeoutUser(submitOrderDto.getUserId()).getData();
        TakeoutOrdersPo takeoutOrdersPo = new TakeoutOrdersPo();
        takeoutOrdersPo.setUserId(submitOrderDto.getUserId());
        takeoutOrdersPo.setAddressId(submitOrderDto.getAddressId());
        takeoutOrdersPo.setOrderTime(LocalDateTime.now());
        takeoutOrdersPo.setAmount(shoppingCart.getTotalPrice());
        takeoutOrdersPo.setRemark(submitOrderDto.getRemark());
        takeoutOrdersPo.setMobile(takeoutAddressResultVo.getMobile());
        takeoutOrdersPo.setAddress(takeoutAddressResultVo.getProvinceName()
                + takeoutAddressResultVo.getDistrictName()
                + takeoutAddressResultVo.getCityName()
                + takeoutAddressResultVo.getDetail());
        takeoutOrdersPo.setUserName(takeoutUserResultVo.getName());
        takeoutOrdersPo.setConsignee(takeoutOrdersPo.getConsignee());
        this.save(takeoutOrdersPo);
        // 2.3 插入订单明细
        List<TakeoutOrderDetailPo> takeoutOrderDetailPos = shoppingCart.getTakeoutShoppingCartPoList().stream().map(takeoutShoppingCartPo -> {
            TakeoutOrderDetailPo takeoutOrderDetailPo = new TakeoutOrderDetailPo();
            takeoutOrderDetailPo.setName(takeoutShoppingCartPo.getName());
            takeoutOrderDetailPo.setImage(takeoutShoppingCartPo.getImage());
            takeoutOrderDetailPo.setOrderId(takeoutOrdersPo.getId());
            takeoutOrderDetailPo.setDishId(takeoutShoppingCartPo.getDishId());
            takeoutOrderDetailPo.setSetmealId(takeoutShoppingCartPo.getSetmealId());
            takeoutOrderDetailPo.setDishFlavor(takeoutShoppingCartPo.getDishFlavor());
            takeoutOrderDetailPo.setNumber(takeoutShoppingCartPo.getNumber());
            takeoutOrderDetailPo.setAmount(takeoutShoppingCartPo.getAmount());
            return takeoutOrderDetailPo;
        }).collect(Collectors.toList());
        takeoutOrderDetailService.saveBatch(takeoutOrderDetailPos);
        // 2.4 构造返回参数
        OrderSubmitResultVo orderSubmitResultVo = new OrderSubmitResultVo();
        orderSubmitResultVo.setOrderId(takeoutOrdersPo.getId());
        orderSubmitResultVo.setPayAmount(takeoutOrdersPo.getAmount());
        orderSubmitResultVo.setMobile(takeoutAddressResultVo.getMobile());
        orderSubmitResultVo.setAddress(takeoutOrdersPo.getAddress());
        orderSubmitResultVo.setConsignee(takeoutOrdersPo.getConsignee());
        orderSubmitResultVo.setOrderTime(takeoutOrdersPo.getOrderTime());
        orderSubmitResultVo.setPayDeadlineTime(LocalDateTime.now().plusSeconds(takeoutOrderProperties.getDelayLevel().getTime()));
        orderSubmitResultVo.setOrderDetailList(takeoutOrderDetailPos);
        // 2.5 发送订单过期消息 or 定时任务扫描 这里要注意 事务提交 了以后才发消息
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                //具体的业务操作
                OrderCancelMessage orderCancelMessage = new OrderCancelMessage();
                orderCancelMessage.setBizKey(takeoutOrdersPo.getId().toString());
                orderCancelMessage.setMessageSource("order-submit");
                orderCancelMessage.setSendTime(LocalDateTime.now());
                orderCancelMessage.setRetryTimes(RETRY_TIMES);
                SendResult sendResult = rocketMQEnhanceTemplate.sendDelay(ORDER_CANCEL_TOPIC, orderCancelMessage, takeoutOrderProperties.getDelayLevel().getLevel());
                SendStatus sendStatus = sendResult.getSendStatus();
                if (!SEND_OK.equals(sendStatus)) {
                    log.warn("订单：{}，超时取消发送失败", takeoutOrdersPo.getId());
                }
            }
        });

        return orderSubmitResultVo;
    }

    @Override
    public Boolean cancelOrder(CancelOrderDto cancelOrderDto) {
        TakeoutOrdersPo takeoutOrdersPo = this.getById(cancelOrderDto.getId());
        if (Objects.isNull(takeoutOrdersPo)){
            ExceptionUtil.castServiceException(ORDER_NOT_EXISTS);
        }
        if (!OrderStatusEnum.isWaitingPay(takeoutOrdersPo.getStatus())){
            ExceptionUtil.castServiceException(ORDER_IS_NOT_WAITING_APY);
        }
        takeoutOrdersPo.setStatus(OrderStatusEnum.CANCEL.getStatus());
        return this.updateById(takeoutOrdersPo);
    }

    @Override
    public Boolean payOrder(PayOrderDto payOrderDto) {
        TakeoutOrdersPo takeoutOrdersPo = this.getById(payOrderDto.getOrderId());
        if (Objects.isNull(takeoutOrdersPo)){
            ExceptionUtil.castServiceException(ORDER_NOT_EXISTS);
        }
        if (!OrderStatusEnum.isWaitingPay(takeoutOrdersPo.getStatus())){
            ExceptionUtil.castServiceException(ORDER_STATUS_IS_NOT_WAITING_PAY);
        }
        // 这里直接成功了 不使用支付平台
        takeoutOrdersPo.setStatus(OrderStatusEnum.SUCCESS.getStatus());
        takeoutOrdersPo.setPayMethod(payOrderDto.getPayType());
        takeoutOrdersPo.setCheckoutTime(LocalDateTime.now());
        return this.updateById(takeoutOrdersPo);
    }
}
