package cn.xk.xcode.client;

import cn.xk.xcode.entity.dto.MemberPointChangeReqDto;
import cn.xk.xcode.factory.MemberPointFallFactory;
import cn.xk.xcode.pojo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author xuk
 * @Date 2024/8/6 18:30
 * @Version 1.0
 * @Description MemberPointClient
 */
@FeignClient(value = "xcode-member", fallbackFactory = MemberPointFallFactory.class)
@Tag(name = "用户积分rpc接口")
@RequestMapping("/member-point")
public interface MemberPointClient {
    @Operation(summary = "用户积分变更")
    @PostMapping("/memberPointChange")
    CommonResult<Boolean> memberPointChange(@RequestBody MemberPointChangeReqDto memberPointChangeReqDto);
}
