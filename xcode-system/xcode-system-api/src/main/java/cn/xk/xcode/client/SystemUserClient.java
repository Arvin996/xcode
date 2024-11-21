package cn.xk.xcode.client;

import cn.xk.xcode.entity.SystemUserResultVo;
import cn.xk.xcode.factory.SystemUserClientFallback;
import cn.xk.xcode.pojo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/11/18 15:40
 * @Version 1.0.0
 * @Description SystemUserClient
 **/
@FeignClient(value = "xcode-system", fallback = SystemUserClientFallback.class)
@RequestMapping("/user")
@Tag(name = "系统用户远程调用")
public interface SystemUserClient {

    @Operation(summary = "根据用户id获取用户信息")
    @GetMapping("/getUser")
    CommonResult<SystemUserResultVo> getUser(@RequestParam("id") Long id);

    @GetMapping("/selectNotUserList")
    @Operation(summary = "排除用户id集合获取用户信息")
    CommonResult<List<SystemUserResultVo>> selectNotUserList(@RequestParam("userIds") Collection<Long> userIds);

    @GetMapping("/selectUserList")
    @Operation(summary = "根据用户id集合获取用户信息")
    CommonResult<List<SystemUserResultVo>> selectUserList(@RequestParam("userIds") Collection<Long> userIds);
}
