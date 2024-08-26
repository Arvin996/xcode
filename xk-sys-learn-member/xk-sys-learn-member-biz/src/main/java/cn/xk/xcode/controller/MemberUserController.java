package cn.xk.xcode.controller;

import cn.xk.xcode.client.MemberUserClient;
import cn.xk.xcode.entity.dto.user.MemberUserBaseDto;
import cn.xk.xcode.entity.dto.user.MemberUserSignDto;
import cn.xk.xcode.entity.vo.MemberUserResultVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.MemberUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @Author Administrator
 * @Date 2024/8/23 15:32
 * @Version V1.0.0
 * @Description MemberUserController
 */
@RestController
public class MemberUserController implements MemberUserClient {

    @Resource
    private MemberUserService memberUserService;

    @Override
    public CommonResult<MemberUserResultVo> getMemberUser(String userId) {
        return CommonResult.success(memberUserService.getMemberUser(userId));
    }

    @Override
    public CommonResult<List<MemberUserResultVo>> getMemberUserList(Collection<Long> ids) {
        return CommonResult.success(memberUserService.getMemberUserList(ids));
    }

    @Override
    public CommonResult<List<MemberUserResultVo>> getUserListByNickname(String nickname) {
        return CommonResult.success(memberUserService.getUserListByNickname(nickname));
    }

    @Override
    public CommonResult<MemberUserResultVo> getUserByMobile(String mobile) {
        return CommonResult.success(memberUserService.getUserByMobile(mobile));
    }

    @Override
    public CommonResult<MemberUserResultVo> getUserByEmail(String email) {
        return CommonResult.success(memberUserService.getUserByEmail(email));
    }

    @Operation(summary = "用户签到")
    @PostMapping("/userSign")
    public CommonResult<Boolean> userSign(@Validated @RequestBody MemberUserSignDto memberUserSignDto) {
        return CommonResult.success(memberUserService.userSign(memberUserSignDto));
    }
}
