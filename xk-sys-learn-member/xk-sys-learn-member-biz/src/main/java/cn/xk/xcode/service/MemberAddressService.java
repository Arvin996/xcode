package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.address.MemberAddressAddDto;
import cn.xk.xcode.entity.dto.address.MemberAddressBaseDto;
import cn.xk.xcode.entity.dto.address.MemberAddressUpdateDto;
import cn.xk.xcode.entity.vo.MemberAddressResultVo;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.MemberAddressPo;

import java.util.List;

/**
 *  服务层。
 *
 * @author lenovo
 * @since 2024-08-05
 */
public interface MemberAddressService extends IService<MemberAddressPo> {

    List<MemberAddressResultVo> getMemberAddressList(String userId);

    MemberAddressResultVo getMemberDefaultAddress(String userId);

    Boolean addAddress(MemberAddressAddDto memberAddressAddDto);

    Boolean updateAddress(MemberAddressUpdateDto memberAddressUpdateDto);

    Boolean deleteAddress(MemberAddressBaseDto memberAddressBaseDto);

    Boolean setDefaultAddress(MemberAddressBaseDto memberAddressBaseDto);
}
