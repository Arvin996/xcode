package cn.xk.xcode.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.entity.dto.group.MemberGroupAddDto;
import cn.xk.xcode.entity.dto.group.MemberGroupBaseDto;
import cn.xk.xcode.entity.dto.group.MemberGroupQueryDto;
import cn.xk.xcode.entity.dto.group.MemberGroupUpdateDto;
import cn.xk.xcode.entity.vo.group.MemberGroupResultVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.MemberGroupPo;
import cn.xk.xcode.mapper.MemberGroupMapper;
import cn.xk.xcode.service.MemberGroupService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.List;
import static cn.xk.xcode.entity.def.MemberGroupTableDef.MEMBER_GROUP_PO;
import static cn.xk.xcode.enums.MemberErrorCodeConstants.GROUP_NOT_EXISTS;
import static cn.xk.xcode.enums.MemberErrorCodeConstants.GROUP_HAS_EXISTS;
import static cn.xk.xcode.enums.MemberErrorCodeConstants.GROUP_HAS_USER;

/**
 *  服务层实现。
 *
 * @author lenovo
 * @since 2024-08-05
 */
@Service
public class MemberGroupServiceImpl extends ServiceImpl<MemberGroupMapper, MemberGroupPo> implements MemberGroupService {

    @Override
    public Boolean addGroup(MemberGroupAddDto memberGroupAddDto) {
        if (ObjectUtil.isNotNull(this.getOne(MEMBER_GROUP_PO.NAME.eq(memberGroupAddDto.getName())))){
            ExceptionUtil.castServiceException(GROUP_HAS_EXISTS, memberGroupAddDto.getName());
        }
        return this.save(MemberGroupPo.builder().name(memberGroupAddDto.getName()).build());
    }

    @Override
    public Boolean updateGroup(MemberGroupUpdateDto memberGroupUpdateDto) {
        if (StringUtils.hasLength(memberGroupUpdateDto.getName())){
            if (ObjectUtil.isNotNull(this.getOne(MEMBER_GROUP_PO.NAME.eq(memberGroupUpdateDto.getName())))){
                ExceptionUtil.castServiceException(GROUP_HAS_EXISTS, memberGroupUpdateDto.getName());
            }
        }
        if (ObjectUtil.isNotNull(this.getById(memberGroupUpdateDto.getId()))){
            ExceptionUtil.castServerException(GROUP_NOT_EXISTS);
        }
        return this.updateById(BeanUtil.toBean(memberGroupUpdateDto,  MemberGroupPo.class));
    }

    @Override
    public List<MemberGroupResultVo> queryGroup(MemberGroupQueryDto memberGroupQueryDto) {
        QueryWrapper queryWrapper = QueryWrapper.create(MemberGroupPo.class)
                .where("1=1")
                .and(MEMBER_GROUP_PO.ID.eq(memberGroupQueryDto.getId()).when(ObjectUtil.isNotNull(memberGroupQueryDto.getId())))
                .and(MEMBER_GROUP_PO.NAME.like(memberGroupQueryDto.getName()).when(StringUtils.hasLength(memberGroupQueryDto.getName())))
                .and(MEMBER_GROUP_PO.STATUS.eq(memberGroupQueryDto.getStatus()).when(ObjectUtil.isNotNull(memberGroupQueryDto.getStatus())));
        return CollectionUtil.convertList(this.list(queryWrapper), bean -> BeanUtil.toBean(bean, MemberGroupResultVo.class));
    }

    @Override
    public Boolean deleteGroup(MemberGroupBaseDto memberGroupBaseDto) {
        if (ObjectUtil.isNotNull(this.getById(memberGroupBaseDto.getId()))){
            ExceptionUtil.castServiceException(GROUP_HAS_USER);
        }
        return this.removeById(memberGroupBaseDto.getId());
    }
}
