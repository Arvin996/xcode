package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.MemberExperienceChangeReqDto;
import cn.xk.xcode.entity.dto.MemberPointChangeReqDto;
import cn.xk.xcode.entity.dto.user.*;
import cn.xk.xcode.entity.vo.MemberUserResultVo;
import cn.xk.xcode.entity.vo.user.MemberUserLoginVo;
import cn.xk.xcode.pojo.LoginVO;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.MemberUserPo;

import java.util.Collection;
import java.util.List;

/**
 *  服务层。
 *
 * @author lenovo
 * @since 2024-08-05
 */
public interface MemberUserService extends IService<MemberUserPo> {


    MemberUserResultVo getMemberUser(String userId);

    List<MemberUserResultVo> getMemberUserList(Collection<Long> ids);

    List<MemberUserResultVo> getUserListByNickname(String nickname);

    MemberUserResultVo getUserByMobile(String mobile);

    MemberUserResultVo getUserByEmail(String email);

    Boolean userSign(MemberUserSignDto memberUserSignDto);

    void memberPointChange(MemberPointChangeReqDto memberPointChangeReqDto);

    void memberExperienceChange(MemberExperienceChangeReqDto memberExperienceChangeReqDto);

    Boolean userRegister(MemberUserRegisterDto memberUserRegisterDto);

    MemberUserLoginVo createMemberUserLoginVo(MemberUserPo memberUserPo);

    LoginVO userLogin(MemberUserLoginDto memberUserLoginDto);

    Boolean kickout(MemberUserBaseDto memberUserBaseDto);

    Boolean changePassword(MemberUserChangePasswordDto memberUserChangePasswordDto);

    Boolean bindEmail(MemberUserBindEmailDto memberUserBindEmailDto);

    Boolean bindMobile(MemberUserBindMobileDto memberUserBindMobileDto);
}
