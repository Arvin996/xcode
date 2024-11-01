package cn.xk.xcode.core;

import cn.hutool.core.util.StrUtil;
import com.gg.midend.web.annotation.AutoRegisterController;
import com.gg.midend.web.core.RegisterServiceContext;
import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * @Author xuk
 * @Date 2024/11/1 14:42
 * @Version 1.0.0
 * @Description RegisterControllerConfigure
 **/
public class RegisterControllerConfigure implements InitializingBean, ApplicationContextAware {

    @Getter
    private final Set<Class<?>> serviceSet;

    private ApplicationContext applicationContext;


    public RegisterControllerConfigure(Set<Class<?>> serviceSet) {
        this.serviceSet = serviceSet;
    }

    private void registerService(Class<?> clazz) {
        Object bean = applicationContext.getBeansOfType(clazz).values().iterator().next();
        AutoRegisterController autoRegisterController = clazz.getAnnotation(AutoRegisterController.class);
        String name = autoRegisterController.name();
        String controllerName = StrUtil.isBlank(name) ? clazz.getSimpleName() : name;
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            RegisterServiceContext.registerService(controllerName + "." + method.getName(), bean);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.serviceSet.forEach(this::registerService);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
