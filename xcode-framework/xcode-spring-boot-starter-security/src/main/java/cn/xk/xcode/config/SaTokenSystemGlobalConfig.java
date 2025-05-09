package cn.xk.xcode.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.xk.xcode.core.StpSystemUtil;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2024/6/24 09:49
 * @Version 1.0
 * @Description SaTokenGlobalConfig
 */
public class SaTokenSystemGlobalConfig implements WebMvcConfigurer {

    @Resource
    private WhitelistProperties whitelistProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，定义详细认证规则
        registry.addInterceptor(new SaInterceptor(handler -> {
            // 打印白名单 System.out.println(whitelistProperties.getWhitelist());
            // 指定一条 match 规则
            SaRouter
                    .match("/**")    // 拦截的 path 列表，可以写多个 */
                    .notMatch("/webjars/**", "/doc.html", "/v3/**", "/warm-flow-ui/**", "/warm-flow/**")
                    .notMatch(whitelistProperties.getWhitelist())
                    .check(r -> StpSystemUtil.checkLogin());        // 要执行的校验动作，可以写完整的 lambda 表达式

        })).addPathPatterns("/**");

    }


}
