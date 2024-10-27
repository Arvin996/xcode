package cn.xk.xcode.core.aop;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.*;
import cn.hutool.json.JSONUtil;
import cn.xk.xcode.config.XkSysSignProperties;
import cn.xk.xcode.core.annotation.IgnoreCrypt;
import cn.xk.xcode.core.crypt.AbstractCrypt;
import cn.xk.xcode.core.sign.AbstractSignAlgStrange;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.pojo.CommonResult;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static cn.xk.xcode.core.CryptGlobalConstant.*;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/10/26 12:33
 * @description CryptInterceptor
 */
public class CryptInterceptor implements MethodInterceptor {

    private boolean isSign = false;
    private final AbstractCrypt crypt;
    private final AbstractSignAlgStrange sign;
    private static final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    public CryptInterceptor(AbstractCrypt abstractCrypt, AbstractSignAlgStrange abstractSignAlgStrange) {
        this.crypt = abstractCrypt;
        this.sign = abstractSignAlgStrange;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            Object object;
            Method method = invocation.getMethod();
            Object targetObject = invocation.getThis();
            Class<?> clazz = targetObject != null ? targetObject.getClass() : method.getDeclaringClass();
            if (method.isAnnotationPresent(IgnoreCrypt.class) || clazz.isAnnotationPresent(IgnoreCrypt.class)) {
                object = invocation.proceed();
            } else {
                this.decrypt(invocation);
                object = invocation.proceed();
                object = this.encrypt(invocation, object);
            }
            return object;
        } catch (Throwable e) {
            throw new Throwable(e);
        }
    }

    private void decrypt(MethodInvocation invocation) throws Throwable {
        Object[] arguments = invocation.getArguments();
        if (ArrayUtil.isEmpty(arguments)) {
            return;
        }
        Object arg;
        for (int i = 0; i < arguments.length; i++) {
            arg = arguments[i];
            // 1. 解密
            String decryptStr = crypt.decrypt(arg.toString());
            boolean isJson = JSONUtil.isTypeJSON(decryptStr);
            if (isJson) {
                if (JSONUtil.isTypeJSONObject(decryptStr)) {
                    arguments[i] = JSONUtil.parseObj(decryptStr);
                } else {
                    arguments[i] = JSONUtil.parseArray(decryptStr);
                }
            } else {
                // 基本类型
                arguments[i] = decryptStr;
            }
        }
        if (isSign) {
            try {
                handleSign(arguments, invocation);
            } catch (Exception e) {
                throw new Throwable(e);
            }
        }
    }

    private void handleSign(Object[] arguments, MethodInvocation invocation) throws Exception {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        Map<String, Object> bodyMap = new HashMap<>();
        Map<String, Object> paramsMap = new HashMap<>();
        Method method = invocation.getMethod();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        for (int i = 0; i < arguments.length; i++) {
            bodyMap.put(parameterNames[i], arguments[i]);
        }
        UrlBuilder urlBuilder = null;
        if (Objects.nonNull(httpServletRequest)) {
            urlBuilder = UrlBuilder.of(httpServletRequest.getRequestURL() + "?" + httpServletRequest.getQueryString());
            urlBuilder.getQuery().getQueryMap().forEach((k, v) -> {
                paramsMap.put((String) k, v);
            });
        }
        XkSysSignProperties xkSysSignProperties = sign.getXkSysSignProperties();
        String signName = xkSysSignProperties.getSignName();
        XkSysSignProperties.SignLocation signLocation = xkSysSignProperties.getSignLocation();
        String signData = "";
        if (signLocation.equals(XkSysSignProperties.SignLocation.BODY)) {
            for (int i = 0; i < arguments.length; i++) {
                if (parameterNames[i].equals(signName)) {
                    signData = arguments[i].toString();
                }
            }
        } else if (signLocation.equals(XkSysSignProperties.SignLocation.PARAM)) {
            if (Objects.nonNull(urlBuilder)) {
                signData = (String) urlBuilder.getQuery().get(signName);
            }
        } else {
            if (Objects.nonNull(httpServletRequest)) {
                signData = httpServletRequest.getHeader(signName);
            }
        }
        if (signData.isEmpty()) {
            ExceptionUtil.castServerException(SIGN_DATA_IS_NULL);
        }
        boolean b = sign.verifySignData(bodyMap, paramsMap, signData);
        if (!b) {
            ExceptionUtil.castServerException(SIGN_FAILED);
        }
    }

    private Object encrypt(MethodInvocation invocation, Object object) {
        if (!(object instanceof CommonResult)) {
            ExceptionUtil.castServerException(RETURN_DATA_NOT_VALID);
        }
        CommonResult<?> commonResult = (CommonResult<?>) object;
        Object data = commonResult.getData();
        commonResult.setMsg(crypt.encrypt(commonResult.getMsg()));
        commonResult.setCode(crypt.encrypt(commonResult.getCode().toString()));
        CommonResult<String> stringCommonResult = new CommonResult<>();
        stringCommonResult.setCode(commonResult.getCode());
        stringCommonResult.setMsg(commonResult.getMsg());
        if (ObjectUtil.isNotNull(data)) {
            if (ObjUtil.isBasicType(data)){
                stringCommonResult.setData(crypt.encrypt(data.toString()));
            }else {
                stringCommonResult.setData(JSONUtil.toJsonStr(data));
            }
        }
        return stringCommonResult;
    }

    private HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes)) {
            return null;
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        return servletRequestAttributes.getRequest();
    }
}
