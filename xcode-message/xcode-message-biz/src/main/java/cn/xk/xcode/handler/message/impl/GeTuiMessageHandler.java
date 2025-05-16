package cn.xk.xcode.handler.message.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.xk.xcode.constants.MessageChannelUrlConstant;
import cn.xk.xcode.core.annotation.Handler;
import cn.xk.xcode.entity.discard.account.other.GeTuiAccount;
import cn.xk.xcode.entity.discard.content.GeTuiContentModel;
import cn.xk.xcode.entity.po.MessageChannelAccountPo;
import cn.xk.xcode.entity.po.MessageTaskDetailPo;
import cn.xk.xcode.entity.discard.getui.GeTuiBatchSendParam;
import cn.xk.xcode.entity.discard.getui.GeTuiBatchSendResult;
import cn.xk.xcode.entity.discard.getui.GeTuiSendResult;
import cn.xk.xcode.entity.discard.getui.GeTuiSingleSendParam;
import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.enums.ChannelTypeEnum;
import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.handler.message.AbstractHandler;
import cn.xk.xcode.handler.message.HandlerResult;
import cn.xk.xcode.mq.XxlMqTemplate;
import cn.xk.xcode.utils.AccessTokenUtil;
import cn.xk.xcode.utils.collections.CollectionUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static cn.xk.xcode.config.GlobalMessageConstants.GE_TUI_CREATE_TASK_FAILED;
import static cn.xk.xcode.constants.MessageChannelUrlConstant.GE_TUI_BATCH_PUSH_CREATE_TASK_PATH;
import static cn.xk.xcode.constants.MessageChannelUrlConstant.GE_TUI_SINGLE_PUSH_PATH;

/**
 * @Author xuk
 * @Date 2025/3/14 9:20
 * @Version 1.0.0
 * @Description GeTuiMessageHandler
 **/
@Handler
@Slf4j
public class GeTuiMessageHandler extends AbstractHandler {

    @Resource
    private XxlMqTemplate xxlMqTemplate;

    private static final Integer DELAY_TIME = 1;

    @Override
    public HandlerResult doSendMessage(MessageTask messageTask, MessageChannelAccountPo messageChannelAccountPo) {
        GeTuiAccount geTuiAccount = JSON.parseObject(messageChannelAccountPo.getChannelConfig(), GeTuiAccount.class);
        String accessToken = AccessTokenUtil.getGeTuiAccessToken(messageChannelAccountPo, geTuiAccount, false);
        String messageContent = messageTask.getMessageContent();
        GeTuiContentModel geTuiContentModel = JSON.parseObject(messageContent, GeTuiContentModel.class);
        String result;
        Set<String> receiverSet = messageTask.getReceiverSet();
        if (receiverSet.size() == 1) {
            result = singleSend(messageTask, accessToken, geTuiAccount, geTuiContentModel);
            GeTuiSendResult geTuiSendResult = JSON.parseObject(result, GeTuiSendResult.class);
            MessageTaskDetailPo messageTaskDetailPo = new MessageTaskDetailPo();
            messageTaskDetailPo.setTaskId(messageTask.getId());
            messageTaskDetailPo.setReceiver(receiverSet.iterator().next());
            messageTaskDetailPo.setExecTime(LocalDateTime.now());
            messageTaskDetailPo.setRetryTimes(0);
            if (geTuiSendResult.getCode() == 0) {
                messageTaskDetailPo.setStatus("0");
                messageTaskDetailPo.setSuccessTime(LocalDateTime.now());
                messageTaskDetailPo.setFailMsg(null);
                return HandlerResult.builder().successCount(1).list(CollectionUtil.createSingleList(messageTaskDetailPo)).build();
            } else {
                messageTaskDetailPo.setStatus("1");
                messageTaskDetailPo.setFailMsg(geTuiSendResult.getMsg());
                messageTaskDetailPo.setSuccessTime(null);
                return HandlerResult.builder().successCount(0).list(CollectionUtil.createSingleList(messageTaskDetailPo)).build();
            }
        } else {
            String taskId = createTaskId(accessToken, geTuiAccount, geTuiContentModel);
            result = batchSend(taskId, messageTask, accessToken, geTuiAccount, geTuiContentModel);
            log.info("GeTui 批量推送结果 {}", result);
            //    GeTuiDelayTaskDetail geTuiDelayTaskDetail = GeTuiDelayTaskDetail.builder().taskId(taskId).cids(messageTask.getReceiverSet()).build();
            //    xxlMqTemplate.sendMessage(MqMessageTopicEnum.GE_TUI_DELAY_TOPIC.getTopic(), JSON.toJSONString(geTuiDelayTaskDetail), DateUtil.addMinutes(new Date(), DELAY_TIME));
            GeTuiBatchSendResult geTuiBatchSendResult = JSON.parseObject(result, GeTuiBatchSendResult.class);
            JSONObject data = geTuiBatchSendResult.getData();
            JSONArray jsonArray = data.getJSONArray(taskId);
            int successCount = 0;
            List<MessageTaskDetailPo> list = new ArrayList<>();
            for (Object object : jsonArray) {
                GeTuiBatchSendResult.SingleResult singleResult = JSON.parseObject(object.toString(), GeTuiBatchSendResult.SingleResult.class);
                MessageTaskDetailPo messageTaskDetailPo = new MessageTaskDetailPo();
                messageTaskDetailPo.setTaskId(messageTask.getId());
                messageTaskDetailPo.setReceiver(singleResult.getCid());
                messageTaskDetailPo.setExecTime(LocalDateTime.now());
                messageTaskDetailPo.setRetryTimes(0);
                String status = singleResult.getStatus();
                if (status.startsWith("successed")){
                    successCount++;
                    messageTaskDetailPo.setStatus("0");
                    messageTaskDetailPo.setSuccessTime(LocalDateTime.now());
                    messageTaskDetailPo.setFailMsg(null);
                }else {
                    messageTaskDetailPo.setStatus("1");
                    messageTaskDetailPo.setFailMsg(status);
                }
                list.add(messageTaskDetailPo);
            }
            return HandlerResult.builder().successCount(successCount).list(list).build();
        }
    }

    private String batchSend(String taskId, MessageTask messageTask, String accessToken, GeTuiAccount geTuiAccount, GeTuiContentModel geTuiContentModel) {
        String url = MessageChannelUrlConstant.GE_TUI_BASE_URL + geTuiAccount.getAppId() + MessageChannelUrlConstant.GE_TUI_BATCH_PUSH_PATH;
        GeTuiBatchSendParam param = GeTuiBatchSendParam
                .builder()
                .taskId(taskId)
                .isAsync(false)
                .audience(GeTuiBatchSendParam.AudienceVO.builder().cid(messageTask.getReceiverSet()).build())
                .build();
        return HttpRequest.post(url).header(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue())
                .header("token", accessToken)
                .body(JSON.toJSONString(param))
                .timeout(2000)
                .execute().body();
    }

    private String createTaskId(String accessToken, GeTuiAccount geTuiAccount, GeTuiContentModel geTuiContentModel) {
        String url = MessageChannelUrlConstant.GE_TUI_BASE_URL + geTuiAccount.getAppId() + GE_TUI_BATCH_PUSH_CREATE_TASK_PATH;
        GeTuiSingleSendParam param = GeTuiSingleSendParam
                .builder()
                .requestId(IdUtil.getSnowflakeNextIdStr())
                .pushMessage(GeTuiSingleSendParam.PushMessageVO.builder()
                        .notification(GeTuiSingleSendParam.PushMessageVO.NotificationVO.builder()
                                .title(geTuiContentModel.getTitle()).body(geTuiContentModel.getContent()).clickType("startapp").build())
                        .build())
                .build();
        String taskId = "";
        try {
            String body = HttpRequest.post(url).header(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue())
                    .header("token", accessToken)
                    .body(JSON.toJSONString(param))
                    .timeout(2000)
                    .execute().body();

            taskId = JSON.parseObject(body, GeTuiSendResult.class).getData().getString("taskId");
            return taskId;
        } catch (Exception e) {
            log.error("GeTui 创建任务id失败 {}", e.getMessage());
            throw new ServerException(GE_TUI_CREATE_TASK_FAILED);
        }
    }


    private String singleSend(MessageTask messageTask, String accessToken, GeTuiAccount geTuiAccount, GeTuiContentModel geTuiContentModel) {
        String url = MessageChannelUrlConstant.GE_TUI_BASE_URL + geTuiAccount.getAppId() + GE_TUI_SINGLE_PUSH_PATH;
        GeTuiSingleSendParam param = GeTuiSingleSendParam
                .builder()
                .requestId(IdUtil.getSnowflakeNextIdStr())
                .pushMessage(GeTuiSingleSendParam.PushMessageVO.builder()
                        .notification(GeTuiSingleSendParam.PushMessageVO.NotificationVO.builder()
                                .title(geTuiContentModel.getTitle()).body(geTuiContentModel.getContent()).clickType("startapp").build())
                        .build())
                .audience(GeTuiSingleSendParam.AudienceVO.builder().cid(messageTask.getReceiverSet()).build())
                .build();
        return HttpRequest.post(url).header(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue())
                .header("token", accessToken)
                .body(JSON.toJSONString(param))
                .timeout(2000)
                .execute().body();

    }

    @Override
    public String channelCode() {
        return ChannelTypeEnum.GE_TUI.getCode();
    }

    @Override
    public boolean needRateLimit() {
        return false;
    }
}
