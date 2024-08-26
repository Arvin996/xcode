package cn.xk.xcode.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.entity.dto.address.MemberAddressAddDto;
import cn.xk.xcode.entity.dto.address.MemberAddressBaseDto;
import cn.xk.xcode.entity.dto.address.MemberAddressUpdateDto;
import cn.xk.xcode.entity.vo.MemberAddressResultVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.MemberAddressPo;
import cn.xk.xcode.mapper.MemberAddressMapper;
import cn.xk.xcode.service.MemberAddressService;
import com.xk.xcode.core.utils.AreaUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cn.xk.xcode.entity.def.MemberAddressTableDef.MEMBER_ADDRESS_PO;
import static cn.xk.xcode.enums.MemberErrorCodeConstants.ADDRESS_NOT_EXISTS;
import static cn.xk.xcode.enums.MemberErrorCodeConstants.SYSTEM_ERROR;

/**
 * 服务层实现。
 *
 * @author lenovo
 * @since 2024-08-05
 */
@Service
public class MemberAddressServiceImpl extends ServiceImpl<MemberAddressMapper, MemberAddressPo> implements MemberAddressService {

    @Override
    public List<MemberAddressResultVo> getMemberAddressList(String userId) {
        List<MemberAddressPo> addressPoList = this.list(MEMBER_ADDRESS_PO.USER_ID.eq(userId));
        return CollectionUtil.convertList(addressPoList, bean -> BeanUtil.toBean(bean, MemberAddressResultVo.class));
    }

    @Override
    public MemberAddressResultVo getMemberDefaultAddress(String userId) {
        QueryWrapper queryWrapper = QueryWrapper.create(MemberAddressPo.class)
                .where(MEMBER_ADDRESS_PO.USER_ID.eq(userId))
                .and(MEMBER_ADDRESS_PO.DEFAULT_ADDRESS.eq("0"));
        MemberAddressResultVo memberAddressResultVo = BeanUtil.toBean(this.getOne(queryWrapper), MemberAddressResultVo.class);
        memberAddressResultVo.setArea(AreaUtils.getArea(memberAddressResultVo.getAreaId()));
        return memberAddressResultVo;
    }

    @Transactional
    @Override
    public Boolean addAddress(MemberAddressAddDto memberAddressAddDto) {
        String userId = StpUtil.getLoginIdAsString();
        // 1. 看是否是默认地址
        String defaultAddress = memberAddressAddDto.getDefaultAddress();
        if ("0".equals(defaultAddress)) {
            // 获取用户默认地址
            MemberAddressPo defaultMemberAddressPo = this.getOne(QueryWrapper.create(MemberAddressPo.class).where(
                    MEMBER_ADDRESS_PO.USER_ID.eq(userId).and(
                            MEMBER_ADDRESS_PO.DEFAULT_ADDRESS.eq("0")
                    )
            ));
            // 处理默认地址
            if (ObjectUtil.isNotNull(defaultMemberAddressPo)) {
                defaultMemberAddressPo.setDefaultAddress("1");
                this.updateById(defaultMemberAddressPo);
            }
        }
        return this.save(BeanUtil.toBean(memberAddressAddDto, MemberAddressPo.class));
    }

    @Override
    public Boolean updateAddress(MemberAddressUpdateDto memberAddressUpdateDto) {
        validateCurrentUser(memberAddressUpdateDto.getId());
        MemberAddressPo memberAddressPo = BeanUtil.toBean(memberAddressUpdateDto, MemberAddressPo.class);
        String userId = StpUtil.getLoginIdAsString();
        return this.update(memberAddressPo, QueryWrapper.create(MemberAddressPo.class).where(
                MEMBER_ADDRESS_PO.ID.eq(memberAddressUpdateDto.getId()).and(
                        MEMBER_ADDRESS_PO.USER_ID.eq(userId)
                )
        ));
    }

    @Override
    public Boolean deleteAddress(MemberAddressBaseDto memberAddressBaseDto) {
        validateCurrentUser(memberAddressBaseDto.getId());
        return this.remove(QueryWrapper.create(MemberAddressPo.class).where(
                MEMBER_ADDRESS_PO.ID.eq(memberAddressBaseDto.getId())
        ).and(MEMBER_ADDRESS_PO.USER_ID.eq(StpUtil.getLoginIdAsString())));
    }

    @Override
    public Boolean setDefaultAddress(MemberAddressBaseDto memberAddressBaseDto) {
        validateCurrentUser(memberAddressBaseDto.getId());
        MemberAddressPo memberAddressPo = this.getById(memberAddressBaseDto.getId());
        if (ObjectUtil.isNull(memberAddressPo)){
            ExceptionUtil.castServerException(ADDRESS_NOT_EXISTS);
        }
        if ("0".equals(memberAddressPo.getDefaultAddress())){
            return true;
        }
        MemberAddressPo defaultAddress = this.getOne(QueryWrapper.create(MemberAddressPo.class).where(
                MEMBER_ADDRESS_PO.USER_ID.eq(StpUtil.getLoginIdAsString())
                        .and(MEMBER_ADDRESS_PO.DEFAULT_ADDRESS.eq("0"))
        ));
        if (ObjectUtil.isNotNull(defaultAddress)){
            defaultAddress.setDefaultAddress("1");
            this.updateById(defaultAddress);
        }
        memberAddressPo.setDefaultAddress("0");
        return this.updateById(memberAddressPo);
    }

    public void validateCurrentUser(Integer id){
        MemberAddressPo memberAddressPo = this.getById(id);
        if (ObjectUtil.isNull(memberAddressPo)){
            ExceptionUtil.castServiceException(ADDRESS_NOT_EXISTS);
        }
        String loginId = StpUtil.getLoginIdAsString();
        if (!loginId.equals(memberAddressPo.getUserId())){
            ExceptionUtil.castServiceException(SYSTEM_ERROR);
        }
    }
}
