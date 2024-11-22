package cn.xk.xcode.core;

import com.alibaba.fastjson2.filter.SimplePropertyPreFilter;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Author xuk
 * @Date 2024/11/22 15:40
 * @Version 1.0.0
 * @Description SimplePropertyPreFilterUtil
 **/
public class SimplePropertyPreFilterFactory {
    private SimplePropertyPreFilterFactory() {}

    public static SimplePropertyPreFilter createForExcludeProperties(String... properties) {
        SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter();
        simplePropertyPreFilter.getExcludes().addAll(Arrays.stream(properties).collect(Collectors.toList()));
        return simplePropertyPreFilter;
    }

    public static SimplePropertyPreFilter createForIncludeProperties(String... properties) {
        SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter();
        simplePropertyPreFilter.getIncludes().addAll(Arrays.stream(properties).collect(Collectors.toList()));
        return simplePropertyPreFilter;
    }
}
