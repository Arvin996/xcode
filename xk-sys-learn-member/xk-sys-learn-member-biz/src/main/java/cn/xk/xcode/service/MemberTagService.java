package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.tag.MemberListQueryDto;
import cn.xk.xcode.entity.dto.tag.MemberTagAddDto;
import cn.xk.xcode.entity.dto.tag.MemberTagBaseDto;
import cn.xk.xcode.entity.dto.tag.MemberTagUpdateDto;
import cn.xk.xcode.entity.vo.tag.MemberTagVo;
import cn.xk.xcode.entity.vo.tag.MemberUserTagsVo;
import cn.xk.xcode.pojo.CommonResult;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.MemberTagPo;

/**
 *  服务层。
 *
 * @author lenovo
 * @since 2024-08-05
 */
public interface MemberTagService extends IService<MemberTagPo> {

    Boolean addTag(MemberTagAddDto memberTagAddDto);

    Boolean deleteTag(MemberTagBaseDto memberTagAddDto);

    Boolean updateTage(MemberTagUpdateDto memberTagUpdateDto);

    MemberUserTagsVo getUserTagList(MemberListQueryDto memberListQueryDto);
}
