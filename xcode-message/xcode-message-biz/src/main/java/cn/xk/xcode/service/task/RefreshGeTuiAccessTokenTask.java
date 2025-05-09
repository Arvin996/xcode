package cn.xk.xcode.service.task;

import cn.xk.xcode.annotation.AutoRegisterXxlJob;
import cn.xk.xcode.core.factory.ThreadPoolProduceDecider;
import cn.xk.xcode.core.factory.ThreadPoolTypeEnums;
import cn.xk.xcode.entity.account.other.GeTuiAccount;
import cn.xk.xcode.entity.po.MessageChannelAccountPo;
import cn.xk.xcode.enums.ChannelTypeEnum;
import cn.xk.xcode.service.MessageChannelAccountService;
import cn.xk.xcode.utils.AccessTokenUtil;
import com.alibaba.fastjson2.JSON;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static cn.xk.xcode.entity.def.MessageChannelAccountTableDef.MESSAGE_CHANNEL_ACCOUNT_PO;
import static cn.xk.xcode.utils.AccessTokenUtil.GE_TUI_ACCESS_TOKEN_EXPIRE;
import static cn.xk.xcode.utils.AccessTokenUtil.GE_TUI_ACCESS_TOKEN_PREFIX;

/**
 * @Author xuk
 * @Date 2025/3/11 8:22
 * @Version 1.0.0
 * @Description RefreshGeTuiAccessTokenTask
 **/
@Component
public class RefreshGeTuiAccessTokenTask {

    @Resource
    private MessageChannelAccountService messageChannelAccountService;

    @Resource
    private ThreadPoolProduceDecider threadPoolProduceDecider;

    @Resource
    private StringRedisTemplate redisTemplate;

    @AutoRegisterXxlJob(executorHandler = "refreshGeTuiAccessTokenTask",
            cron = "0 0/1 * * * ?",
            author = "arvin",
            jobDesc = "定时刷新getui的token",
            triggerStatus = 1)
    public void refreshGeTuiAccessTokenTask() {
        threadPoolProduceDecider.decide(ThreadPoolTypeEnums.SINGLE, true, "refreshGeTuiAccessTokenTask").execute(() -> {
            List<MessageChannelAccountPo> list = messageChannelAccountService.list(MESSAGE_CHANNEL_ACCOUNT_PO.CHANNEL_CODE.eq(ChannelTypeEnum.GE_TUI.getCode()).and(MESSAGE_CHANNEL_ACCOUNT_PO.STATUS.eq(0)));
            for (MessageChannelAccountPo messageChannelAccountPo : list) {
                GeTuiAccount account = JSON.parseObject(messageChannelAccountPo.getChannelCode(), GeTuiAccount.class);
                String accessToken = AccessTokenUtil.getGeTuiAccessToken(messageChannelAccountPo, account, true);
                if (StringUtils.hasText(accessToken)) {
                    redisTemplate.opsForValue().set(GE_TUI_ACCESS_TOKEN_PREFIX + messageChannelAccountPo.getId(), accessToken, GE_TUI_ACCESS_TOKEN_EXPIRE, TimeUnit.SECONDS);
                }
            }

        });
    }

}
