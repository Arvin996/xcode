package cn.xk.xcode.client;

import cn.xk.xcode.entity.dto.MemberExperienceChangeReqDto;
import cn.xk.xcode.factory.MemberExperienceFallFactory;
import cn.xk.xcode.pojo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author xuk
 * @Date 2024/8/6 12:07
 * @Version 1.0
 * @Description MemberExperienceClient
 */
@FeignClient(value = "xk-sys-member", fallbackFactory = MemberExperienceFallFactory.class)
@Tag(name = "用户经验rpc接口")
@RequestMapping("/member-experience")
public interface MemberExperienceClient {
    @Operation(summary = "用户经验变更")
    @PostMapping("/memberExperienceChange")
    CommonResult<Boolean> memberExperienceChange(@RequestBody MemberExperienceChangeReqDto memberExperienceChangeReqDto);
}
