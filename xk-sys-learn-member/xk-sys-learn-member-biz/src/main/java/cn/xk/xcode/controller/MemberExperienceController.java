package cn.xk.xcode.controller;

import cn.xk.xcode.client.MemberExperienceClient;
import cn.xk.xcode.entity.dto.MemberExperienceChangeReqDto;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.MemberUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author Administrator
 * @Date 2024/8/26 14:12
 * @Version V1.0.0
 * @Description MemberExperienceController
 */
@RestController
public class MemberExperienceController implements MemberExperienceClient {

    @Resource
    private MemberUserService memberUserService;

    @Override
    @PostMapping("/memberExperienceChange")
    public CommonResult<Boolean> memberExperienceChange(MemberExperienceChangeReqDto memberExperienceChangeReqDto) {
        memberUserService.memberExperienceChange(memberExperienceChangeReqDto);
        return CommonResult.success(true);
    }
}
