package com.xk.xcode.core.utils;

import cn.hutool.core.io.resource.ResourceUtil;
import com.xk.xcode.core.entity.Area;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;

import java.io.IOException;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/7/2 18:16
 * @description
 */
@Slf4j
public class IpUtils
{
    private final static IpUtils INSTANCE = new IpUtils();

    /**
     * IP 查询器，启动加载到内存中
     */
    private static Searcher SEARCHER;

    private IpUtils() {
        try {
            long now = System.currentTimeMillis();
            byte[] bytes = ResourceUtil.readBytes("ip2region.xdb");
            SEARCHER = Searcher.newWithBuffer(bytes);
            log.info("启动加载 IPUtils 成功，耗时 ({}) 毫秒", System.currentTimeMillis() - now);
        } catch (IOException e) {
            log.error("启动加载 IPUtils 失败", e);
        }
    }

    @SneakyThrows
    public static Integer getAreaId(long ip) {
        return Integer.parseInt(SEARCHER.search(ip));
    }

    @SneakyThrows
    public static Integer getAreaId(String ip) {
        return Integer.parseInt(SEARCHER.search(ip.trim()));
    }

    public static Area getArea(String ip) {
        return AreaUtils.getArea(getAreaId(ip));
    }

    public static Area getArea(long ip) {
        return AreaUtils.getArea(getAreaId(ip));
    }
}
