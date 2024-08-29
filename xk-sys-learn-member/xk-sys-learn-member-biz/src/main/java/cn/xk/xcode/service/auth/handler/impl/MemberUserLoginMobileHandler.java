package cn.xk.xcode.service.auth.handler.impl;

import cn.xk.xcode.client.CheckCodeClientApi;
import cn.xk.xcode.entity.dto.user.MemberUserLoginDto;
import cn.xk.xcode.entity.po.MemberUserPo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.service.MemberUserService;
import cn.xk.xcode.service.auth.handler.AbstractMemberUserLoginHandler;

import static cn.xk.xcode.entity.def.MemberUserTableDef.MEMBER_USER_PO;
import static cn.xk.xcode.enums.MemberErrorCodeConstants.USER_PHONE_NOT_REGISTER;


/**
 * @Author Administrator
 * @Date 2024/8/27 19:58
 * @Version V1.0.0
 * @Description MemberUserLoginPhoneHandler
 */
public class MemberUserLoginMobileHandler extends AbstractMemberUserLoginHandler {

    public MemberUserLoginMobileHandler(CheckCodeClientApi checkCodeClientApi, MemberUserService memberUserService) {
        super(checkCodeClientApi, memberUserService);
    }

    @Override
    public void castException() {
        ExceptionUtil.castServiceException(USER_PHONE_NOT_REGISTER);
    }

    @Override
    public MemberUserPo getMemberUserPo(MemberUserLoginDto memberUserLoginDto) {
        return memberUserService.getOne(MEMBER_USER_PO.MOBILE.eq(memberUserLoginDto.getMobile()));
    }
}
