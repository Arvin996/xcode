package cn.xk.xcode.config;

import cn.xk.xcode.client.CaptchaClientApi;
import cn.xk.xcode.service.MemberUserService;
import cn.xk.xcode.service.auth.handler.AbstractMemberUserLoginHandler;
import cn.xk.xcode.service.auth.handler.impl.MemberUserLoginEmailHandler;
import cn.xk.xcode.service.auth.handler.impl.MemberUserLoginMobileHandler;
import cn.xk.xcode.service.auth.handler.impl.MemberUserPasswordHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Author Administrator
 * @Date 2024/8/27 20:35
 * @Version V1.0.0
 * @Description XcodeMemberConfig
 */
@Configuration
public class XcodeMemberConfig {

    @Resource
    private MemberUserService memberUserService;

    @Bean(name = "login_mobile")
    public AbstractMemberUserLoginHandler memberUserLoginMobileHandler(CaptchaClientApi captchaClientApi){
        return new MemberUserLoginMobileHandler(captchaClientApi, memberUserService);
    }

    @Bean(name = "login_email")
    public AbstractMemberUserLoginHandler memberUserLoginEmailHandler(CaptchaClientApi captchaClientApi){
        return new MemberUserLoginEmailHandler(captchaClientApi, memberUserService);
    }

    @Bean(name = "login_password")
    public AbstractMemberUserLoginHandler memberUserPasswordHandler(CaptchaClientApi captchaClientApi){
        return new MemberUserPasswordHandler(captchaClientApi, memberUserService);
    }
}
