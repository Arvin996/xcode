package cn.xk.xcode.client;

import cn.xk.xcode.entity.SystemDictResultVo;
import cn.xk.xcode.factory.SystemDictClientFallback;
import cn.xk.xcode.pojo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/11/18 15:56
 * @Version 1.0.0
 * @Description SystemDictClient
 **/
@FeignClient(value = "xcode-system", fallback = SystemDictClientFallback.class)
@RequestMapping("/dict")
@Tag(name = "系统字典远程调用")
public interface SystemDictClient {

    @Operation(summary = "获取特定通过的字典值")
    @GetMapping("/getDictValue")
    CommonResult<String> getDictValue(@RequestParam("dictType") String dictType, @RequestParam("dictCode") String dictCode);

    @Operation(summary = "获取某个字典类型的所有字典值")
    @GetMapping("/getDictTypeValues")
    CommonResult<List<SystemDictResultVo>> getDictTypeValues(@RequestParam("dictType") String dictType);
}
