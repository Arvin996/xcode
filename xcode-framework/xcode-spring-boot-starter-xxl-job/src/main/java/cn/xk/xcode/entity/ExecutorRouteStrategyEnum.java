package cn.xk.xcode.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author xuk
 * @Date 2025/2/6 10:23
 * @Version 1.0.0
 * @Description ExecutorRouteStrategyEnum
 **/
@AllArgsConstructor
@Getter
public enum ExecutorRouteStrategyEnum {

    FIRST("FIRST", "第一个"),
    LAST("LAST", "最后一个"),
    ROUND("ROUND", "轮训"),
    RANDOM("RANDOM", "随机"),
    CONSISTENT_HASH("CONSISTENT_HASH", "一致性HASH"),
    LEAST_FREQUENTLY_USED("LEAST_FREQUENTLY_USED", "最不经常使用LFU"),
    LEAST_RECENTLY_USED("LEAST_RECENTLY_USED", "最近最少使用LRU"),
    FAILOVER("FAILOVER", "故障转移"),
    BUSYOVER("BUSYOVER", "忙碌转移"),
    SHARDING_BROADCAST("SHARDING_BROADCAST", "分片广播");


    private final String value;
    private final String desc;
}
