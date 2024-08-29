package cn.xk.xcode.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.xk.xcode.client.CheckCodeClientApi;
import cn.xk.xcode.entity.dto.CheckCodeVerifyReqDto;
import cn.xk.xcode.entity.dto.MemberBaseReqDto;
import cn.xk.xcode.entity.dto.MemberExperienceChangeReqDto;
import cn.xk.xcode.entity.dto.MemberPointChangeReqDto;
import cn.xk.xcode.entity.dto.user.*;
import cn.xk.xcode.entity.po.*;
import cn.xk.xcode.entity.vo.MemberUserResultVo;
import cn.xk.xcode.entity.vo.user.MemberUserLoginVo;
import cn.xk.xcode.enums.MemberPointChangeBizTypeEnum;
import cn.xk.xcode.event.MemberExperienceRecordEvent;
import cn.xk.xcode.event.MemberLevelChangeRecordEvent;
import cn.xk.xcode.event.MemberPointRecordEvent;
import cn.xk.xcode.event.MemberSignRecordEvent;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.LoginVO;
import cn.xk.xcode.service.*;
import cn.xk.xcode.service.auth.enums.LoginTypeEnum;
import cn.xk.xcode.service.auth.handler.AbstractMemberUserLoginHandler;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.mapper.MemberUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static cn.xk.xcode.entity.def.MemberLevelTableDef.MEMBER_LEVEL_PO;
import static cn.xk.xcode.entity.def.MemberSignTableDef.MEMBER_SIGN_PO;
import static cn.xk.xcode.entity.def.MemberTagTableDef.MEMBER_TAG_PO;
import static cn.xk.xcode.entity.def.MemberUserTableDef.MEMBER_USER_PO;
import static cn.xk.xcode.enums.MemberErrorCodeConstants.*;
import static cn.xk.xcode.enums.MemberPointChangeBizTypeEnum.SIGN;

/**
 * 服务层实现。
 *
 * @author lenovo
 * @since 2024-08-05
 */
@Service
public class MemberUserServiceImpl extends ServiceImpl<MemberUserMapper, MemberUserPo> implements MemberUserService {

    @Resource
    private MemberSignService memberSignService;

    @Resource
    private MemberLevelService memberLevelService;

    @Resource
    private CheckCodeClientApi checkCodeClientApi;

    @Resource
    private MemberTagService memberTagService;

    @Resource
    private MemberGroupService memberGroupService;

    @Override
    public MemberUserResultVo getMemberUser(String userId) {
        MemberUserPo memberUserPo = this.getById(userId);
        if (ObjectUtil.isNull(memberUserPo)) {
            ExceptionUtil.castServiceException(USER_NOT_EXISTS);
        }
        return BeanUtil.toBean(memberUserPo, MemberUserResultVo.class);
    }

    @Override
    public List<MemberUserResultVo> getMemberUserList(Collection<Long> ids) {
        QueryWrapper queryWrapper = QueryWrapper.create(MemberUserPo.class)
                .where(MEMBER_USER_PO.ID.in(ids));
        List<MemberUserPo> memberUserPoList = this.list(queryWrapper);
        return CollectionUtil.convertList(memberUserPoList, bean -> BeanUtil.toBean(bean, MemberUserResultVo.class));
    }

    @Override
    public List<MemberUserResultVo> getUserListByNickname(String nickname) {
        if (!StringUtils.hasText(nickname)) {
            return Collections.emptyList();
        }
        return CollectionUtil.convertList(this.list(MEMBER_USER_PO.NICKNAME.like(nickname)), bean -> BeanUtil.toBean(bean, MemberUserResultVo.class));
    }

    @Override
    public MemberUserResultVo getUserByMobile(String mobile) {
        if (!StringUtils.hasText(mobile)) {
            return null;
        }
        MemberUserPo memberUserPo = this.getOne(MEMBER_USER_PO.MOBILE.eq(mobile));
        if (ObjectUtil.isNotNull(memberUserPo)) {
            return BeanUtil.toBean(memberUserPo, MemberUserResultVo.class);
        }
        return null;
    }

    @Override
    public MemberUserResultVo getUserByEmail(String email) {
        if (!StringUtils.hasText(email)) {
            return null;
        }
        MemberUserPo memberUserPo = this.getOne(MEMBER_USER_PO.EMAIL.eq(email));
        if (ObjectUtil.isNotNull(memberUserPo)) {
            return BeanUtil.toBean(memberUserPo, MemberUserResultVo.class);
        }
        return null;
    }

    // todo 待完善
    @Override
    public Boolean userSign(MemberUserSignDto memberUserSignDto) {
        String userId = memberUserSignDto.getId();
        Integer day = memberUserSignDto.getDay();
        MemberSignPo memberSignPo = memberSignService.getOne(MEMBER_SIGN_PO.DAY.eq(day));
        if (ObjectUtil.isNull(memberSignPo)) {
            ExceptionUtil.castServiceException(SIGN_IN_CONFIG_NOT_EXISTS, day);
        }
        // 1. 创建签到记录 这里考虑使用异步事件监听的方式 更优雅
        MemberSignRecordPo memberSignRecordPo = new MemberSignRecordPo();
        memberSignRecordPo.setUserId(userId);
        memberSignRecordPo.setDay(day);
        memberSignRecordPo.setPoint(memberSignPo.getPoint());
        SpringUtil.publishEvent(new MemberSignRecordEvent(memberSignRecordPo));
        // 2. 增加积分 创建积分增加记录
        MemberPointChangeReqDto memberPointChangeReqDto = MemberPointChangeReqDto.builder().point(memberSignPo.getPoint())
                .bizId(day.toString()).bizType(SIGN.getType()).build();
        memberPointChangeReqDto.setUserId(userId);
        memberPointChange(memberPointChangeReqDto);
        // 3. 增加经验 这里要判断用户是否升级
        MemberExperienceChangeReqDto memberExperienceChangeReqDto = MemberExperienceChangeReqDto.builder().bizId(day.toString()).bizType(SIGN.getType()).experience(memberSignPo.getExperience())
                .build();
        memberExperienceChangeReqDto.setUserId(userId);
        memberExperienceChange(memberExperienceChangeReqDto);
        return true;
    }

    @Override
    public void memberPointChange(MemberPointChangeReqDto memberPointChangeReqDto) {
        String userId = memberPointChangeReqDto.getUserId();
        Integer bizType = memberPointChangeReqDto.getBizType();
        String bizId = memberPointChangeReqDto.getBizId();
        Integer point = memberPointChangeReqDto.getPoint();
        MemberUserPo memberUserPo = this.getById(userId);
        if (ObjectUtil.isNull(memberUserPo)) {
            ExceptionUtil.castServiceException(USER_NOT_EXISTS);
        }
        MemberPointChangeBizTypeEnum memberPointChangeBizTypeEnum = MemberPointChangeBizTypeEnum.getByType(bizType);
        if (ObjectUtil.isNull(memberPointChangeBizTypeEnum)) {
            ExceptionUtil.castServiceException(POINT_BIZ_NOT_SUPPORT);
        }
        if (!memberPointChangeBizTypeEnum.isAdd()) {
            memberUserPo.setPoint(memberUserPo.getPoint() - point);
        } else {
            memberUserPo.setPoint(memberUserPo.getPoint() + point);
        }
        MemberPointRecordPo memberPointRecordPo = new MemberPointRecordPo();
        memberPointRecordPo.setUserId(userId);
        memberPointRecordPo.setBizType(bizType);
        memberPointRecordPo.setBizId(bizId);
        memberPointRecordPo.setTitle(memberPointChangeBizTypeEnum.getName());
        memberPointRecordPo.setDescription(String.format(memberPointChangeBizTypeEnum.getDescription(), point));
        memberPointRecordPo.setChangePoint(point);
        memberPointRecordPo.setTotalPoint(memberUserPo.getPoint());
        SpringUtil.publishEvent(new MemberPointRecordEvent(memberPointRecordPo));
        memberUserPo.setPoint(memberUserPo.getPoint() + point);
        this.updateById(memberUserPo);
    }

    @Override
    public void memberExperienceChange(MemberExperienceChangeReqDto memberExperienceChangeReqDto) {
        Integer experience = memberExperienceChangeReqDto.getExperience();
        String bizId = memberExperienceChangeReqDto.getBizId();
        String userId = memberExperienceChangeReqDto.getUserId();
        MemberPointChangeBizTypeEnum memberPointChangeBizTypeEnum = MemberPointChangeBizTypeEnum.getByType(memberExperienceChangeReqDto.getBizType());
        if (ObjectUtil.isNull(memberPointChangeBizTypeEnum)) {
            ExceptionUtil.castServiceException(EXPERIENCE_BIZ_NOT_SUPPORT);
        }
        // 1. 获取用户
        MemberUserPo memberUserPo = this.getById(userId);
        if (ObjectUtil.isNull(memberUserPo)) {
            ExceptionUtil.castServiceException(USER_NOT_EXISTS);
        }
        // 获取用户等级
        Integer levelId = memberUserPo.getLevelId();
        // 获取用户现有经验
        Integer currentExperience = memberUserPo.getExperience();
        int changeExperience;
        if (memberPointChangeBizTypeEnum.isAdd()) {
            // 判断是否升级
            MemberLevelPo memberLevelPo = memberLevelService.list(MEMBER_LEVEL_PO.LEVEL.gt(levelId)).get(0);
            if (ObjectUtil.isNull(memberLevelPo)) {
                // 不存在下一级
                memberLevelPo = memberLevelService.getOne(MEMBER_LEVEL_PO.LEVEL.eq(levelId));
                if (Objects.equals(currentExperience, memberLevelPo.getExperience())) {
                    return;
                }
                if (currentExperience + experience >= memberLevelPo.getExperience()) {
                    memberUserPo.setExperience(memberLevelPo.getExperience());
                    changeExperience = memberLevelPo.getExperience() - currentExperience;
                } else {
                    memberUserPo.setExperience(currentExperience + experience);
                    changeExperience = experience;
                }
            } else {
                // 存在下一级
                changeExperience = experience;
                memberUserPo.setExperience(experience + currentExperience);
                if (currentExperience + experience >= memberLevelPo.getExperience()) {
                    // 升级
                    memberUserPo.setLevelId(memberLevelPo.getLevel());
                    // 记录日志
                    createMemberLevelChangeRecord(userId, memberLevelPo, memberUserPo, changeExperience, memberPointChangeBizTypeEnum);
                }
            }
        } else {
            // 判断是否降级
            MemberLevelPo memberLevelPo = memberLevelService.list(MEMBER_LEVEL_PO.LEVEL.lt(levelId)).get(0);
            if (ObjectUtil.isNull(memberLevelPo)) {
                // 不存在上一级
                if (currentExperience - experience <= 0) {
                    changeExperience = currentExperience;
                    memberUserPo.setExperience(0);
                } else {
                    changeExperience = experience;
                    memberUserPo.setExperience(currentExperience - experience);
                }
            } else {
                // 存在上一级
                changeExperience = experience;
                memberUserPo.setExperience(currentExperience - experience);
                if (memberLevelPo.getExperience() >= currentExperience - experience) {
                    memberUserPo.setLevelId(memberLevelPo.getId());
                    // 记录日志
                    createMemberLevelChangeRecord(userId, memberLevelPo, memberUserPo, changeExperience, memberPointChangeBizTypeEnum);
                }
            }
        }
        // 经验变更日志
        MemberExperienceRecordPo memberExperienceRecordPo = new MemberExperienceRecordPo();
        memberExperienceRecordPo.setUserId(userId);
        memberExperienceRecordPo.setExperience(changeExperience);
        memberExperienceRecordPo.setDescription(String.format(memberPointChangeBizTypeEnum.getDescription(), changeExperience));
        memberExperienceRecordPo.setTitle(memberPointChangeBizTypeEnum.getName());
        memberExperienceRecordPo.setBizId(bizId);
        memberExperienceRecordPo.setBizType(memberPointChangeBizTypeEnum.getType());
        memberExperienceRecordPo.setTotalExperience(memberUserPo.getExperience());
        SpringUtil.publishEvent(new MemberExperienceRecordEvent(memberExperienceRecordPo));
        // 更新用户经验
        this.updateById(memberUserPo);
    }

    @Override
    public Boolean userRegister(MemberUserRegisterDto memberUserRegisterDto) {
        String registerType = memberUserRegisterDto.getRegisterType();
        String code = memberUserRegisterDto.getCode();
        // 校验验证码
        CommonResult<Boolean> verifiedCodeResult = checkCodeClientApi.verifyCode(CheckCodeVerifyReqDto.builder().code(code).type(registerType).build());
        if (!CommonResult.isSuccess(verifiedCodeResult)) {
            ExceptionUtil.castServiceException0(verifiedCodeResult.getCode(), verifiedCodeResult.getMsg());
        }
        // 校验密码
        String password = memberUserRegisterDto.getPassword();
        String confirmPassword = memberUserRegisterDto.getConfirmPassword();
        if (!ObjectUtil.equal(password, confirmPassword)) {
            ExceptionUtil.castServiceException(PASSWORD_AND_CONFIRM_NOT_MATCH);
        }
        // 校验手机号或者邮箱
        if ("phone".equals(registerType)) {
            MemberUserResultVo user = getUserByMobile(memberUserRegisterDto.getMobile());
            if (ObjectUtil.isNotNull(user)) {
                ExceptionUtil.castServiceException(USER_MOBILE_ALREADY_USED);
            }
        } else {
            MemberUserResultVo memberUserResultVo = getUserByEmail(memberUserRegisterDto.getEmail());
            if (ObjectUtil.isNotNull(memberUserResultVo)) {
                ExceptionUtil.castServiceException(USER_EMAIL_ALREADY_USED);
            }
        }
        MemberUserPo memberUserPo = BeanUtil.toBean(memberUserRegisterDto, MemberUserPo.class);
        memberUserPo.setExperience(0);
        memberUserPo.setPassword(SaSecureUtil.md5(memberUserPo.getPassword()));
        memberUserPo.setId(IdUtil.fastUUID());
        memberUserPo.setPoint(0);
        memberUserPo.setStatus("0");
        QueryWrapper queryWrapper = QueryWrapper.create(MemberLevelPo.class).orderBy(MEMBER_LEVEL_PO.LEVEL, true);
        List<MemberLevelPo> memberLevelPoList = memberLevelService.list(queryWrapper);
        memberUserPo.setLevelId(memberLevelPoList.get(0).getLevel());
        this.save(memberUserPo);
        return true;
    }

    @Override
    public MemberUserLoginVo createMemberUserLoginVo(MemberUserPo memberUserPo) {
        MemberUserLoginVo memberUserLoginVo = BeanUtil.toBean(memberUserPo, MemberUserLoginVo.class);
        memberUserLoginVo.setMemberLevelResultVo(memberLevelService.getMemberUserLevel(MemberBaseReqDto.builder().userId(memberUserPo.getId()).build()));
        memberUserLoginVo.setGroupName(memberGroupService.getById(memberUserLoginVo.getGroupId()).getName());
        List<String> tagNames = memberTagService.list(MEMBER_TAG_PO.ID.in(memberUserLoginVo.getTagIds())).stream().map(MemberTagPo::getName).collect(Collectors.toList());
        memberUserLoginVo.setTagNames(tagNames);
        return memberUserLoginVo;
    }

    @Override
    public LoginVO userLogin(MemberUserLoginDto memberUserLoginDto) {
        AbstractMemberUserLoginHandler loginHandler = SpringUtil.getBean("login" + memberUserLoginDto.getLoginType(), AbstractMemberUserLoginHandler.class);
        return loginHandler.doLogin(memberUserLoginDto);
    }

    @Override
    public Boolean kickout(MemberUserBaseDto memberUserBaseDto) {
        StpUtil.kickout(memberUserBaseDto.getId());
        MemberUserPo memberUserPo = getById(memberUserBaseDto.getId());
        memberUserPo.setStatus("1");
        updateById(memberUserPo);
        return true;
    }

    @Override
    public Boolean changePassword(MemberUserChangePasswordDto memberUserChangePasswordDto) {
        String id = memberUserChangePasswordDto.getId();
        String newPassword = memberUserChangePasswordDto.getNewPassword();
        String oldPassword = memberUserChangePasswordDto.getOldPassword();
        MemberUserPo memberUserPo = getById(id);
        if (ObjectUtil.notEqual(SaSecureUtil.md5(oldPassword), memberUserPo.getPassword())){
            ExceptionUtil.castServiceException(AUTH_OLD_PASSWORD_NOT_CORRECT);
        }
        if (ObjectUtil.equals(newPassword, oldPassword)){
            ExceptionUtil.castServiceException(AUTH_OLD_PASSWORD_MUST_NOT_EQUAL_NEW);
        }
        memberUserPo.setPassword(SaSecureUtil.md5(newPassword));
        updateById(memberUserPo);
        // 用户需要重新登录 这里需要刷新令牌
        StpUtil.logout(id);
        return true;
    }

    @Override
    public Boolean bindEmail(MemberUserBindEmailDto memberUserBindEmailDto) {
        verifyCode(memberUserBindEmailDto.getCode(), LoginTypeEnum.EMAIL.getType());
        MemberUserPo memberUserPo = getById(memberUserBindEmailDto.getId());
        memberUserPo.setEmail(memberUserBindEmailDto.getEmail());
        return updateById(memberUserPo);
    }

    @Override
    public Boolean bindMobile(MemberUserBindMobileDto memberUserBindMobileDto) {
        verifyCode(memberUserBindMobileDto.getCode(), LoginTypeEnum.MOBILE.getType());
        MemberUserPo memberUserPo = getById(memberUserBindMobileDto.getId());
        memberUserPo.setMobile(memberUserBindMobileDto.getMobile());
        return updateById(memberUserPo);
    }

    private static void createMemberLevelChangeRecord(String userId, MemberLevelPo memberLevelPo
            , MemberUserPo memberUserPo, int changeExperience
            , MemberPointChangeBizTypeEnum memberPointChangeBizTypeEnum) {
        MemberLevelChangeRecordPo memberLevelChangeRecordPo = new MemberLevelChangeRecordPo();
        memberLevelChangeRecordPo.setUserId(userId);
        memberLevelChangeRecordPo.setLevelId(memberLevelPo.getLevel());
        memberLevelChangeRecordPo.setLevelName(memberLevelPo.getName());
        memberLevelChangeRecordPo.setTotalExperience(memberUserPo.getExperience());
        memberLevelChangeRecordPo.setChangeExperience(changeExperience);
        memberLevelChangeRecordPo.setTitle(memberPointChangeBizTypeEnum.getName());
        memberLevelChangeRecordPo.setDescription(String.format(memberPointChangeBizTypeEnum.getDescription(), changeExperience));
        SpringUtil.publishEvent(new MemberLevelChangeRecordEvent(memberLevelChangeRecordPo));
    }

    private void verifyCode(String code, String type){
        CommonResult<Boolean> booleanCommonResult = checkCodeClientApi.verifyCode(CheckCodeVerifyReqDto.builder()
                .type(type).code(code).build());
        if (!CommonResult.isSuccess(booleanCommonResult)){
            ExceptionUtil.castServiceException0(booleanCommonResult.getCode(), booleanCommonResult.getMsg());
        }
    }
}
