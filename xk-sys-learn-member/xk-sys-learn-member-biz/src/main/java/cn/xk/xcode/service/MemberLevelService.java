package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.MemberBaseReqDto;
import cn.xk.xcode.entity.dto.level.MemberLevelAddDto;
import cn.xk.xcode.entity.dto.level.MemberLevelBaseDto;
import cn.xk.xcode.entity.dto.level.MemberLevelUpdateDto;
import cn.xk.xcode.entity.vo.MemberLevelResultVo;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.MemberLevelPo;

/**
 *  服务层。
 *
 * @author lenovo
 * @since 2024-08-05
 */
public interface MemberLevelService extends IService<MemberLevelPo> {

    MemberLevelResultVo getMemberUserLevel(MemberBaseReqDto memberBaseReqDto);

    Boolean addMemberLevel(MemberLevelAddDto memberLevelAddDto);

    Boolean deleteMemberLevel(MemberLevelBaseDto memberLevelBaseDto);

    Boolean updateMemberLevel(MemberLevelUpdateDto memberLevelUpdateDto);
}
