package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.sign.MemberSignAddDto;
import cn.xk.xcode.entity.dto.sign.MemberSignUpdateDto;
import cn.xk.xcode.entity.vo.sign.MemberSignResultVo;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.MemberSignPo;

import java.util.List;

/**
 *  服务层。
 *
 * @author lenovo
 * @since 2024-08-05
 */
public interface MemberSignService extends IService<MemberSignPo> {

    Boolean addSign(MemberSignAddDto memberSignAddDto);

    Boolean updateSignRule(MemberSignUpdateDto memberSignUpdateDto);

    List<MemberSignResultVo> getSignRule();
}
