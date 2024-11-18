package cn.xk.xcode.client;

import cn.xk.xcode.entity.dto.MemberBaseReqDto;
import cn.xk.xcode.entity.vo.MemberLevelResultVo;
import cn.xk.xcode.factory.MemberLevelFallFactory;
import cn.xk.xcode.pojo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author xuk
 * @Date 2024/8/6 15:55
 * @Version 1.0
 * @Description MemberLevelClient
 */
@FeignClient(value = "xk-sys-member", fallbackFactory = MemberLevelFallFactory.class)
@Tag(name = "用户等级rpc接口")
@RequestMapping("/member-level")
public interface MemberLevelClient
{
    @Operation(summary = "获取用户等级")
    @PostMapping("/getMemberUserLevel")
    CommonResult<MemberLevelResultVo> getMemberUserLevel(@Validated @RequestBody MemberBaseReqDto memberBaseReqDto);
}
