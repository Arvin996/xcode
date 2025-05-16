package cn.xk.xcode.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.xk.xcode.core.StpMemberUtil;
import cn.xk.xcode.core.StpSystemUtil;
import cn.xk.xcode.exception.core.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.INVALID_TOKEN;

/**
 * @Author xuk
 * @Date 2024/11/22 8:53
 * @Version 1.0.0
 * @Description SaTokenMemberGlobalConfig
 **/
@SuppressWarnings("all")
@Slf4j
public class SaTokenMemberGlobalConfig implements WebMvcConfigurer {

    @Resource
    private WhitelistProperties whitelistProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handler -> {
            // 打印白名单 System.out.println(whitelistProperties.getWhitelist());
            // 指定一条 match 规则
            SaRouter
                    .match("/**")    // 拦截的 path 列表，可以写多个 */
                    .notMatch("/doc.html", "/v3/**", "/warm-flow-ui/**", "/warm-flow/**")
                    .notMatch("/*.html")
                    .notMatch("/swagger-resources")
                    .notMatch("/webjars/**")
                    .notMatch("/**/api-docs")
                    .notMatch("/static/**")
                    .notMatch("/error")
                    .notMatch("/**/favicon.ico")
                    .notMatch("/resources/**")
                    .notMatch(whitelistProperties.getWhitelist())
                    .check(r -> StpSystemUtil.checkLogin());        // 要执行的校验动作，可以写完整的 lambda 表达式

        })).addPathPatterns("/**");

    }
}
