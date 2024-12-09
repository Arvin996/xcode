package cn.xk.xcode.service.auth.handler.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.client.CaptchaClientApi;
import cn.xk.xcode.entity.dto.user.MemberUserLoginDto;
import cn.xk.xcode.entity.po.MemberUserPo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.service.MemberUserService;
import cn.xk.xcode.service.auth.handler.AbstractMemberUserLoginHandler;
import com.mybatisflex.core.query.QueryWrapper;

import static cn.xk.xcode.entity.def.MemberUserTableDef.MEMBER_USER_PO;
import static cn.xk.xcode.enums.MemberErrorCodeConstants.AUTH_LOGIN_BAD_CREDENTIALS;

/**
 * @Author Administrator
 * @Date 2024/8/27 20:27
 * @Version V1.0.0
 * @Description MemberUserPasswordHandler
 */
public class MemberUserPasswordHandler extends AbstractMemberUserLoginHandler {

    public MemberUserPasswordHandler(CaptchaClientApi captchaClientApi, MemberUserService memberUserService) {
        super(captchaClientApi, memberUserService);
    }

    @Override
    public void castException() {
        ExceptionUtil.castServiceException(AUTH_LOGIN_BAD_CREDENTIALS);
    }

    @Override
    public MemberUserPo getMemberUserPo(MemberUserLoginDto memberUserLoginDto) {
        MemberUserPo memberUserPo = getMemberUserPoByEmail(memberUserLoginDto);
        if (ObjectUtil.isNull(memberUserPo)){
            memberUserPo = getMemberUserPoByMobile(memberUserLoginDto);
        }
        return memberUserPo;
    }

    private MemberUserPo getMemberUserPoByMobile(MemberUserLoginDto memberUserLoginDto) {
        return memberUserService.getOne(QueryWrapper.create(MemberUserPo.class).where(
                MEMBER_USER_PO.MOBILE.eq(memberUserLoginDto.getAccount())
        ).and(MEMBER_USER_PO.PASSWORD.eq(SaSecureUtil.md5(memberUserLoginDto.getPassword()))));
    }

    private MemberUserPo getMemberUserPoByEmail(MemberUserLoginDto memberUserLoginDto) {
        return memberUserService.getOne(QueryWrapper.create(MemberUserPo.class).where(
                MEMBER_USER_PO.EMAIL.eq(memberUserLoginDto.getAccount())
        ).and(MEMBER_USER_PO.PASSWORD.eq(SaSecureUtil.md5(memberUserLoginDto.getPassword()))));
    }
}
