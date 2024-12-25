package cn.xk.xcode.core.resolver;

import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.core.annotation.IgnoreParamCrypt;
import cn.xk.xcode.core.crypt.AbstractCrypt;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.RequestHeaderMapMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestHeaderMethodArgumentResolver;

/**
 * @Author xuk
 * @Date 2024/12/25 14:11
 * @Version 1.0.0
 * @Description CryptRequestHeaderMethodArgumentResolver
 **/
public class CryptRequestHeaderMethodArgumentResolver extends RequestHeaderMethodArgumentResolver {

    private final AbstractCrypt abstractCrypt;

    public CryptRequestHeaderMethodArgumentResolver(ConfigurableBeanFactory beanFactory, AbstractCrypt abstractCrypt) {
        super(beanFactory);
        this.abstractCrypt = abstractCrypt;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return super.supportsParameter(parameter);
    }

    @Override
    protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
        Object content = super.resolveName(name, parameter, request);
        // 解析
        if (ObjectUtil.isNotEmpty(content)){
            if (parameter.hasParameterAnnotation(IgnoreParamCrypt.class)){
                return content;
            }else {
                return abstractCrypt.decrypt(content.toString());
            }
        }
        return null;
    }
}
