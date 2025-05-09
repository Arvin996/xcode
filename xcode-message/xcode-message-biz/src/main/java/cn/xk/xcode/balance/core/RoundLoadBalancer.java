package cn.xk.xcode.balance.core;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.xk.xcode.core.lock.DistributedLock;
import cn.xk.xcode.entity.po.MessageChannelAccountPo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.utils.collections.CollectionUtil;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.LOCKED;

/**
 * @Author xuk
 * @Date 2025/3/11 14:33
 * @Version 1.0.0
 * @Description RoundLoadBalancer
 **/
public class RoundLoadBalancer implements ILoadBalancer {

    private final DistributedLock distributedLock = SpringUtil.getBean(DistributedLock.class);
    private final static String LOCK_KEY_PREFIX = "round_load_balancer_lock_";
    private final static String CURRENT_ROUND_KEY_PREFIX = "round_load_balancer_current_round_";
    private final static long WAIT_LOCK_TIME_OUT = 2L;
    private final static long LOCK_TIME_OUT = 4L;
    private final StringRedisTemplate redisTemplate = SpringUtil.getBean(StringRedisTemplate.class);

    @Override
    public MessageChannelAccountPo choose(List<MessageChannelAccountPo> accountPoList) {
        if (CollectionUtil.isEmpty(accountPoList)) {
            return null;
        }
        String channelCode = accountPoList.get(0).getChannelCode();
        String lockKey = LOCK_KEY_PREFIX + channelCode;
        boolean locked = distributedLock.tryLock(lockKey, WAIT_LOCK_TIME_OUT, LOCK_TIME_OUT, TimeUnit.SECONDS);
        if (!locked) {
            ExceptionUtil.castServerException(LOCKED);
        }
        try {
            String currentRoundKey = CURRENT_ROUND_KEY_PREFIX + channelCode;
            if (!redisTemplate.hasKey(currentRoundKey)) {
                redisTemplate.opsForValue().set(currentRoundKey, "0");
            }
            String currentRound = redisTemplate.opsForValue().get(currentRoundKey);
            if (StrUtil.isEmpty(currentRound)) {
                currentRound = "0";
            }
            int currentRoundInt = Integer.parseInt(currentRound);
            redisTemplate.opsForValue().set(currentRoundKey, String.valueOf(++currentRoundInt));
            return accountPoList.get(currentRoundInt % accountPoList.size());
        } finally {
            distributedLock.unlock(lockKey);
        }
    }
}
