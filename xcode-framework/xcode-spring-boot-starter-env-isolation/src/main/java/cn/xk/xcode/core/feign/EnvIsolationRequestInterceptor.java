package cn.xk.xcode.core.feign;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.context.EnvTagContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @Author xuk
 * @Date 2024/11/29 8:55
 * @Version 1.0.0
 * @Description EnvIsolationRequestInterceptor
 **/
public class EnvIsolationRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String tag = EnvTagContextHolder.getTag();
        if (StrUtil.isNotEmpty(tag)) {
            requestTemplate.header("tag", tag);
        }
    }
}
