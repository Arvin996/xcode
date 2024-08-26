package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.xk.xcode.client.MemberConfigClient;
import cn.xk.xcode.entity.dto.config.MemberConfigAddDto;
import cn.xk.xcode.entity.vo.MemberConfigResultVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.MemberConfigService;
import cn.xk.xcode.utils.object.BeanUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author Administrator
 * @Date 2024/8/22 9:41
 * @Version V1.0.0
 * @Description MemberConfigController
 */
@RestController
public class MemberConfigController implements MemberConfigClient {

    @Resource
    private MemberConfigService memberConfigService;

    @Override
    public CommonResult<MemberConfigResultVo> getMemberConfig() {
        return CommonResult.success(BeanUtil.toBean(memberConfigService.list().get(0), MemberConfigResultVo.class));
    }

    @SaCheckPermission("member:config:add")
    @Operation(summary = "添加会员配置")
    @PostMapping("/addMemberConfig")
    public CommonResult<Boolean> addMemberConfig(@Validated @RequestBody MemberConfigAddDto memberConfigAddDto){
        return CommonResult.success(memberConfigService.addMemberConfig(memberConfigAddDto));
    }

    @SaCheckPermission("member:config:update")
    @Operation(summary = "修改会员配置")
    @PostMapping("/updateMemberConfig")
    public CommonResult<Boolean> updateMemberConfig(@Validated @RequestBody MemberConfigAddDto memberConfigAddDto){
        return CommonResult.success(memberConfigService.updateMemberConfig(memberConfigAddDto));
    }

    @SaCheckPermission("member:config:delete")
    @Operation(summary = "删除会员配置")
    @PostMapping("/deleteMemberConfig")
    public CommonResult<Boolean> deleteMemberConfig(){
        return CommonResult.success(memberConfigService.deleteMemberConfig());
    }
}


