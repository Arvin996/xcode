package cn.xk.xcode.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.xk.xcode.core.StpMemberUtil;
import cn.xk.xcode.core.StpSystemUtil;
import cn.xk.xcode.exception.core.ExceptionUtil;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.UNAUTHORIZED;

/**
 * @Author xuk
 * @Date 2025/5/14 8:35
 * @Version 1.0.0
 * @Description SaTokenMixedGlobalConfig
 **/
@SuppressWarnings("all")
public class SaTokenMixedGlobalConfig implements WebMvcConfigurer {

    @Resource
    private WhitelistProperties whitelistProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handler -> {
            // 打印白名单 System.out.println(whitelistProperties.getWhitelist());
            // 指定一条 match 规则
            SaRouter.notMatch("/doc.html", "/v3/**", "/warm-flow-ui/**", "/warm-flow/**")
                    .notMatch("/*.html")
                    .notMatch("/swagger-resources")
                    .notMatch("/webjars/**")
                    .notMatch("/**/api-docs")
                    .notMatch("/static/**")
                    .notMatch("/error")
                    .notMatch("/**/favicon.ico")
                    .notMatch("/resources/**")
                    .notMatch("/template/**")
                    .notMatch(whitelistProperties.getWhitelist())
                    .notMatch("/auth/third/**")
                    .match("/**").check(r -> {
                        if (!StpSystemUtil.isLogin() && !StpMemberUtil.isLogin()) {
                            ExceptionUtil.castServerException(UNAUTHORIZED);
                        }
                    })
                    .match("/manage/**").check(r -> StpSystemUtil.checkLogin())
                    .match("/api/**").check(r -> StpMemberUtil.checkLogin());
        })).addPathPatterns("/**");
    }
}
