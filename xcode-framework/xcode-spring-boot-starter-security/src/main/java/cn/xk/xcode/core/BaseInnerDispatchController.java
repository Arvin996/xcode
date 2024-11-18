package cn.xk.xcode.core;

import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

/**
 * @Author xuk
 * @Date 2024/11/1 13:36
 * @Version 1.0.0
 * @Description BaseInnerDispatchController
 **/
@RestController
@RequestMapping("/inner")
public class BaseInnerDispatchController {

    @PostMapping("/body/{serviceName}.{methodName}")
    public Object dispatch(@PathVariable("serviceName") String serviceName, @PathVariable("methodName") String methodName, @RequestBody Object body) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return RegisterServiceContext.invoke(serviceName, methodName, body);
    }
}
