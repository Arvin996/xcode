package cn.xk.xcode.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.xk.xcode.config.HutoolHttpRequestAspect;
import cn.xk.xcode.core.aop.interceptor.ProxyType;
import cn.xk.xcode.core.support.proxy.ThirdHttpProxyFactory;
import cn.xk.xcode.core.support.wrap.ThirdRequestWrapper;
import cn.xk.xcode.pojo.CommonResult;
import com.alibaba.fastjson2.JSON;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2025/1/14 11:25
 * @Version 1.0.0
 * @Description TestTraceController
 **/
@RestController
@RequestMapping("/trace")
public class TestTraceController {

    @PostMapping("/test1")
    public CommonResult<Map<String, Object>> test1(@RequestBody Map<String, Object> map) {
        return CommonResult.success(map);
    }

    @PostMapping("/test2")
    public CommonResult<Map<String, Object>> test2(@RequestBody Map<String, Object> map) {
        HttpRequest httpRequest = HttpRequest.post("http://localhost:1001/test1").body(JSON.toJSONString(map));
        Object execute = new ThirdRequestWrapper("http://localhost:1001/test1", httpRequest, "execute", new Object[]{}, o -> true, JSON.toJSONString(map)).process();
        System.out.println(execute.toString());
        return CommonResult.success(map);
    }
}
