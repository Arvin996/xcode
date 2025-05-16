//package cn.xk.xcode.service.task;
//
//import cn.xk.xcode.annotation.AutoRegisterXxlJob;
//import cn.xk.xcode.core.factory.ThreadPoolProduceDecider;
//import cn.xk.xcode.core.factory.ThreadPoolTypeEnums;
//import cn.xk.xcode.entity.discard.account.other.DingDingWorkNoticeAccount;
//import cn.xk.xcode.entity.po.MessageChannelAccountPo;
//import cn.xk.xcode.enums.ChannelTypeEnum;
//import cn.xk.xcode.service.MessageChannelAccountService;
//import cn.xk.xcode.utils.AccessTokenUtil;
//import com.alibaba.fastjson2.JSON;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import javax.annotation.Resource;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import static cn.xk.xcode.entity.def.MessageChannelAccountTableDef.MESSAGE_CHANNEL_ACCOUNT_PO;
//import static cn.xk.xcode.utils.AccessTokenUtil.*;
//
/// **
// * @Author xuk
// * @Date 2025/3/11 9:23
// * @Version 1.0.0
// * @Description RefreshDingDingAccessTokenTask
// **/
//@Component
//public class RefreshDingDingAccessTokenTask {
//
//    @Resource
//    private MessageChannelAccountService messageChannelAccountService;
//
//    @Resource
//    private ThreadPoolProduceDecider threadPoolProduceDecider;
//
//    @Resource
//    private StringRedisTemplate redisTemplate;
//
//    @AutoRegisterXxlJob(executorHandler = "refreshDingDingAccessTokenTask",
//            cron = "0 0/1 * * * ?",
//            author = "arvin",
//            jobDesc = "定时刷新ding ding的token",
//            triggerStatus = 1)
//    public void refreshDingDingAccessTokenTask() {
//        threadPoolProduceDecider.decide(ThreadPoolTypeEnums.SINGLE, true, "refreshDingDingAccessTokenTask").execute(() -> {
//            List<MessageChannelAccountPo> list = messageChannelAccountService.list(MESSAGE_CHANNEL_ACCOUNT_PO.CHANNEL_CODE.eq(ChannelTypeEnum.DING_DING_WORK_NOTICE.getCode()).and(MESSAGE_CHANNEL_ACCOUNT_PO.STATUS.eq(0)));
//            for (MessageChannelAccountPo messageChannelAccountPo : list) {
//                DingDingWorkNoticeAccount account = JSON.parseObject(messageChannelAccountPo.getChannelCode(), DingDingWorkNoticeAccount.class);
//                String accessToken = AccessTokenUtil.getDingDingAccessToken(messageChannelAccountPo, account, true);
//                if (StringUtils.hasText(accessToken)) {
//                    redisTemplate.opsForValue().set(DING_DING_ACCESS_TOEN_PREFIX + messageChannelAccountPo.getId(), accessToken, DING_DING_ACCESS_TOKEN_EXPIRE, TimeUnit.SECONDS);
//                }
//            }
//
//        });
//    }
//}
