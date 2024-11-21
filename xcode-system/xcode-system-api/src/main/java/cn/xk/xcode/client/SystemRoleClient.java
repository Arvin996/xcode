package cn.xk.xcode.client;

import cn.xk.xcode.entity.SystemRoleResultVo;
import cn.xk.xcode.factory.SystemRoleClientFallback;
import cn.xk.xcode.pojo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author xuk
 * @Date 2024/11/20 16:11
 * @Version 1.0.0
 * @Description SystemRoleClient
 **/
@FeignClient(value = "xcode-system", fallback = SystemRoleClientFallback.class)
@RequestMapping("/role")
@Tag(name = "系统用户远程调用")
public interface SystemRoleClient {

    @Operation(summary = "根据用户id获取用户信息")
    @GetMapping("/getRole")
    CommonResult<SystemRoleResultVo> getRole(@RequestParam("id") Integer id);
}
