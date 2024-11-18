package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.group.MemberGroupAddDto;
import cn.xk.xcode.entity.dto.group.MemberGroupBaseDto;
import cn.xk.xcode.entity.dto.group.MemberGroupQueryDto;
import cn.xk.xcode.entity.dto.group.MemberGroupUpdateDto;
import cn.xk.xcode.entity.vo.group.MemberGroupResultVo;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.MemberGroupPo;

import java.util.List;

/**
 *  服务层。
 *
 * @author lenovo
 * @since 2024-08-05
 */
public interface MemberGroupService extends IService<MemberGroupPo> {

    Boolean addGroup(MemberGroupAddDto memberGroupAddDto);

    Boolean updateGroup(MemberGroupUpdateDto memberGroupUpdateDto);

    List<MemberGroupResultVo> queryGroup(MemberGroupQueryDto memberGroupQueryDto);

    Boolean deleteGroup(MemberGroupBaseDto memberGroupBaseDto);
}
