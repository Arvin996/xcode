package cn.xk.xcode.core.resolver;

import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.core.annotation.IgnoreParamCrypt;
import cn.xk.xcode.core.crypt.AbstractCrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.PathVariableMethodArgumentResolver;

/**
 * @Author xuk
 * @Date 2024/12/25 13:28
 * @Version 1.0.0
 * @Description CryptoPathVariableMethodArgumentResolver
 **/
@RequiredArgsConstructor
public class CryptPathVariableMethodArgumentResolver extends PathVariableMethodArgumentResolver {

    private final AbstractCrypt abstractCrypt;

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
