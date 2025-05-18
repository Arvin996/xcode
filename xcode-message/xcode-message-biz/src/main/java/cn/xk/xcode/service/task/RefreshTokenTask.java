package cn.xk.xcode.service.task;

import cn.xk.xcode.annotation.AutoRegisterXxlJob;
import cn.xk.xcode.core.factory.ThreadPoolProduceDecider;
import cn.xk.xcode.core.factory.ThreadPoolTypeEnums;
import cn.xk.xcode.entity.po.MessageChannelAccountPo;
import cn.xk.xcode.enums.ChannelTypeEnum;
import cn.xk.xcode.enums.MessageChannelEnum;
import cn.xk.xcode.service.MessageChannelAccountService;
import cn.xk.xcode.service.MessageChannelParamService;
import cn.xk.xcode.utils.AccessTokenUtil;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.collections.MapUtil;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static cn.xk.xcode.entity.def.MessageChannelAccountTableDef.MESSAGE_CHANNEL_ACCOUNT;
import static cn.xk.xcode.entity.def.MessageChannelTableDef.MESSAGE_CHANNEL;

/**
 * @author xukai
 * @version 1.0
 * @date 2025/5/18 11:13
 * @description RefreshTokenTask
 */
@Component
public class RefreshTokenTask {


    @Resource
    private MessageChannelAccountService messageChannelAccountService;

    @Resource
    private MessageChannelParamService messageChannelParamService;

    @Resource
    private ThreadPoolProduceDecider threadPoolProduceDecider;

    @AutoRegisterXxlJob(executorHandler = "refreshWxMiniProgramAccessTokenTask",
            cron = "0 0/1 * * * ?",
            author = "arvin",
            jobDesc = "定时刷新微信小程序的token",
            triggerStatus = 1)
    public void refreshWxMiniProgramAccessTokenTask() {
        threadPoolProduceDecider.decide(ThreadPoolTypeEnums.SINGLE, true, "refreshGeTuiAccessTokenTask").execute(() -> {
            QueryWrapper queryWrapper = QueryWrapper.create()
                    .select(MESSAGE_CHANNEL_ACCOUNT.ALL_COLUMNS)
                    .leftJoin(MESSAGE_CHANNEL)
                    .on(MESSAGE_CHANNEL.ID.eq(MESSAGE_CHANNEL_ACCOUNT.CHANNEL_ID))
                    .where(MESSAGE_CHANNEL.CODE.eq(MessageChannelEnum.WX_MINI_PROGRAM.getCode()).and(MESSAGE_CHANNEL_ACCOUNT.STATUS.eq(0)));
            List<MessageChannelAccountPo> list = messageChannelAccountService.list(queryWrapper);
            if (CollectionUtil.isNotEmpty(list)) {
                for (MessageChannelAccountPo messageChannelAccountPo : list) {
                    Map<String, Object> channelParamsValueMap = messageChannelParamService.getChannelParamsAndValueForAccount(messageChannelAccountPo.getId());
                    if (MapUtil.isNotEmpty(channelParamsValueMap)) {
                        AccessTokenUtil.getWxMiniProgramAccessToken(messageChannelAccountPo, channelParamsValueMap, true);
                    }
                }
            }
        });
    }

    @AutoRegisterXxlJob(executorHandler = "refreshWxOfficeAccessTokenTask",
            cron = "0 0/1 * * * ?",
            author = "arvin",
            jobDesc = "定时刷新微信公众号的token",
            triggerStatus = 1)
    public void refreshWxOfficeAccessTokenTask() {
        threadPoolProduceDecider.decide(ThreadPoolTypeEnums.SINGLE, true, "refreshWxOfficeAccessTokenTask").execute(() -> {
            QueryWrapper queryWrapper = QueryWrapper.create()
                    .select(MESSAGE_CHANNEL_ACCOUNT.ALL_COLUMNS)
                    .leftJoin(MESSAGE_CHANNEL)
                    .on(MESSAGE_CHANNEL.ID.eq(MESSAGE_CHANNEL_ACCOUNT.CHANNEL_ID))
                    .where(MESSAGE_CHANNEL.CODE.eq(MessageChannelEnum.WX_OFFICE.getCode()).and(MESSAGE_CHANNEL_ACCOUNT.STATUS.eq(0)));
            List<MessageChannelAccountPo> list = messageChannelAccountService.list(queryWrapper);
            if (CollectionUtil.isNotEmpty(list)) {
                for (MessageChannelAccountPo messageChannelAccountPo : list) {
                    Map<String, Object> channelParamsValueMap = messageChannelParamService.getChannelParamsAndValueForAccount(messageChannelAccountPo.getId());
                    if (MapUtil.isNotEmpty(channelParamsValueMap)) {
                        AccessTokenUtil.getWxOfficeAccessToken(messageChannelAccountPo, channelParamsValueMap, true);
                    }
                }
            }
        });
    }


}
