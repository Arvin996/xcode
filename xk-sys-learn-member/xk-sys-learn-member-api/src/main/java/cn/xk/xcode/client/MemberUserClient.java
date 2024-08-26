package cn.xk.xcode.client;

import cn.xk.xcode.entity.vo.MemberUserResultVo;
import cn.xk.xcode.factory.MemberUserFallFactory;
import cn.xk.xcode.pojo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/8/8 13:46
 * @Version 1.0
 * @Description MemberUserClient
 */
@FeignClient(value = "xk-sys-member", fallbackFactory = MemberUserFallFactory.class)
@Tag(name = "用户rpc接口")
@RequestMapping("/member-user")
public interface MemberUserClient {

    @GetMapping("/getMemberUser")
    @Operation(summary = "获取用户信息")
    @Parameter(description = "用户id", example = "aak1211n121")
    CommonResult<MemberUserResultVo> getMemberUser(@RequestParam("userId") String userId);

    @GetMapping("/getMemberUserListByIds")
    @Operation(summary = "获取多用户信息")
    CommonResult<List<MemberUserResultVo>> getMemberUserList(@RequestParam("ids") Collection<Long> ids);

    @GetMapping("/getMemberUserListByNickname")
    @Operation(summary = "基于用户昵称，模糊匹配用户列表")
    @Parameter(name = "nickname", description = "用户昵称，模糊匹配", required = true, example = "土豆")
    CommonResult<List<MemberUserResultVo>> getUserListByNickname(@RequestParam("nickname") String nickname);

    @GetMapping("/getMemberUserListByMobile")
    @Operation(summary = "基于手机号，精准匹配用户")
    @Parameter(name = "mobile", description = "基于手机号，精准匹配用户", required = true, example = "13027102413")
    CommonResult<MemberUserResultVo> getUserByMobile(@RequestParam("mobile") String mobile);

    @GetMapping("/getMemberUserListByEmail")
    @Operation(summary = "基于邮箱，精准匹配用户")
    @Parameter(name = "email", description = "基于手机号，精准匹配用户", required = true, example = "123@qq.com")
    CommonResult<MemberUserResultVo> getUserByEmail(@RequestParam("email") String email);

}
