package cn.xk.xcode.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.entity.dto.MemberBaseReqDto;
import cn.xk.xcode.entity.dto.level.MemberLevelAddDto;
import cn.xk.xcode.entity.dto.level.MemberLevelBaseDto;
import cn.xk.xcode.entity.dto.level.MemberLevelUpdateDto;
import cn.xk.xcode.entity.po.MemberUserPo;
import cn.xk.xcode.entity.vo.MemberLevelResultVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.mapper.MemberUserMapper;
import cn.xk.xcode.utils.object.BeanUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.MemberLevelPo;
import cn.xk.xcode.mapper.MemberLevelMapper;
import cn.xk.xcode.service.MemberLevelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

import static cn.xk.xcode.entity.def.MemberLevelTableDef.MEMBER_LEVEL_PO;
import static cn.xk.xcode.entity.def.MemberUserTableDef.MEMBER_USER_PO;
import static cn.xk.xcode.enums.MemberErrorCodeConstants.*;

/**
 * 服务层实现。
 *
 * @author lenovo
 * @since 2024-08-05
 */
@Service
public class MemberLevelServiceImpl extends ServiceImpl<MemberLevelMapper, MemberLevelPo> implements MemberLevelService {

    @Resource
    private MemberUserMapper memberUserMapper;

    @Override
    public MemberLevelResultVo getMemberUserLevel(MemberBaseReqDto memberBaseReqDto) {
        MemberUserPo memberUserPo = memberUserMapper.selectOneById(memberBaseReqDto.getUserId());
        if (ObjectUtil.isNull(memberUserPo)) {
            ExceptionUtil.castServiceException(USER_NOT_EXISTS);
        }
        Integer level = memberUserPo.getLevelId();
        MemberLevelPo memberLevelPo = this.getOne(MEMBER_LEVEL_PO.LEVEL.eq(level));
        if (ObjectUtil.isNull(memberLevelPo)) {
            ExceptionUtil.castServiceException(LEVEL_NOT_EXISTS);
        }
        return BeanUtil.toBean(memberLevelPo, MemberLevelResultVo.class);
    }

    @Override
    public Boolean addMemberLevel(MemberLevelAddDto memberLevelAddDto) {
        validateMemberLevel(memberLevelAddDto);
        return this.save(BeanUtil.toBean(memberLevelAddDto, MemberLevelPo.class));
    }

    @Override
    public Boolean deleteMemberLevel(MemberLevelBaseDto memberLevelBaseDto) {
        long count = memberUserMapper.selectCountByCondition(MEMBER_USER_PO.LEVEL_ID.eq(memberLevelBaseDto.getId()));
        if (count > 0) {
            ExceptionUtil.castServiceException(LEVEL_HAS_USER);
        }
        return this.removeById(memberLevelBaseDto.getId());
    }

    @Override
    public Boolean updateMemberLevel(MemberLevelUpdateDto memberLevelUpdateDto) {
        validateMemberLevel(BeanUtil.toBean(memberLevelUpdateDto, MemberLevelAddDto.class));
        MemberLevelPo memberLevelPo = this.getById(memberLevelUpdateDto.getId());
        if (ObjectUtil.isNull(memberLevelPo)) {
            ExceptionUtil.castServiceException(LEVEL_NOT_EXISTS);
        }
        return this.updateById(BeanUtil.toBean(memberLevelUpdateDto, MemberLevelPo.class));
    }

    public void validateMemberLevel(MemberLevelAddDto memberLevelAddDto) {
        if (count(MEMBER_LEVEL_PO.LEVEL.eq(memberLevelAddDto.getLevel())) > 0) {
            ExceptionUtil.castServiceException(LEVEL_VALUE_EXISTS, memberLevelAddDto.getLevel());
        }
        if (count(MEMBER_LEVEL_PO.NAME.eq(memberLevelAddDto.getName())) > 0) {
            ExceptionUtil.castServiceException(LEVEL_NAME_EXISTS, memberLevelAddDto.getName());
        }
        List<MemberLevelPo> list = this.list(QueryWrapper.create(MemberLevelPo.class).where(
                MEMBER_LEVEL_PO.LEVEL.gt(memberLevelAddDto.getLevel())
        ).orderBy(MEMBER_LEVEL_PO.LEVEL, false));
        if (!list.isEmpty()) {
            MemberLevelPo memberLevelPo = list.get(0);
            if (memberLevelPo.getExperience() > memberLevelAddDto.getExperience()) {
                ExceptionUtil.castServiceException(LEVEL_EXPERIENCE_MIN, memberLevelAddDto.getLevel(), memberLevelPo.getLevel());
            }
        }
        list = this.list(QueryWrapper.create(MemberLevelPo.class).where(
                MEMBER_LEVEL_PO.LEVEL.lt(memberLevelAddDto.getLevel())
        ).orderBy(MEMBER_LEVEL_PO.LEVEL, true));
        if (!list.isEmpty()) {
            MemberLevelPo memberLevelPo = list.get(0);
            if (memberLevelPo.getExperience() < memberLevelAddDto.getExperience()) {
                ExceptionUtil.castServiceException(LEVEL_EXPERIENCE_MAX, memberLevelAddDto.getLevel(), memberLevelPo.getLevel());
            }
        }
    }
}
