package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.address.AddAddressDto;
import cn.xk.xcode.entity.dto.address.AddressBaseDto;
import cn.xk.xcode.entity.dto.address.UpdateAddressDto;
import cn.xk.xcode.entity.vo.TakeoutAddressResultVo;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.TakeoutAddressPo;

import java.util.List;

/**
 *  服务层。
 *
 * @author Administrator
 * @since 2024-10-29
 */
public interface TakeoutAddressService extends IService<TakeoutAddressPo> {

    List<TakeoutAddressResultVo> getTakeoutAddressList(Long userId);

    TakeoutAddressResultVo getTakeoutDefaultAddress(Long userId);

    Boolean addTakeoutAddress(AddAddressDto addAddressDto);

    Boolean updateTakeoutAddress(UpdateAddressDto updateAddressDto);

    Boolean setDefaultTakeoutAddress(AddressBaseDto addressBaseDto);
}
