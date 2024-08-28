package cn.xk.xcode.config;

import cn.xk.xcode.client.CheckCodeClientApi;
import cn.xk.xcode.service.MemberUserService;
import cn.xk.xcode.service.auth.handler.AbstractMemberUserLoginHandler;
import cn.xk.xcode.service.auth.handler.impl.MemberUserLoginEmailHandler;
import cn.xk.xcode.service.auth.handler.impl.MemberUserLoginPhoneHandler;
import cn.xk.xcode.service.auth.handler.impl.MemberUserPasswordHandler;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Author Administrator
 * @Date 2024/8/27 20:35
 * @Version V1.0.0
 * @Description XkSysMemberConfig
 */
@Configuration
@EnableFeignClients(clients = {CheckCodeClientApi.class})
public class XkSysMemberConfig {

    @Resource
    private MemberUserService memberUserService;

    @Bean(name = "login_phone")
    public AbstractMemberUserLoginHandler memberUserLoginPhoneHandler(CheckCodeClientApi checkCodeClientApi){
        return new MemberUserLoginPhoneHandler(checkCodeClientApi, memberUserService);
    }

    @Bean(name = "login_email")
    public AbstractMemberUserLoginHandler memberUserLoginEmailHandler(CheckCodeClientApi checkCodeClientApi){
        return new MemberUserLoginEmailHandler(checkCodeClientApi, memberUserService);
    }

    @Bean(name = "login_password")
    public AbstractMemberUserLoginHandler memberUserPasswordHandler(CheckCodeClientApi checkCodeClientApi){
        return new MemberUserPasswordHandler(checkCodeClientApi, memberUserService);
    }
}
