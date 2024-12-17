package cn.xk.xcode.core.filter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.xk.xcode.core.annotation.IgnoreCrypt;
import cn.xk.xcode.core.crypt.AbstractCrypt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @Author xuk
 * @Date 2024/12/17 9:39
 * @Version 1.0.0
 * @Description DecryptFilter
 **/
@Slf4j
public class DecryptFilter implements Filter {

    private final AbstractCrypt crypt;

    public DecryptFilter(AbstractCrypt crypt) {
        this.crypt = crypt;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HandlerMethod handlerMethod = getHandlerMethod(request);
        if (ObjectUtil.isNull(handlerMethod)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        Method method = handlerMethod.getMethod();
        if (handlerMethod.hasMethodAnnotation(IgnoreCrypt.class) || method.getDeclaringClass().isAnnotationPresent(IgnoreCrypt.class)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        ServletRequest requestWrapper;
        // 是否为 json 请求
        if (StringUtils.startsWithIgnoreCase(request.getContentType(), MediaType.APPLICATION_JSON_VALUE)) {
            // 处理@PathVariable 的参数
            Parameter[] parameters = method.getParameters();
            int count = 0;
            for (Parameter parameter : parameters) {
                if (parameter.isAnnotationPresent(PathVariable.class)) {
                    count++;
                }
            }
            requestWrapper = new DecryptRequestBodyWrapper(request, crypt, count);
            filterChain.doFilter(requestWrapper, servletResponse);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private HandlerMethod getHandlerMethod(HttpServletRequest request) {
        RequestMappingHandlerMapping handlerMapping = SpringUtil.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        try {
            HandlerExecutionChain mappingHandler = handlerMapping.getHandler(request);
            if (ObjectUtil.isNotNull(mappingHandler)) {
                Object handler = mappingHandler.getHandler();
                if (ObjectUtil.isNotNull(handler)) {
                    // 从handler获取注解
                    if (handler instanceof HandlerMethod) {
                        return ((HandlerMethod) handler);
                    }
                }
            }
        } catch (Exception e) {
            log.error("解密过滤器获取HandlerMethod异常：{}", e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }

}
