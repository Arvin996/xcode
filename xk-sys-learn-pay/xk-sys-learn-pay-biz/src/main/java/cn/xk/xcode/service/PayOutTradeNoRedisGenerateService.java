package cn.xk.xcode.service;

import cn.hutool.core.date.DateUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static cn.hutool.core.date.DatePattern.PURE_DATETIME_PATTERN;
import static cn.xk.xcode.config.PayProperties.ORDER_NO_PREFIX;
import static cn.xk.xcode.config.PayProperties.PAY_NO;

/**
 * @Author xuk
 * @Date 2024/9/29 8:59
 * @Version 1.0.0
 * @Description PayOutTradeNoRedisGenerateService
 **/
@Service
public class PayOutTradeNoRedisGenerateService {

    private static final int KEY_EXPIRED_TIME = 3;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public String generateOrderOutTradeNo() {
        String prefix = ORDER_NO_PREFIX + DateUtil.format(LocalDateTime.now(), PURE_DATETIME_PATTERN);
        String key = PAY_NO + prefix;
        Long no = stringRedisTemplate.opsForValue().increment(key);
        stringRedisTemplate.expire(key, KEY_EXPIRED_TIME, TimeUnit.SECONDS);
        return prefix + no;
    }
}
