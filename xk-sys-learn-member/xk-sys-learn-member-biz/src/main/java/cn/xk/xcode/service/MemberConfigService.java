package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.config.MemberConfigAddDto;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.MemberConfigPo;

/**
 *  服务层。
 *
 * @author lenovo
 * @since 2024-08-05
 */
public interface MemberConfigService extends IService<MemberConfigPo> {

    Boolean addMemberConfig(MemberConfigAddDto memberConfigAddDto);

    Boolean updateMemberConfig(MemberConfigAddDto memberConfigAddDto);

    Boolean deleteMemberConfig();
}
