package cn.xk.xcode.controller;

import cn.xk.xcode.client.MemberUserClient;
import cn.xk.xcode.entity.dto.user.MemberUserBindEmailDto;
import cn.xk.xcode.entity.dto.user.MemberUserBindMobileDto;
import cn.xk.xcode.entity.dto.user.MemberUserSignDto;
import cn.xk.xcode.entity.dto.user.MemberUserUpdateDto;
import cn.xk.xcode.entity.po.MemberUserPo;
import cn.xk.xcode.entity.vo.MemberUserResultVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.MemberUserService;
import cn.xk.xcode.utils.object.BeanUtil;
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
    public CommonResult<MemberUserResultVo> getMemberUser(Long userId) {
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

    @Operation(summary = "绑定邮箱")
    @PostMapping("/bindEmail")
    public CommonResult<Boolean> bindEmail(@Validated @RequestBody MemberUserBindEmailDto memberUserBandingEmailDto) {
        return CommonResult.success(memberUserService.bindEmail(memberUserBandingEmailDto));
    }

    @Operation(summary = "绑定手机号")
    @PostMapping("/bindMobile")
    public CommonResult<Boolean> bindMobile(@Validated @RequestBody MemberUserBindMobileDto memberUserBindMobileDto) {
        return CommonResult.success(memberUserService.bindMobile(memberUserBindMobileDto));
    }

    @Operation(summary = "用户基础信息更新")
    @PostMapping("/updateUserInfo")
    public CommonResult<Boolean> updateUserInfo(@Validated @RequestBody MemberUserUpdateDto memberUserUpdateDto) {
        return CommonResult.success(memberUserService.updateById(BeanUtil.toBean(memberUserUpdateDto, MemberUserPo.class)));
    }

}
