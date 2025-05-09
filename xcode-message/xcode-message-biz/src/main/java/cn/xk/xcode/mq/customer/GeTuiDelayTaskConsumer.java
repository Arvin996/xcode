//package cn.xk.xcode.mq.customer;
//
//import cn.hutool.http.HttpRequest;
//import cn.xk.xcode.constants.MessageChannelUrlConstant;
//import cn.xk.xcode.core.ThreadPoolExecutorHolder;
//import cn.xk.xcode.entity.MessageChannelEnums;
//import cn.xk.xcode.entity.account.other.GeTuiAccount;
//import cn.xk.xcode.entity.po.MessageTaskDetailPo;
//import cn.xk.xcode.entity.po.MessageTaskPo;
//import cn.xk.xcode.enums.MessageTaskStatusEnum;
//import cn.xk.xcode.mq.GeTuiDelayTaskDetail;
//import cn.xk.xcode.service.MessageTaskDetailService;
//import cn.xk.xcode.service.MessageTaskService;
//import cn.xk.xcode.utils.AccessTokenUtil;
//import com.alibaba.fastjson2.JSON;
//import com.alibaba.fastjson2.JSONObject;
//import com.xxl.mq.client.consumer.IMqConsumer;
//import com.xxl.mq.client.consumer.MqResult;
//import com.xxl.mq.client.consumer.annotation.MqConsumer;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * @Author xuk
// * @Date 2025/3/14 10:46
// * @Version 1.0.0
// * @Description GeTuiDelayTaskConsumer
// **/
//@Component
//@MqConsumer(topic = "geTuiDelayTopic")
//public class GeTuiDelayTaskConsumer implements IMqConsumer {
//
//    @Resource
//    private ThreadPoolExecutorHolder threadPoolExecutorHolder;
//
//    @Resource
//    private MessageTaskService messageTaskService;
//
//    @Resource
//    private MessageTaskDetailService messageTaskDetailService;
//
//    @Override
//    public MqResult consume(String s) throws Exception {
//        GeTuiDelayTaskDetail geTuiDelayTaskDetail = JSON.parseObject(s, GeTuiDelayTaskDetail.class);
//        String url = MessageChannelUrlConstant.GE_TUI_BASE_URL + MessageChannelUrlConstant.GE_TUI_TASK_DETAIL;
//        Set<String> cids = geTuiDelayTaskDetail.getCids();
//        ExecutorService executorService = threadPoolExecutorHolder.routeThreadPool(MessageChannelEnums.GE_TUI.getChannel());
//        CountDownLatch countDownLatch = new CountDownLatch(cids.size());
//        String accessToken = AccessTokenUtil.getGeTuiAccessToken(geTuiDelayTaskDetail.getMessageChannelAccountPo(), JSON.parseObject(geTuiDelayTaskDetail.getMessageChannelAccountPo().getChannelConfig(), GeTuiAccount.class), false);
//        AtomicInteger successCount = new AtomicInteger(0);
//        List<MessageTaskDetailPo> list = new ArrayList<>();
//        for (String cid : cids) {
//            executorService.execute(() -> {
//                MessageTaskDetailPo messageTaskDetailPo = new MessageTaskDetailPo();
//                messageTaskDetailPo.setTaskId(geTuiDelayTaskDetail.getMessageTaskId());
//                messageTaskDetailPo.setReceiver(cid);
//                messageTaskDetailPo.setExecTime(geTuiDelayTaskDetail.getExecTime());
//                messageTaskDetailPo.setRetryTimes(0);
//                try {
//                    String body = HttpRequest.get(url + geTuiDelayTaskDetail.getTaskId() + "/" + cid).header("token", accessToken).timeout(2000).execute().body();
//                    JSONObject jsonObject = JSON.parseObject(body);
//                    if (jsonObject.getIntValue("code") == 0) {
//                        successCount.getAndIncrement();
//                        messageTaskDetailPo.setStatus("0");
//                        messageTaskDetailPo.setSuccessTime(LocalDateTime.now());
//                        messageTaskDetailPo.setFailMsg(null);
//                    } else {
//                        messageTaskDetailPo.setStatus("1");
//                        messageTaskDetailPo.setFailMsg(jsonObject.getString("msg"));
//                    }
//                } catch (Exception e) {
//                    messageTaskDetailPo.setStatus("1");
//                    messageTaskDetailPo.setFailMsg(e.getMessage());
//                } finally {
//                    countDownLatch.countDown();
//                    list.add(messageTaskDetailPo);
//                }
//            });
//        }
//        countDownLatch.await();
//        MessageTaskPo messageTaskPo = messageTaskService.getById(geTuiDelayTaskDetail.getMessageTaskId());
//        int count = successCount.get();
//        if (count == cids.size()) {
//            messageTaskPo.setStatus(MessageTaskStatusEnum.ALL_SUCCESS.getStatus());
//        } else if (0 == count) {
//            messageTaskPo.setStatus(MessageTaskStatusEnum.FAIL.getStatus());
//        } else {
//            messageTaskPo.setStatus(MessageTaskStatusEnum.PART_SUCCESS.getStatus());
//        }
//        messageTaskDetailService.saveBatch(list);
//        return MqResult.SUCCESS;
//    }
//}
