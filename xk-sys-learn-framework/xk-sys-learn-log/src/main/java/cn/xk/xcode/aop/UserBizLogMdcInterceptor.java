package cn.xk.xcode.aop;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.xk.xcode.annotation.UserBizLogMdc;
import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;

/**
 * @Author xuk
 * @Date 2024/10/17 11:11
 * @Version 1.0.0
 * @Description UserBizLogInterceptor
 * @Optimize 注解要额外新增一些其它属性 比如说 商品id
 **/
@RequiredArgsConstructor
public class UserBizLogMdcInterceptor implements MethodInterceptor {

    private static final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    @Override
    public Object invoke(@NotNull MethodInvocation invocation) throws Throwable {
        try {
            this.execute(invocation);
            return invocation.proceed();
        }finally {
            MDC.clear();
        }
    }

    private void execute(MethodInvocation invocation) {
        // 增强方法
        Method method = invocation.getMethod();
        // 代理对象
        Object targetObject = invocation.getThis();
        // 代理类
        Class<?> clazz = targetObject != null ? targetObject.getClass() : method.getDeclaringClass();
        UserBizLogMdc userBizLogMdc = null;
        // 首先看类上面是否有注解
        if (clazz.isAnnotationPresent(UserBizLogMdc.class)) {
            userBizLogMdc= clazz.getAnnotation(UserBizLogMdc.class);

        }
        if (method.isAnnotationPresent(UserBizLogMdc.class)){
            userBizLogMdc = method.getAnnotation(UserBizLogMdc.class);
        }
        MDC.put("userId", StpUtil.getLoginIdAsString());
        MDC.put("threadId", Thread.currentThread().getName() + Thread.currentThread().getId());
        MDC.put("userIp", "");
        // 这里最好需要从请求参数里面拿一下 比如说#xxx.yyy的形式 不支持递归
        if (ObjectUtil.isNull(userBizLogMdc)){
            return;
        }
        String orderId = userBizLogMdc.orderId();
        String refundId = userBizLogMdc.refundId();
        Object[] arguments = invocation.getArguments();
        if (ArrayUtil.isEmpty(arguments)){
            // 没有参数 直接返回
            return;
        }
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        if (ArrayUtil.isEmpty(parameterNames)){
            // 没有参数 直接返回
            return;
        }
        if (!StrUtil.isEmptyIfStr(orderId)){
            if (orderId.startsWith("#")){
                // 解析
                MDC.put("orderId", resolverArgs(orderId, arguments, parameterNames));
            }else {
                MDC.put("orderId", orderId);
            }
        }
        if (!StrUtil.isEmptyIfStr(refundId)){
            if (refundId.startsWith("#")){
                // 解析
                MDC.put("refundId", resolverArgs(refundId, arguments, parameterNames));
            }else {
                MDC.put("refundId", refundId);
            }
        }
    }

    private String resolverArgs(String arg, Object[] arguments, String[] parameterNames){
        // 这里进行解析
        String res = null;
        arg = arg.substring(1);
        String[] split = arg.split("\\.");
        int i = 0;
        for (; i < parameterNames.length; i++){
            if (parameterNames[i].equals(split[0])){
                break;
            }
        }
        if (i == parameterNames.length){
            // todo 报错 解析错误
        }
        Object argValue = arguments[i];
        JSONObject jsonObject = JSONUtil.parseObj(argValue);
        for (int j = 1; j < split.length; j++){
            if (ObjectUtil.isNull(jsonObject)){
                // todo 报错 解析错误
            }
            if (j == split.length - 1){
                res = jsonObject.getStr(split[j], null);
            }
            jsonObject = jsonObject.getJSONObject(split[j]);
        }
        if (StrUtil.isEmpty(res)){
            // todo 报错 解析错误
        }
        return res;
    }
}
