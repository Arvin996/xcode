package cn.xk.xcode.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.entity.dto.sign.MemberSignAddDto;
import cn.xk.xcode.entity.dto.sign.MemberSignUpdateDto;
import cn.xk.xcode.entity.vo.sign.MemberSignResultVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.MemberSignPo;
import cn.xk.xcode.mapper.MemberSignMapper;
import cn.xk.xcode.service.MemberSignService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static cn.xk.xcode.enums.MemberErrorCodeConstants.SIGN_IN_CONFIG_EXISTS;
import static cn.xk.xcode.enums.MemberErrorCodeConstants.SIGN_IN_CONFIG_NOT_EXISTS;

/**
 * 服务层实现。
 *
 * @author lenovo
 * @since 2024-08-05
 */
@Service
public class MemberSignServiceImpl extends ServiceImpl<MemberSignMapper, MemberSignPo> implements MemberSignService {

    @Override
    public Boolean addSign(MemberSignAddDto memberSignAddDto) {
        if (!ObjectUtil.isNull(this.getById(memberSignAddDto.getDay()))) {
            ExceptionUtil.castServiceException(SIGN_IN_CONFIG_EXISTS, memberSignAddDto.getDay());
        }
        return this.save(BeanUtil.toBean(memberSignAddDto, MemberSignPo.class));
    }

    @Override
    public Boolean updateSignRule(MemberSignUpdateDto memberSignUpdateDto) {
        if (ObjectUtil.isNull(this.getById(memberSignUpdateDto.getDay()))){
            ExceptionUtil.castServiceException(SIGN_IN_CONFIG_NOT_EXISTS, memberSignUpdateDto.getDay());
        }
        return this.updateById(BeanUtil.toBean(memberSignUpdateDto, MemberSignPo.class));
    }

    @Override
    public List<MemberSignResultVo> getSignRule() {
        return CollectionUtil.convertList(this.list(), bean -> BeanUtil.toBean(bean, MemberSignResultVo.class));
    }
}
