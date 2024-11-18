package cn.xk.xcode.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import cn.xk.xcode.config.PayProperties;
import cn.xk.xcode.convert.refund.PayRefundConvert;
import cn.xk.xcode.core.client.PayClient;
import cn.xk.xcode.core.factory.PayClientFactory;
import cn.xk.xcode.entity.dto.refund.PayCreateRefundDto;
import cn.xk.xcode.entity.po.PayAppPo;
import cn.xk.xcode.entity.po.PayChannelPo;
import cn.xk.xcode.entity.po.PayOrderPo;
import cn.xk.xcode.entity.refund.PayRefundResultVo;
import cn.xk.xcode.entity.vo.refund.PayCreateRefundVo;
import cn.xk.xcode.enums.PayOrderStatusEnums;
import cn.xk.xcode.enums.PayRefundStatusEnums;
import cn.xk.xcode.enums.notify.PayNotifyTypeEnum;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.service.*;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.PayRefundPo;
import cn.xk.xcode.mapper.PayRefundMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.time.LocalDateTime;

import static cn.xk.xcode.entity.def.PayOrderTableDef.PAY_ORDER_PO;
import static cn.xk.xcode.entity.def.PayRefundTableDef.PAY_REFUND_PO;
import static cn.xk.xcode.enums.PayModuleErrorCodeConstants.*;

/**
 * 服务层实现。
 *
 * @author Administrator
 * @since 2024-09-23
 */
@Slf4j
@Service
public class PayRefundServiceImpl extends ServiceImpl<PayRefundMapper, PayRefundPo> implements PayRefundService {

    @Resource
    private PayAppService payAppService;

    @Resource
    private PayOrderService payOrderService;

    @Resource
    private PayProperties payProperties;

    @Resource
    private PayClientFactory payClientFactory;

    @Resource
    private PayChannelService payChannelService;

    @Resource
    private PayOutTradeNoRedisGenerateService payOutTradeNoRedisGenerateService;

    @Resource
    private PayNotifyTaskService payNotifyTaskService;

    @Override
    public PayCreateRefundVo getRefund(Long refundId) {
        return PayRefundConvert.convert(this.getById(refundId));
    }

    @Override
    public Long createRefund(PayCreateRefundDto reqDTO) {
        PayAppPo payAppPo = payAppService.checkApp(reqDTO.getAppId());
        PayOrderPo payOrderPo = validatePayOrderCanRefund(reqDTO);
        String channelCode = payOrderPo.getChannelCode();
        PayChannelPo payChannelPo = payChannelService.checkChannel(channelCode);
        PayClient payClient = payClientFactory.getPayClient(channelCode);
        PayRefundPo payRefundPo = new PayRefundPo();
        String payOutRefundNo = payOutTradeNoRedisGenerateService.generatePayOutRefundNo();
        payRefundPo.setOutRefundNo(payOutRefundNo);
        payRefundPo.setAppId(reqDTO.getAppId());
        payRefundPo.setChannelCode(payOrderPo.getChannelCode());
        payRefundPo.setOrderId(payOrderPo.getId());
        payRefundPo.setOutTradeNo(payOrderPo.getOutTradeNo());
        payRefundPo.setMerchantOrderId(payOrderPo.getMerchantOrderId());
        payRefundPo.setMerchantRefundId(reqDTO.getMerchantRefundId());
        payRefundPo.setNotifyUrl(payAppPo.getRefundNotifyUrl());
        payRefundPo.setStatus(PayRefundStatusEnums.REFUND_WAITING.getStatus());
        payRefundPo.setRefundPrice(reqDTO.getPrice());
        payRefundPo.setPayPrice(payOrderPo.getPrice());
        payRefundPo.setReason(reqDTO.getReason());
        payRefundPo.setUserIp(reqDTO.getUserIp());
        payRefundPo.setChannelOrderNo(payOrderPo.getChannelOrderNo());
        payRefundPo.setCreateTime(LocalDateTime.now());
        this.save(payRefundPo);
        cn.xk.xcode.entity.refund.PayCreateRefundDto payCreateRefundDto = new cn.xk.xcode.entity.refund.PayCreateRefundDto();
        payCreateRefundDto.setOutTradeNo(payOrderPo.getOutTradeNo());
        payCreateRefundDto.setOutRefundNo(payOutRefundNo);
        payCreateRefundDto.setReason(reqDTO.getReason());
        payCreateRefundDto.setPayPrice(payOrderPo.getPrice());
        payCreateRefundDto.setRefundPrice(reqDTO.getPrice());
        payCreateRefundDto.setNotifyUrl(genChannelRefundNotifyUrl(payChannelPo));
        PayRefundResultVo payRefundResultVo = payClient.createRefund(payCreateRefundDto);
        getSelf().notifyRefund(payRefundPo.getId(), channelCode, payRefundResultVo);
        return payRefundPo.getId();
    }


    @Transactional
    public void notifyRefund(Long refundId, String channelCode, PayRefundResultVo payRefundResultVo) {
        if (PayRefundStatusEnums.isSuccess(payRefundResultVo.getStatus())) {
            notifyRefundSuccess(channelCode, payRefundResultVo);
        }
        if (PayRefundStatusEnums.isFailure(payRefundResultVo.getStatus())) {
            notifyRefundFailure(channelCode, payRefundResultVo);
        }
        if (PayRefundStatusEnums.REFUND_WAITING.getStatus().equals(payRefundResultVo.getStatus())) {
            // 添加任务
            payNotifyTaskService.createPayNotifyTask(PayNotifyTypeEnum.REFUND.getType(), refundId);
        }
    }

    private void notifyRefundFailure(String channelCode, PayRefundResultVo payRefundResultVo) {
        PayRefundPo payRefundPo = this.getOne(PAY_REFUND_PO.OUT_REFUND_NO.eq(payRefundResultVo.getOutRefundNo()).and(
                PAY_REFUND_PO.CHANNEL_CODE.eq(channelCode)));
        if (ObjectUtil.isNull(payRefundPo)) {
            ExceptionUtil.castServiceException(REFUND_IS_NOT_EXISTS);
        }
        if (PayRefundStatusEnums.isFailure(payRefundPo.getStatus())) { // 如果已经是成功，直接返回，不用重复更新
            log.info("[notifyRefundSuccess][退款订单({}) 已经是退款关闭，无需更新]", payRefundPo.getId());
            return;
        }
        if (!PayRefundStatusEnums.REFUND_WAITING.getStatus().equals(payRefundPo.getStatus())) {
            ExceptionUtil.castServerException(REFUND_STATUS_IS_NOT_WAITING);
        }
        // 1.2 更新 PayRefundDO
        PayRefundPo updateRefundObj = new PayRefundPo()
                .setChannelRefundNo(payRefundResultVo.getChannelRefundNo())
                .setStatus(PayRefundStatusEnums.REFUND_FAILURE.getStatus())
                .setChannelNotifyData(JSONUtil.toJsonStr(payRefundResultVo.getRawData()))
                .setChannelErrorCode(payRefundResultVo.getChannelErrorCode()).setChannelErrorMsg(payRefundResultVo.getChannelErrorMsg());
        boolean update = this.update(updateRefundObj, PAY_REFUND_PO.ID.eq(payRefundPo.getId()).and(PAY_REFUND_PO.STATUS.eq(payRefundPo.getStatus())));
        if (!update) {
            ExceptionUtil.castServiceException(REFUND_STATUS_IS_NOT_WAITING);
        }
        log.info("[notifyRefundFailure][退款订单({}) 更新为退款失败]", payRefundPo.getId());
    }

    private void notifyRefundSuccess(String channelCode, PayRefundResultVo payRefundResultVo) {
        PayRefundPo payRefundPo = this.getOne(PAY_REFUND_PO.OUT_REFUND_NO.eq(payRefundResultVo.getOutRefundNo()).and(
                PAY_REFUND_PO.CHANNEL_CODE.eq(channelCode)));
        if (ObjectUtil.isNull(payRefundPo)) {
            ExceptionUtil.castServiceException(REFUND_IS_NOT_EXISTS);
        }
        if (PayRefundStatusEnums.isSuccess(payRefundPo.getStatus())) {
            log.info("[notifyRefundSuccess][退款订单({}) 已经是退款成功，无需更新]", payRefundPo.getId());
            return;
        }
        if (!PayRefundStatusEnums.REFUND_WAITING.getStatus().equals(payRefundPo.getStatus())) {
            ExceptionUtil.castServerException(REFUND_STATUS_IS_NOT_WAITING);
        }
        // 更新
        PayRefundPo updatePayRefundPo = new PayRefundPo();
        updatePayRefundPo.setId(payRefundPo.getId());
        updatePayRefundPo.setStatus(PayRefundStatusEnums.REFUND_SUCCESS.getStatus());
        updatePayRefundPo.setSuccessTime(payRefundResultVo.getSuccessTime());
        updatePayRefundPo.setChannelRefundNo(payRefundResultVo.getChannelRefundNo());
        updatePayRefundPo.setChannelNotifyData(JSONUtil.toJsonStr(payRefundResultVo.getRawData()));
        boolean update = this.update(updatePayRefundPo, PAY_REFUND_PO.STATUS.eq(PayRefundStatusEnums.REFUND_WAITING.getStatus()));
        if (!update) {
            ExceptionUtil.castServiceException(REFUND_STATUS_IS_NOT_WAITING);
        }
        // 更新订单
        payOrderService.updateOrderRefundPrice(payRefundPo.getOrderId(), payRefundPo.getRefundPrice());
    }


    private PayRefundServiceImpl getSelf() {
        return SpringUtil.getBean(getClass());
    }

    private String genChannelRefundNotifyUrl(PayChannelPo channel) {
        return payProperties.getRefundNotifyUrl() + "/" + channel.getChannelCode();
    }


    private PayOrderPo validatePayOrderCanRefund(PayCreateRefundDto payCreateRefundDto) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(PAY_ORDER_PO.APP_ID.eq(payCreateRefundDto.getAppId()))
                .and(PAY_ORDER_PO.MERCHANT_ORDER_ID.eq(payCreateRefundDto.getMerchantOrderId()));
        PayOrderPo payOrderPo = payOrderService.getOne(queryWrapper);
        if (ObjectUtil.isNull(payOrderPo)) {
            ExceptionUtil.castServiceException(PAY_ORDER_NOT_FOUND);
        }
        if (!(PayOrderStatusEnums.isRefund(payOrderPo.getStatus()) || PayOrderStatusEnums.isSuccess(payOrderPo.getStatus()))) {
            ExceptionUtil.castServiceException(PAY_ORDER_REFUND_FAIL_STATUS_ERROR);
        }
        if ((payCreateRefundDto.getPrice() + payOrderPo.getRefundPrice()) > payOrderPo.getPrice()) {
            ExceptionUtil.castServiceException(PAY_ORDER_REFUND_FAIL_PRICE_ERROR);
        }
        if (this.count(QueryWrapper.create().where(PAY_REFUND_PO.ORDER_ID
                .eq(payOrderPo.getId())).and(PAY_REFUND_PO.APP_ID.eq(payCreateRefundDto.getAppId())).and(PAY_REFUND_PO.STATUS.eq(PayRefundStatusEnums.REFUND_WAITING))) > 0) {
            ExceptionUtil.castServiceException(REFUND_IS_REFUNDING);
        }
        return payOrderPo;
    }
}
