package cn.xk.xcode.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.entity.dto.tag.MemberListQueryDto;
import cn.xk.xcode.entity.dto.tag.MemberTagAddDto;
import cn.xk.xcode.entity.dto.tag.MemberTagBaseDto;
import cn.xk.xcode.entity.dto.tag.MemberTagUpdateDto;
import cn.xk.xcode.entity.po.MemberUserPo;
import cn.xk.xcode.entity.vo.tag.MemberTagVo;
import cn.xk.xcode.entity.vo.tag.MemberUserTagsVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.mapper.MemberUserMapper;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.MemberTagPo;
import cn.xk.xcode.mapper.MemberTagMapper;
import cn.xk.xcode.service.MemberTagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static cn.xk.xcode.entity.def.MemberTagTableDef.MEMBER_TAG_PO;
import static cn.xk.xcode.entity.def.MemberUserTableDef.MEMBER_USER_PO;
import static cn.xk.xcode.enums.MemberErrorCodeConstants.*;

/**
 *  服务层实现。
 *
 * @author lenovo
 * @since 2024-08-05
 */
@Service
public class MemberTagServiceImpl extends ServiceImpl<MemberTagMapper, MemberTagPo> implements MemberTagService {

    @Resource
    private MemberUserMapper memberUserMapper;

    @Override
    public Boolean addTag(MemberTagAddDto memberTagAddDto) {
        return this.save(MemberTagPo.builder().name(memberTagAddDto.getName()).build());
    }

    @Override
    public Boolean deleteTag(MemberTagBaseDto memberTagAddDto) {
        QueryWrapper queryWrapper = QueryWrapper.create(MemberUserPo.class)
                .where(QueryMethods.findInSet(QueryMethods.string(memberTagAddDto.getId().toString()), MEMBER_USER_PO.TAG_IDS).ne(0));
        if (memberUserMapper.selectCountByQuery(queryWrapper) > 0){
            ExceptionUtil.castServiceException(TAG_HAS_USER);
        }
        return this.removeById(memberTagAddDto.getId());
    }

    @Override
    public Boolean updateTage(MemberTagUpdateDto memberTagUpdateDto) {
        validateTag(memberTagUpdateDto.getName());
        MemberTagPo memberTagPo = this.getById(memberTagUpdateDto.getId());
        if (ObjectUtil.isNotNull(memberTagPo)){
            ExceptionUtil.castServiceException(TAG_NOT_ALLOWED_UPDATE);
        }
        return this.updateById(MemberTagPo.builder().id(memberTagUpdateDto.getId()).name(memberTagUpdateDto.getName()).build());
    }

    @Override
    public MemberUserTagsVo getUserTagList(MemberListQueryDto memberListQueryDto) {
        QueryWrapper queryWrapper = QueryWrapper.create(MemberTagPo.class)
                .where(MEMBER_TAG_PO.ID.in(memberListQueryDto.getTagIds()));
        return MemberUserTagsVo.builder().tags(CollectionUtil.convertList(
                this.list(queryWrapper), bean -> BeanUtil.toBean(bean, MemberTagVo.class)
        )).build();
    }

    public void validateTag(String name){
        int count = (int) this.count(MEMBER_TAG_PO.NAME.eq(name));
        if (count > 0){
            ExceptionUtil.castServiceException(TAG_NAME_EXISTS);
        }
    }

}
