package cn.xk.xcode.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.entity.dto.user.MemberUserBaseDto;
import cn.xk.xcode.entity.dto.user.MemberUserSignDto;
import cn.xk.xcode.entity.po.MemberSignPo;
import cn.xk.xcode.entity.vo.MemberUserResultVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.service.MemberLevelService;
import cn.xk.xcode.service.MemberSignRecordService;
import cn.xk.xcode.service.MemberSignService;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.MemberUserPo;
import cn.xk.xcode.mapper.MemberUserMapper;
import cn.xk.xcode.service.MemberUserService;
import com.xk.xcode.core.utils.AreaUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static cn.xk.xcode.entity.def.MemberSignTableDef.MEMBER_SIGN_PO;
import static cn.xk.xcode.entity.def.MemberUserTableDef.MEMBER_USER_PO;
import static cn.xk.xcode.enums.MemberErrorCodeConstants.USER_NOT_EXISTS;

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
    private MemberSignRecordService memberSignRecordService;

    @Resource
    private MemberLevelService memberLevelService;

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
        MemberUserPo memberUserPo = this.getById(userId);
        MemberSignPo memberSignPo = memberSignService.getOne(MEMBER_SIGN_PO.DAY.eq(day));
        return null;
    }
}
