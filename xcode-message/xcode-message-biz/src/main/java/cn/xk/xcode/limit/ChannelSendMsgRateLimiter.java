package cn.xk.xcode.limit;

import cn.xk.xcode.config.XcodeMessageProperties;
import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.enums.RateLimiterEnum;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;

/**
 * @Author xuk
 * @Date 2025/3/12 13:44
 * @Version 1.0.0
 * @Description ChannelSendMsgRateLimiter
 **/
@RequiredArgsConstructor
public class ChannelSendMsgRateLimiter implements BaseRateLimiter{

    private final RedissonClient redissonClient;

    private final XcodeMessageProperties xcodeMessageProperties;

    private static final String KEY = "channel_send_msg";

    @Override
    public Boolean rateLimit(MessageTask messageTask) {
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(RATE_LIMITER_KEY_PREFIX + KEY);
        rateLimiter.setRate(RateType.OVERALL, xcodeMessageProperties.getLimiter().getChannelNumRatePerSecond(), 1, RateIntervalUnit.SECONDS);
        return rateLimiter.tryAcquire(messageTask.getReceiverSet().size());
    }

    @Override
    public RateLimiterEnum type() {
        return RateLimiterEnum.CHANNEL_SEND_SUER_NUM_LIMITER;
    }
}
