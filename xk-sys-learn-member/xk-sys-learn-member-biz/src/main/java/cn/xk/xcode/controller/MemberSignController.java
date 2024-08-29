package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.xk.xcode.entity.dto.sign.MemberSignAddDto;
import cn.xk.xcode.entity.dto.sign.MemberSignUpdateDto;
import cn.xk.xcode.entity.vo.sign.MemberSignResultVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.MemberSignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Administrator
 * @Date 2024/8/21 11:18
 * @Version V1.0.0
 * @Description MemberSignController
 */
@RestController
@RequestMapping("/sign")
@Tag(name = "会员用户签到接口")
public class MemberSignController {

    @Resource
    private MemberSignService memberSignService;

    @Operation(summary = "添加签到规则")
    @PostMapping("/addSign")
    @SaCheckPermission("member:sign:add")
    public CommonResult<Boolean> addSign(@Validated @RequestBody MemberSignAddDto memberSignAddDto){
        return CommonResult.success(memberSignService.addSign(memberSignAddDto));
    }

    @Operation(summary = "更新签到规则")
    @PostMapping("/updateSignRule")
    @SaCheckPermission("member:sign:update")
    public CommonResult<Boolean> updateSignRule(@Validated @RequestBody MemberSignUpdateDto memberSignUpdateDto){
        return CommonResult.success(memberSignService.updateSignRule(memberSignUpdateDto));
    }

    @Operation(summary = "获取签到规则")
    @PostMapping("/getSignRules")
    public CommonResult<List<MemberSignResultVo>> getSignRule(){
        return CommonResult.success(memberSignService.getSignRule());
    }

}
