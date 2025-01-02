package cn.xk.xcode.controller;

import cn.hutool.core.map.MapUtil;
import cn.xk.xcode.core.annotation.IgnoreCrypt;
import cn.xk.xcode.core.annotation.IgnoreParamCrypt;
import cn.xk.xcode.pojo.CommonResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author xuk
 * @Date 2024/12/24 15:11
 * @Version 1.0.0
 * @Description Test1Controller
 **/

@RestController
public class TestCryptController {

    @IgnoreCrypt
    @PostMapping("/test1/{id}")
    public CommonResult<Map<String, Object>> test1(@RequestBody Map<String, Object> map, @PathVariable("id") String id){
        System.out.println(map);
        System.out.println(id);
        return CommonResult.success(map);
    }

    @GetMapping("/test2/{id}/{id1}")
    public CommonResult<Map<String, Object>> test2( @PathVariable("id") String id, @PathVariable("id1") String id1){
        System.out.println(id);
        System.out.println(id1);
        return CommonResult.success(MapUtil.of("id", id));
    }

    @GetMapping("/test3")
    public CommonResult<Map<String, Object>> test3( @RequestParam("aa") String aa, @RequestParam("id") Long id){
        System.out.println(aa);
        System.out.println(id);
        return CommonResult.success(MapUtil.of("id", aa));
    }

    @GetMapping("/test4")
    public CommonResult<Map<String, Object>> test4(@RequestHeader("id1") String id){
        System.out.println(id);
        return CommonResult.success(MapUtil.of("id", id));
    }
}
