package cn.xk.xcode.config;

import cn.hutool.extra.spring.SpringUtil;
import cn.xk.xcode.core.resolver.CryptPathVariableMethodArgumentResolver;
import cn.xk.xcode.core.resolver.CryptRequestHeaderMethodArgumentResolver;
import cn.xk.xcode.core.resolver.CryptRequestParamMethodArgumentResolver;
import cn.xk.xcode.core.crypt.AbstractCrypt;
import cn.xk.xcode.utils.collections.CollectionUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/12/25 13:45
 * @Version 1.0.0
 * @Description WebPathArgumentConfiguration
 **/
public class WebArgumentResolverConfiguration implements InitializingBean {

    @Resource
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @Resource
    private ConfigurableBeanFactory configurableBeanFactory;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<HandlerMethodArgumentResolver> argumentResolvers = requestMappingHandlerAdapter.getArgumentResolvers();
        if (CollectionUtil.isEmpty(argumentResolvers)){
            return;
        }
        List<HandlerMethodArgumentResolver> list = new ArrayList<>();
        list.add(0, new CryptPathVariableMethodArgumentResolver(SpringUtil.getBean(AbstractCrypt.class)));
        list.add(1, new CryptRequestParamMethodArgumentResolver(true, SpringUtil.getBean(AbstractCrypt.class)));
        list.add(2, new CryptRequestHeaderMethodArgumentResolver(configurableBeanFactory, SpringUtil.getBean(AbstractCrypt.class)));
        list.addAll(argumentResolvers);
        requestMappingHandlerAdapter.setArgumentResolvers(list);
    }
}
