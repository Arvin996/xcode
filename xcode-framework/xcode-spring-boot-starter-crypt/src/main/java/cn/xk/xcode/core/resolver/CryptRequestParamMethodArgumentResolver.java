package cn.xk.xcode.core.resolver;

import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.core.annotation.IgnoreParamCrypt;
import cn.xk.xcode.core.crypt.AbstractCrypt;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;

/**
 * @Author xuk
 * @Date 2024/12/25 14:04
 * @Version 1.0.0
 * @Description CryptRequestParamMethodArgumentResolver
 **/
public class CryptRequestParamMethodArgumentResolver extends RequestParamMethodArgumentResolver {

    private final AbstractCrypt abstractCrypt;

    public CryptRequestParamMethodArgumentResolver(boolean useDefaultResolution, AbstractCrypt abstractCrypt) {
        super(useDefaultResolution);
        this.abstractCrypt = abstractCrypt;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestParam.class);
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
