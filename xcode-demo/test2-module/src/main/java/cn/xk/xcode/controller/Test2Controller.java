package cn.xk.xcode.controller;

import cn.xk.xcode.pojo.CommonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author xuk
 * @Date 2024/12/24 15:13
 * @Version 1.0.0
 * @Description Test2Controller
 **/
@RestController
public class Test2Controller {

    @PostMapping("/test1")
    public CommonResult<Map<String, Object>> test1(@RequestBody Map<String, Object> map){
        return CommonResult.success(map);
    }
}
