package cn.xk.xcode.handler.message;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.balance.core.ILoadBalancer;
import cn.xk.xcode.config.XcodeMessageProperties;
import cn.xk.xcode.core.CommonStatusEnum;
import cn.xk.xcode.core.ThreadPoolExecutorHolder;
import cn.xk.xcode.entity.po.*;
import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.enums.*;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.handler.message.response.SendMessageResponse;
import cn.xk.xcode.limit.RateLimiterHolder;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.*;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cn.xk.xcode.config.GlobalMessageConstants.*;
import static cn.xk.xcode.config.RabbitMqConfiguration.*;
import static cn.xk.xcode.entity.def.MessageChannelAccountTableDef.MESSAGE_CHANNEL_ACCOUNT;
import static cn.xk.xcode.entity.def.MessageChannelTableDef.MESSAGE_CHANNEL;
import static cn.xk.xcode.entity.def.MessageTaskDetailTableDef.MESSAGE_TASK_DETAIL;
import static cn.xk.xcode.exception.GlobalErrorCodeConstants.TOO_MANY_REQUESTS;

/**
 * @Author xuk
 * @Date 2025/3/11 15:40
 * @Version 1.0.0
 * @Description BaseHandler
 **/
@Slf4j
public abstract class AbstractHandler implements IHandler {

    private final static int WAIT_TIME = 30;

    @Resource
    private MessageChannelService messageChannelService;

    @Resource
    private MessageChannelAccountService messageChannelAccountService;


    @Resource
    private MessageChannelParamService messageChannelParamService;

    @Resource
    private ILoadBalancer loadBalancer;

    @Resource
    private RateLimiterHolder rateLimiterHolder;

    @Resource
    private MessageTaskDetailService messageTaskDetailService;

    @Resource
    private MessageTaskService messageTaskService;

    @Resource
    private ThreadPoolExecutorHolder threadPoolExecutorHolder;

    private Map<String, Object> channelAccountParamsValueMap;

    @Resource
    protected StringRedisTemplate stringRedisTemplate;

    @Resource
    protected MessageChannelAccessClientService messageChannelAccessClientService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private XcodeMessageProperties xcodeMessageProperties;

    @Transactional
    @Override
    public CommonResult<SendMessageResponse> sendMessage(MessageTask messageTask) {
        // 1. 负载均衡
        MessageChannelAccountPo messageChannelAccountPo = handleLoadBalance(messageTask);
        if (Objects.isNull(messageChannelAccountPo)) {
            return CommonResult.error(MESSAGE_CHANNEL_ACCOUNT_NOT_EXISTS, null, messageTask.getMsgChannel());
        }
        // 2. 限流
        if (needRateLimit()) {
            if (!rateLimiterHolder.getRateLimiter().rateLimit(messageTask)) {
                return CommonResult.error(TOO_MANY_REQUESTS, null);
            }
        }
        // 3. 验证参数
        populateParamsMap(messageChannelAccountPo);
        try {
            validateChannelAccountParamValue(getChannelAccountParamsValue());
        } catch (Exception e) {
            if (e instanceof ServiceException) {
                ServiceException s = (ServiceException) e;
                return CommonResult.error(s);
            } else if (e instanceof ServerException) {
                ServerException s = (ServerException) e;
                return CommonResult.error(s);
            } else {
                return CommonResult.error(EXEC_MESSAGE_TASK_ERROR, null, e.getMessage());
            }
        }
        MessageTaskPo messageTaskPo = messageTaskService.getById(messageTask.getId());
        messageTaskPo.setAccountId(messageChannelAccountPo.getId());
        messageTaskService.updateById(messageTaskPo);
        // 5. 发消息
        HandlerResult result = null;
        try {
            result = doSendMessage(messageTask, messageChannelAccountPo);
        } catch (InterruptedException e) {
            log.error("发送消息超时,{}", e.getMessage());
            return CommonResult.error(SEND_MESSAGE_TASK_TIMEOUT, null);
        }
        if (Objects.isNull(result)) {
            return CommonResult.error(EXEC_MESSAGE_TASK_ERROR, null, "系统异常");
        }
        List<MessageTaskDetailPo> list = result.getList();
        Integer successCount = result.getSuccessCount();
        MessageChannelAccessClientPo channelAccessClientPo = messageChannelAccessClientService.getById(messageTask.getClientId());
        updateClientAccessCount(channelAccessClientPo, successCount);
        if (successCount == messageTask.getReceiverSet().size() && successCount != 0) {
            messageTaskPo.setStatus(MessageTaskStatusEnum.ALL_SUCCESS.getStatus());
        } else if (successCount == 0) {
            messageTaskPo.setStatus(MessageTaskStatusEnum.FAIL.getStatus());
        } else {
            messageTaskPo.setStatus(MessageTaskStatusEnum.PART_SUCCESS.getStatus());
        }
        messageTaskService.updateById(messageTaskPo);
        // 失败重试
        if (CollectionUtil.isNotEmpty(list)) {
            saveBatchTaskMessageDetails(list);
            // 获取重试消息
            List<MessageTaskDetailPo> retryMessageList = messageTaskDetailService.list(MESSAGE_TASK_DETAIL.TASK_ID.eq(messageTask.getId()).and(MESSAGE_TASK_DETAIL.STATUS.eq("1")));
            if (CollectionUtil.isNotEmpty(retryMessageList)) {
                rabbitTemplate.convertAndSend(RETRY_EXCHANGE_NAME, RETRY_MESSAGE_BINDING_KEY, retryMessageList);
            }
        }
        return buildResponse(result);
    }

    @Override
    public CommonResult<SendMessageResponse> reSendTaskMessage(Long taskId) {
        MessageTaskPo messageTaskPo = messageTaskService.getById(taskId);
        if (ObjectUtil.isNull(messageTaskPo)) {
            return CommonResult.error(MESSAGE_TASK_NOT_EXISTS, null);
        }
        MessageChannelAccessClientPo channelAccessClientPo = messageChannelAccessClientService.getById(messageTaskPo.getClientId());
        MessageChannelAccountPo messageChannelAccountPo = messageChannelAccountService.getById(messageTaskPo.getAccountId());
        if (ObjectUtil.isNull(messageChannelAccountPo)) {
            return CommonResult.error(THE_MESSAGE_CHANNEL_ACCOUNT_NOT_EXISTS, null);
        }
        populateParamsMap(messageChannelAccountPo);
        initChannelSendClient();
        MessageTask messageTask = BeanUtil.toBean(messageTaskPo, MessageTask.class);
        List<String> reSendReceivers = StrUtil.splitTrim(messageTaskPo.getReceivers(), ",");
        messageTask.setReceiverSet(CollectionUtil.convertSet(reSendReceivers, Function.identity()));
        try {
            messageChannelAccessClientService.validateClient(channelAccessClientPo, messageTask.getReceiverSet().size());
        } catch (ServiceException e) {
            return CommonResult.error(e);
        }
        HandlerResult result = null;
        try {
            result = doSendMessage(messageTask, messageChannelAccountPo);
        } catch (InterruptedException e) {
            log.error("发送消息超时,{}", e.getMessage());
            return CommonResult.error(SEND_MESSAGE_TASK_TIMEOUT, null);
        }
        if (Objects.isNull(result)) {
            log.warn("执行结果为空");
            return CommonResult.error(EXEC_MESSAGE_TASK_ERROR, null, "系统异常");
        }
        List<MessageTaskDetailPo> list = result.getList();
        Integer successCount = result.getSuccessCount();
        updateClientAccessCount(channelAccessClientPo, successCount);
        if (successCount == messageTask.getReceiverSet().size() && successCount != 0) {
            messageTaskPo.setStatus(MessageTaskStatusEnum.ALL_SUCCESS.getStatus());
        } else if (successCount == 0) {
            messageTaskPo.setStatus(MessageTaskStatusEnum.FAIL.getStatus());
        } else {
            messageTaskPo.setStatus(MessageTaskStatusEnum.PART_SUCCESS.getStatus());
        }
        if (!(MessageTaskStatusEnum.CANCEL.getStatus().equals(messageTaskPo.getStatus()) ||
                (MessageTaskStatusEnum.PAUSE.getStatus().equals(messageTaskPo.getStatus())))) {
            messageTaskService.updateById(messageTaskPo);
        }
        List<MessageTaskDetailPo> list1 = messageTaskDetailService.list(MESSAGE_TASK_DETAIL.TASK_ID.eq(taskId));
        Map<String, MessageTaskDetailPo> map = new HashMap<>();
        list.forEach(messageTaskDetailPo -> map.put(messageTaskDetailPo.getReceiver(), messageTaskDetailPo));
        List<MessageTaskDetailPo> list2 = new ArrayList<>();
        for (MessageTaskDetailPo messageTaskDetailPo : list1) {
            MessageTaskDetailPo messageTaskDetailPo1 = map.get(messageTaskDetailPo.getReceiver());
            if (ObjectUtil.isNotNull(messageTaskDetailPo1)) {
                messageTaskDetailPo.setStatus(messageTaskDetailPo1.getStatus());
                messageTaskDetailPo.setFailMsg(messageTaskDetailPo1.getFailMsg());
                messageTaskDetailPo.setSuccessTime(messageTaskDetailPo1.getSuccessTime());
                messageTaskDetailPo.setExecTime(messageTaskDetailPo1.getExecTime());
                list2.add(messageTaskDetailPo);
            }
        }
        if (CollectionUtil.isNotEmpty(list2)) {
            messageTaskDetailService.updateBatch(list2);
        }
        return buildResponse(result);
    }

    private CommonResult<SendMessageResponse> buildResponse(HandlerResult result) {
        List<MessageTaskDetailPo> list = result.getList();
        Integer successCount = result.getSuccessCount();
        SendMessageResponse response = new SendMessageResponse();
        response.setSuccessCount(successCount);
        response.setFailCount(list.size() - successCount);
        List<SendMessageResponse.FailMessageDetail> failMessageDetailList = list.stream().filter(m -> CommonStatusEnum.isDisable(m.getStatus()))
                .map(m -> {
                    SendMessageResponse.FailMessageDetail failMessageDetail = new SendMessageResponse.FailMessageDetail();
                    failMessageDetail.setReceiver(m.getReceiver());
                    failMessageDetail.setFailMsg(m.getFailMsg());
                    return failMessageDetail;
                }).collect(Collectors.toList());
        response.setFailMessageDetailList(failMessageDetailList);
        return CommonResult.success(response);
    }

    @Override
    public CommonResult<SendMessageResponse> retrySendMessage(List<MessageTaskDetailPo> messageTaskDetailPoList) {
        MessageTaskPo messageTaskPo = messageTaskService.getById(messageTaskDetailPoList.get(0).getTaskId());
        if (ObjectUtil.isNull(messageTaskPo)) {
            CommonResult.error(MESSAGE_TASK_NOT_EXISTS, null);
        }
        MessageChannelAccountPo messageChannelAccountPo = messageChannelAccountService.getById(messageTaskPo.getAccountId());
        if (ObjectUtil.isNull(messageChannelAccountPo)) {
            CommonResult.error(THE_MESSAGE_CHANNEL_ACCOUNT_NOT_EXISTS, null);
        }
        populateParamsMap(messageChannelAccountPo);
        initChannelSendClient();
        MessageTask messageTask = BeanUtil.toBean(messageTaskPo, MessageTask.class);
        messageTask.setReceiverSet(CollectionUtil.convertSet(messageTaskDetailPoList, MessageTaskDetailPo::getReceiver));
        HandlerResult result = null;
        try {
            result = doSendMessage(messageTask, messageChannelAccountPo);
        } catch (InterruptedException e) {
            log.error("发送消息超时,{}", e.getMessage());
            CommonResult.error(SEND_MESSAGE_TASK_TIMEOUT, null);
        }
        if (ObjectUtil.isNull(result)) {
            log.warn("执行结果为空");
            return CommonResult.error(EXEC_MESSAGE_TASK_ERROR, null, "系统异常");
        }
        List<MessageTaskDetailPo> list = result.getList();
        Integer successCount = result.getSuccessCount();
        if (successCount == messageTask.getReceiverSet().size()) {
            messageTaskPo.setStatus(MessageTaskStatusEnum.ALL_SUCCESS.getStatus());
            messageTaskService.updateById(messageTaskPo);
        } else {
            if (successCount > 0) {
                // 成功了一部分
                messageTaskPo.setStatus(MessageTaskStatusEnum.PART_SUCCESS.getStatus());
                messageTaskService.updateById(messageTaskPo);
            }
        }
        // 更新
        Map<String, MessageTaskDetailPo> map = new HashMap<>();
        list.forEach(messageTaskDetailPo -> map.put(messageTaskDetailPo.getReceiver(), messageTaskDetailPo));
        List<MessageTaskDetailPo> list2 = new ArrayList<>();
        List<MessageTaskDetailPo> retryList = new ArrayList<>();
        for (MessageTaskDetailPo messageTaskDetailPo : messageTaskDetailPoList) {
            MessageTaskDetailPo messageTaskDetailPo1 = map.get(messageTaskDetailPo.getReceiver());
            if (ObjectUtil.isNotNull(messageTaskDetailPo1)) {
                messageTaskDetailPo.setStatus(messageTaskDetailPo1.getStatus());
                messageTaskDetailPo.setRetryTimes(messageTaskDetailPo.getRetryTimes() + 1);
                messageTaskDetailPo.setFailMsg(messageTaskDetailPo1.getFailMsg());
                messageTaskDetailPo.setSuccessTime(messageTaskDetailPo1.getSuccessTime());
                messageTaskDetailPo.setExecTime(messageTaskDetailPo1.getExecTime());
                if (CommonStatusEnum.isDisable(messageTaskDetailPo.getStatus()) && messageTaskDetailPo.getRetryTimes() < xcodeMessageProperties.getFailRetryTimes()) {
                    retryList.add(messageTaskDetailPo);
                }
                list2.add(messageTaskDetailPo);
            }
        }
        if (CollectionUtil.isNotEmpty(list2)) {
            for (MessageTaskDetailPo messageTaskDetailPo : list2) {
                messageTaskDetailService.getById(messageTaskDetailPo);
            }
        //    messageTaskDetailService.updateBatch(list2);
        }
        // 重试补偿
        if (CollectionUtil.isNotEmpty(retryList)) {
            rabbitTemplate.convertAndSend(RETRY_EXCHANGE_NAME, RETRY_MESSAGE_BINDING_KEY, retryList);
        }
        return buildResponse(result);
    }

    private void updateClientAccessCount(MessageChannelAccessClientPo channelAccessClientPo, int successCount) {
        channelAccessClientPo.setRestCount(channelAccessClientPo.getRestCount() - successCount);
        channelAccessClientPo.setUsedCount(channelAccessClientPo.getUsedCount() + successCount);
        messageChannelAccessClientService.updateById(channelAccessClientPo);
    }


    @Override
    public CommonResult<SendMessageResponse> reSendSingleTask(Long taskDetailId) {
        MessageTaskDetailPo messageTaskDetailPo = messageTaskDetailService.getById(taskDetailId);
        if (ObjectUtil.isNull(messageTaskDetailPo)) {
            CommonResult.error(MESSAGE_TASK_DETAIL_NOT_EXISTS, null);
        }
        messageTaskDetailPo.setExecTime(LocalDateTime.now());
        MessageTaskPo messageTaskPo = messageTaskService.getById(messageTaskDetailPo.getTaskId());
        if (ObjectUtil.isNull(messageTaskPo)) {
            CommonResult.error(MESSAGE_TASK_NOT_EXISTS, null);
        }
        MessageChannelAccessClientPo channelAccessClientPo = messageChannelAccessClientService.getById(messageTaskPo.getClientId());
        MessageChannelAccountPo messageChannelAccountPo = messageChannelAccountService.getById(messageTaskPo.getAccountId());
        if (ObjectUtil.isNull(messageChannelAccountPo)) {
            CommonResult.error(THE_MESSAGE_CHANNEL_ACCOUNT_NOT_EXISTS, null);
        }
        populateParamsMap(messageChannelAccountPo);
        initChannelSendClient();
        MessageTask messageTask = BeanUtil.toBean(messageTaskPo, MessageTask.class);
        SingeSendMessageResult singeSendMessageResult = doSendMessage(messageTaskDetailPo.getReceiver(), messageTask, messageChannelAccountPo);
        // 1. 更新消息任务详情
        HandlerResult handlerResult = new HandlerResult();
        if (singeSendMessageResult.isSuccess()) {
            messageTaskDetailPo.setStatus("0");
            handlerResult.setSuccessCount(1);
            messageTaskDetailPo.setSuccessTime(LocalDateTime.now());
            handlerResult.setList(Collections.singletonList(messageTaskDetailPo));
            updateClientAccessCount(channelAccessClientPo, 1);
        } else {
            messageTaskDetailPo.setStatus("1");
            messageTaskDetailPo.setFailMsg(singeSendMessageResult.getFailMsg());
            handlerResult.setList(Collections.singletonList(messageTaskDetailPo));
            handlerResult.setSuccessCount(0);
        }
        messageTaskDetailService.updateById(messageTaskDetailPo);
        // 2. 更新消息任务
        long count = messageTaskDetailService.count(MESSAGE_TASK_DETAIL.TASK_ID.eq(messageTaskDetailPo.getTaskId()));
        long successCount = messageTaskDetailService.count(MESSAGE_TASK_DETAIL.TASK_ID.eq(messageTaskDetailPo.getTaskId()).and(MESSAGE_TASK_DETAIL.STATUS.eq("0")));
        if (count == successCount) {
            messageTaskPo.setStatus(MessageTaskStatusEnum.ALL_SUCCESS.getStatus());
        } else {
            if (successCount == 0) {
                messageTaskPo.setStatus(MessageTaskStatusEnum.FAIL.getStatus());
            } else {
                messageTaskPo.setStatus(MessageTaskStatusEnum.PART_SUCCESS.getStatus());
            }
            if (!(MessageTaskStatusEnum.CANCEL.getStatus().equals(messageTaskPo.getStatus()) ||
                    (MessageTaskStatusEnum.PAUSE.getStatus().equals(messageTaskPo.getStatus())))) {
                messageTaskService.updateById(messageTaskPo);
            }
        }
        return buildResponse(handlerResult);
    }

    private void saveBatchTaskMessageDetails(List<MessageTaskDetailPo> list) {
        messageTaskDetailService.saveBatch(list);
    }

    public abstract void validateChannelAccountParamValue(Map<String, Object> channelAccountParamsValueMap);

    @Override
    public HandlerResult doSendMessage(MessageTask messageTask, MessageChannelAccountPo messageChannelAccountPo) throws InterruptedException {
        HandlerResult handlerResult = new HandlerResult();
        initChannelSendClient();
        ExecutorService executorService = threadPoolExecutorHolder.routeThreadPool(channelCode());
        Set<String> receiverSet = messageTask.getReceiverSet();
        CountDownLatch countDownLatch = new CountDownLatch(receiverSet.size());
        CopyOnWriteArrayList<SingeSendMessageResult> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        receiverSet.forEach(receiver -> {
            executorService.execute(() -> {
                //  这里执行真正发消息的逻辑
                SingeSendMessageResult singeSendMessageResult = doSendMessage(receiver, messageTask, messageChannelAccountPo);
                copyOnWriteArrayList.add(singeSendMessageResult);
                countDownLatch.countDown();
            });
        });
        countDownLatch.await(WAIT_TIME, TimeUnit.MINUTES);
        int successCount = 0;
        List<MessageTaskDetailPo> list = new ArrayList<>();
        for (SingeSendMessageResult singeSendMessageResult : copyOnWriteArrayList) {
            MessageTaskDetailPo messageTaskDetailPo = new MessageTaskDetailPo();
            messageTaskDetailPo.setTaskId(messageTask.getId());
            messageTaskDetailPo.setReceiver(singeSendMessageResult.getReceiver());
            messageTaskDetailPo.setExecTime(singeSendMessageResult.getExecTime());
            messageTaskDetailPo.setRetryTimes(0);
            if (singeSendMessageResult.isSuccess()) {
                successCount++;
                messageTaskDetailPo.setStatus("0");
                messageTaskDetailPo.setSuccessTime(singeSendMessageResult.getSuccessTime());

            } else {
                messageTaskDetailPo.setStatus("1");
                messageTaskDetailPo.setFailMsg(singeSendMessageResult.getFailMsg());
            }
            list.add(messageTaskDetailPo);

        }
        handlerResult.setSuccessCount(successCount);
        handlerResult.setList(list);
        return handlerResult;
    }

    protected abstract void initChannelSendClient();

    protected abstract SingeSendMessageResult doSendMessage(String receiver, MessageTask messageTask, MessageChannelAccountPo messageChannelAccountPo);

    public MessageChannelAccountPo handleLoadBalance(MessageTask messageTask) {
        MessageChannelPo messageChannelPo = messageChannelService.getOne(MESSAGE_CHANNEL.CODE.eq(messageTask.getMsgChannel()));
        if (Objects.isNull(messageChannelPo)) {
            ExceptionUtil.castServerException(CHANNEL_NOT_EXISTS);
        }
        String supportLoadBalance = messageChannelPo.getSupportLoadBalance();
        if ("1".equals(supportLoadBalance)) {
            // 不支持负载 必然有对应的id
            Integer accountId = messageTask.getAccountId();
            if (ObjectUtil.isNull(accountId)) {
                ExceptionUtil.castServiceException(MESSAGE_TASK_ACCOUNT_NOT_DEFINED);
            }
            return messageChannelAccountService.getById(accountId);
        } else {
            // 支持负载均衡
            List<MessageChannelAccountPo> messageChannelAccountPoList = messageChannelAccountService.list(MESSAGE_CHANNEL_ACCOUNT.CHANNEL_ID.eq(messageChannelPo.getId()).and(MESSAGE_CHANNEL_ACCOUNT.STATUS.eq("0")));
            MessageChannelAccountPo channelAccountPo = loadBalancer.choose(messageChannelAccountPoList);
            if (Objects.nonNull(channelAccountPo)) {
                messageTask.setClientId(channelAccountPo.getId());
            }
            return channelAccountPo;
        }
    }

    protected void saveWithDrawInfo(Integer taskDetailId, String taskId) {
        stringRedisTemplate.opsForValue().set(channelCode() + "_" + taskDetailId, taskId, 1, TimeUnit.DAYS);
    }

    protected String getWithDrawInfo(Integer taskDetailId) {
        return stringRedisTemplate.opsForValue().get(channelCode() + "_" + taskDetailId);
    }

    @Override
    public void withDrawMessage(List<Integer> messageTaskDetailIds) {
        List<WithdrawResult> withdrawResults = doWithDrawMessage(messageTaskDetailIds);
        if (CollectionUtil.isNotEmpty(withdrawResults)) {
            List<MessageTaskDetailPo> list = new ArrayList<>();
            for (WithdrawResult withdrawResult : withdrawResults) {
                MessageTaskDetailPo messageTaskDetailPo = messageTaskDetailService.getById(withdrawResult.getTaskDetailId());
                if (withdrawResult.isSuccess()) {
                    messageTaskDetailPo.setStatus("2");
                    list.add(messageTaskDetailPo);
                } else {
                    // 重试
                    messageTaskDetailPo.setStatus("3");
                }
            }
            if (CollectionUtil.isNotEmpty(list)) {
                saveBatchTaskMessageDetails(list);
            }
        }
    }

    @Override
    public List<WithdrawResult> doWithDrawMessage(List<Integer> messageTaskDetailIds) {
        throw new UnsupportedOperationException("渠道" + channelCode() + "不支持撤回");
    }

    private void populateParamsMap(MessageChannelAccountPo messageChannelAccountPo){
        channelAccountParamsValueMap = messageChannelParamService.getChannelParamsAndValueForAccount(messageChannelAccountPo.getId());
    }

    protected Map<String, Object> getChannelAccountParamsValue() {
        return channelAccountParamsValueMap;
    }

}
