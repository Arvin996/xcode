package cn.xk.xcode.client;

import cn.xk.xcode.entity.vo.MemberConfigResultVo;
import cn.xk.xcode.factory.MemberConfigFallFactory;
import cn.xk.xcode.pojo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author xuk
 * @Date 2024/8/6 12:02
 * @Version 1.0
 * @Description MemberConfigClient
 */
@FeignClient(value = "xk-sys-member", fallbackFactory = MemberConfigFallFactory.class)
@Tag(name = "用户信息配置rpc接口")
@RequestMapping("/member-client")
public interface MemberConfigClient
{
    @Operation(summary = "获取会员配置信息")
    @PostMapping("/getMemberConfig")
    CommonResult<MemberConfigResultVo> getMemberConfig();
}
