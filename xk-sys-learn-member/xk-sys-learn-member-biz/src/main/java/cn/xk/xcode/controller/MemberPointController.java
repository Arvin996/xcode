package cn.xk.xcode.controller;

import cn.xk.xcode.client.MemberPointClient;
import cn.xk.xcode.entity.dto.MemberPointChangeReqDto;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.MemberUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
* @Author Administrator
* @Date 2024/8/26 12:05
* @Version V1.0.0
* @Description MemberPointController
*/
@RestController
public class MemberPointController implements MemberPointClient {

    @Resource
    private MemberUserService memberUserService;

    @Override
    @RequestMapping("/member-point")
    public CommonResult<Boolean> memberPointChange(MemberPointChangeReqDto memberPointChangeReqDto) {
        memberUserService.memberPointChange(memberPointChangeReqDto);
        return CommonResult.success(true);
    }
}
