package cn.xk.xcode.core.aop;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.*;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.xk.xcode.config.SignProperties;
import cn.xk.xcode.core.annotation.IgnoreCrypt;
import cn.xk.xcode.core.crypt.AbstractCrypt;
import cn.xk.xcode.core.sign.AbstractSignAlgStrange;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.pojo.CommonResult;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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
@Slf4j
public class CryptInterceptor implements MethodInterceptor {

    @Setter
    private boolean isSign = false;
    private final AbstractCrypt crypt;
    private final AbstractSignAlgStrange sign;

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
                handlerSign(invocation);
                object = invocation.proceed();
                object = this.encrypt(object);
            }
            return object;
        } catch (Throwable e) {
            throw new Throwable(e);
        }
    }

    private void handlerSign(MethodInvocation invocation) throws Throwable {
        Object[] arguments = invocation.getArguments();
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
        SignProperties signProperties = sign.getSignProperties();
        Map<String, Object> bodyMap = new HashMap<>();
        Map<String, Object> paramsMap = new HashMap<>();
        Method method = invocation.getMethod();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < arguments.length; i++) {
            if (parameters[i].isAnnotationPresent(RequestBody.class)){
                Object object = arguments[i];
                JSONObject jsonObject = JSONUtil.parseObj(object);
                jsonObject.keySet().forEach(k -> {
                    bodyMap.put(k, jsonObject.get(k));
                });
            }
        }
        Boolean isDecryptRequestParam = signProperties.getIsDecryptRequestParam();
        UrlBuilder urlBuilder = null;
        if (Objects.nonNull(httpServletRequest)) {
            urlBuilder = UrlBuilder.of(httpServletRequest.getRequestURL() + "?" + httpServletRequest.getQueryString());
            urlBuilder.getQuery().getQueryMap().forEach((k, v) -> {
                if (isDecryptRequestParam){
                    paramsMap.put((String) k, crypt.decrypt(v.toString()));
                }else {
                    paramsMap.put((String) k, v);
                }
            });
        }
        String signName = signProperties.getSignName();
        SignProperties.SignLocation signLocation = signProperties.getSignLocation();
        String signData = "";
        if (signLocation.equals(SignProperties.SignLocation.BODY)) {
            signData = bodyMap.get(signName).toString();
        } else if (signLocation.equals(SignProperties.SignLocation.PARAM)) {
            if (Objects.nonNull(urlBuilder)) {
               if (isDecryptRequestParam){
                   signData = crypt.decrypt(urlBuilder.getQuery().get(signName).toString());
               }else {
                   signData = (String) urlBuilder.getQuery().get(signName);
               }
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
        }else {
            log.info("验签成功");
        }
    }

    private Object encrypt(Object object) {
        if (!(object instanceof CommonResult)) {
            ExceptionUtil.castServerException(RETURN_DATA_NOT_VALID);
        }
        CommonResult<?> commonResult = (CommonResult<?>) object;
        Object data = commonResult.getData();
        CommonResult<String> stringCommonResult = new CommonResult<>();
        stringCommonResult.setCode(commonResult.getCode());
        stringCommonResult.setMsg(commonResult.getMsg());
        if (ObjectUtil.isNotNull(data)) {
            if (ObjUtil.isBasicType(data)) {
                stringCommonResult.setData(crypt.encrypt(data.toString()));
            } else {
                stringCommonResult.setData(crypt.encrypt(JSONUtil.toJsonStr(data)));
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
