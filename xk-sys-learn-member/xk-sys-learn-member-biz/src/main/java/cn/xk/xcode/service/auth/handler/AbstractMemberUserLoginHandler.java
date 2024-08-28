package cn.xk.xcode.service.auth.handler;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.client.CheckCodeClientApi;
import cn.xk.xcode.entity.dto.CheckCodeVerifyReqDto;
import cn.xk.xcode.entity.dto.user.MemberUserLoginDto;
import cn.xk.xcode.entity.po.MemberUserPo;
import cn.xk.xcode.entity.vo.user.MemberUserLoginVo;
import cn.xk.xcode.enums.GlobalStatusEnum;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.LoginUser;
import cn.xk.xcode.pojo.LoginVO;
import cn.xk.xcode.service.MemberUserService;
import cn.xk.xcode.service.auth.enums.LoginTypeEnum;
import cn.xk.xcode.utils.SaTokenLoginUtils;
import cn.xk.xcode.utils.WebServletUtils;
import cn.xk.xcode.utils.collections.CollectionUtil;
import com.xk.xcode.core.utils.IpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static cn.xk.xcode.enums.MemberErrorCodeConstants.*;
import static cn.xk.xcode.service.auth.enums.LoginTypeEnum.*;

/**
 * @Author Administrator
 * @Date 2024/8/27 17:20
 * @Version V1.0.0
 * @Description AbstractMemberUserLoginHandler
 */
@RequiredArgsConstructor
public abstract class AbstractMemberUserLoginHandler {

    protected final CheckCodeClientApi checkCodeClientApi;
    protected final MemberUserService memberUserService;

    public LoginVO doLogin(MemberUserLoginDto memberUserLoginDto){
        MemberUserPo memberUserPo = getMemberUserPo(memberUserLoginDto);
        validateMemberUserPo(memberUserPo);
        CommonResult<Boolean> booleanCommonResult = checkCode(memberUserLoginDto);
        if (!CommonResult.isSuccess(booleanCommonResult)){
            ExceptionUtil.castServiceException0(booleanCommonResult.getCode(), booleanCommonResult.getMsg());
        }
        SaLoginModel saLoginModel = bulidSaLoginModel(memberUserPo, memberUserLoginDto);
        LoginVO loginVO = createLoginVO(createMemberUserLoginVo(memberUserPo));
        LoginUser loginUser = buildLoginUser(memberUserPo);
        SaTokenLoginUtils.doLogin(loginUser, saLoginModel);
        return loginVO;
    }

    public abstract void castException();

    private void validateMemberUserPo(MemberUserPo user){
        if (ObjectUtil.isNull(user)) {
            castException();
        }
        if (!GlobalStatusEnum.isEnable(Integer.parseInt(user.getStatus()))) {
            ExceptionUtil.castServerException(AUTH_LOGIN_USER_DISABLED);
        }
        String ip = WebServletUtils.getClientIp();
        user.setLoginIp(StringUtils.hasLength(ip) ? ip : "未知");
        user.setLoginTime(LocalDateTime.now());
        user.setLoginAreaId(StringUtils.hasLength(ip) ?  IpUtils.getAreaId(ip) : 0);
        memberUserService.updateById(user);
    }

    public MemberUserLoginVo createMemberUserLoginVo(MemberUserPo memberUserPo){
        return memberUserService.createMemberUserLoginVo(memberUserPo);
    }

    // 1. 做校验 2. 更新用户信息
    public abstract MemberUserPo getMemberUserPo(MemberUserLoginDto memberUserLoginDto);

    public CommonResult<Boolean> checkCode(MemberUserLoginDto memberUserLoginDto){
        if (ObjectUtil.equals(LoginTypeEnum.getLoginTypeEnum(memberUserLoginDto.getLoginType()), PASSWORD)){
            return CommonResult.success(true);
        }
        return checkCodeClientApi.verifyCode(CheckCodeVerifyReqDto.builder().code(memberUserLoginDto.getCode()).type(memberUserLoginDto.getLoginType()).build());
    }

    public SaLoginModel bulidSaLoginModel(MemberUserPo memberUserPo, MemberUserLoginDto memberUserLoginDto){
        SaLoginModel saLoginModel = SaLoginModel.create();
        saLoginModel.setDevice(memberUserLoginDto.getLoginDevice());
        saLoginModel.setExtraData(createExtraData(memberUserPo, memberUserLoginDto));
        return saLoginModel;
    }

    public Map<String, Object> createExtraData(MemberUserPo memberUserPo, MemberUserLoginDto memberUserLoginDto) {
        Map<String, Object> extraData = new HashMap<>();
        extraData.put("loginId", memberUserPo.getId());
        extraData.put("loginType", memberUserLoginDto.getLoginType());
        return extraData;
    }

    public LoginVO createLoginVO(Object userInfo) {
        LoginVO loginVo = new LoginVO();
        loginVo.setAccessToken(StpUtil.getTokenValue());
        loginVo.setExpireIn(StpUtil.getTokenTimeout());
        loginVo.setUserInfo(userInfo);
        return loginVo;
    }

    public LoginUser buildLoginUser(MemberUserPo memberUserPo){
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(memberUserPo.getId());
        loginUser.setRoles(CollectionUtil.createEmptySet());
        loginUser.setPermissions(CollectionUtil.createEmptySet());
        return loginUser;
    }

}
