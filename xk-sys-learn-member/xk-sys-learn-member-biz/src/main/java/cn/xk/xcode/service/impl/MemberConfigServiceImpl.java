package cn.xk.xcode.service.impl;

import cn.xk.xcode.entity.dto.config.MemberConfigAddDto;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.MemberConfigPo;
import cn.xk.xcode.mapper.MemberConfigMapper;
import cn.xk.xcode.service.MemberConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.xk.xcode.enums.MemberErrorCodeConstants.POINT_CONFIG_ALREADY_EXISTS;
import static cn.xk.xcode.enums.MemberErrorCodeConstants.POINT_CONFIG_NOT_EXISTS;

/**
 *  服务层实现。
 *
 * @author lenovo
 * @since 2024-08-05
 */
@Service
public class MemberConfigServiceImpl extends ServiceImpl<MemberConfigMapper, MemberConfigPo> implements MemberConfigService {

    @Override
    public Boolean addMemberConfig(MemberConfigAddDto memberConfigAddDto) {
        long count = this.count();
        if (count > 0) {
            ExceptionUtil.castServiceException(POINT_CONFIG_ALREADY_EXISTS);
        }
        return this.save(BeanUtil.toBean(memberConfigAddDto, MemberConfigPo.class));
    }

    @Override
    public Boolean updateMemberConfig(MemberConfigAddDto memberConfigAddDto) {
        long count = this.count();
        if (count == 0){
            ExceptionUtil.castServiceException(POINT_CONFIG_NOT_EXISTS);
        }
        MemberConfigPo memberConfigPo = this.list().get(0);
        memberConfigPo.setGivenPointAdd(memberConfigPo.getGivenPointAdd());
        memberConfigPo.setMaxPointDeduct(memberConfigPo.getMaxPointDeduct());
        memberConfigPo.setPointDeductEnable(memberConfigPo.getPointDeductEnable());
        memberConfigPo.setMaxPointDeduct(memberConfigPo.getMaxPointDeduct());
        return this.updateById(memberConfigPo);
    }

    @Override
    public Boolean deleteMemberConfig() {
        List<MemberConfigPo> list = this.list();
        if (list.isEmpty()){
            ExceptionUtil.castServiceException(POINT_CONFIG_NOT_EXISTS);
        }
        return this.removeById(list.get(0).getId());
    }
}
