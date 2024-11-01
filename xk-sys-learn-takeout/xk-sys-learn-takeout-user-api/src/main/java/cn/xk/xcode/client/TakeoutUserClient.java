package cn.xk.xcode.client;

import cn.xk.xcode.entity.vo.TakeoutUserResultVo;
import cn.xk.xcode.factory.TakeoutUserClientFallbackFactory;
import cn.xk.xcode.pojo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/10/29 14:18
 * @Version 1.0.0
 * @Description TakeoutUserClient
 **/
@FeignClient(value = "xk-sys-takeout-user", fallbackFactory = TakeoutUserClientFallbackFactory.class)
@Tag(name = "外卖用户用户rpc接口")
@RequestMapping("/takeout/user")
public interface TakeoutUserClient {

    @GetMapping("/getTakeoutUser")
    @Operation(summary = "获取用户信息")
    @Parameter(description = "用户id", example = "1231231")
    CommonResult<TakeoutUserResultVo> getTakeoutUser(@RequestParam("userId") Long userId);

    @GetMapping("/getTakeUserList")
    @Operation(summary = "获取多用户信息")
    CommonResult<List<TakeoutUserResultVo>> getTakeUserList(@RequestParam("ids") Collection<Long> ids);

    @GetMapping("/getUserListByName")
    @Operation(summary = "基于用户名称，模糊匹配用户列表")
    @Parameter(name = "name", description = "用户名称，模糊匹配", required = true, example = "土豆")
    CommonResult<List<TakeoutUserResultVo>> getUserListByName(@RequestParam("name") String name);

    @GetMapping("/getUserByMobile")
    @Operation(summary = "基于手机号，精准匹配用户")
    @Parameter(name = "mobile", description = "基于手机号，精准匹配用户", required = true, example = "13027102413")
    CommonResult<TakeoutUserResultVo> getUserByMobile(@RequestParam("mobile") String mobile);
}
