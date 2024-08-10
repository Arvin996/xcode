package cn.xk.xcode.client;

import cn.xk.xcode.entity.dto.MemberQueryDto;
import cn.xk.xcode.entity.dto.MemberUserListQueryDto;
import cn.xk.xcode.entity.dto.MemberUsersQueryDto;
import cn.xk.xcode.entity.vo.MemberUserResultVo;
import cn.xk.xcode.pojo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/8/8 13:46
 * @Version 1.0
 * @Description MemberUserClient
 */
@FeignClient("xk-sys-member")
@Tag(name = "用户rpc接口")
@RequestMapping("/member-user")
public interface MemberUserClient {

    @PostMapping("/getMemberUser")
    @Operation(summary = "获取用户信息")
    CommonResult<MemberUserResultVo> getMemberUser(@RequestBody MemberQueryDto memberUsersQueryDto);

    @PostMapping("/getMemberUserList")
    @Operation(summary = "获取多用户信息")
    CommonResult<List<MemberUserResultVo>> getMemberUserList(@RequestBody MemberUserListQueryDto memberUserListQueryDto);
}
