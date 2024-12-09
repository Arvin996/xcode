package cn.xk.xcode.service.auth.handler.impl;

import cn.xk.xcode.client.CaptchaClientApi;
import cn.xk.xcode.entity.dto.user.MemberUserLoginDto;
import cn.xk.xcode.entity.po.MemberUserPo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.service.MemberUserService;
import cn.xk.xcode.service.auth.handler.AbstractMemberUserLoginHandler;

import static cn.xk.xcode.entity.def.MemberUserTableDef.MEMBER_USER_PO;
import static cn.xk.xcode.enums.MemberErrorCodeConstants.USER_EMAIL_NOT_REGISTER;

/**
 * @Author Administrator
 * @Date 2024/8/27 20:16
 * @Version V1.0.0
 * @Description MemberUserLoginEmailHandler
 */
public class MemberUserLoginEmailHandler extends AbstractMemberUserLoginHandler {

    public MemberUserLoginEmailHandler(CaptchaClientApi captchaClientApi, MemberUserService memberUserService) {
        super(captchaClientApi, memberUserService);
    }

    @Override
    public void castException() {
        ExceptionUtil.castServiceException(USER_EMAIL_NOT_REGISTER);
    }

    @Override
    public MemberUserPo getMemberUserPo(MemberUserLoginDto memberUserLoginDto) {
        return memberUserService.getOne(MEMBER_USER_PO.EMAIL.eq(memberUserLoginDto.getMobile()));
    }
}
