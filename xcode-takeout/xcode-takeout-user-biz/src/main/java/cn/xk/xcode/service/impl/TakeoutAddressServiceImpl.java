package cn.xk.xcode.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.xk.xcode.entity.dto.address.AddAddressDto;
import cn.xk.xcode.entity.dto.address.AddressBaseDto;
import cn.xk.xcode.entity.dto.address.UpdateAddressDto;
import cn.xk.xcode.entity.vo.TakeoutAddressResultVo;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.TakeoutAddressPo;
import cn.xk.xcode.mapper.TakeoutAddressMapper;
import cn.xk.xcode.service.TakeoutAddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cn.xk.xcode.entity.def.TakeoutAddressTableDef.TAKEOUT_ADDRESS_PO;

/**
 * 服务层实现。
 *
 * @author Administrator
 * @since 2024-10-29
 */
@Service
public class TakeoutAddressServiceImpl extends ServiceImpl<TakeoutAddressMapper, TakeoutAddressPo> implements TakeoutAddressService {

    @Override
    public List<TakeoutAddressResultVo> getTakeoutAddressList(Long userId) {
        List<TakeoutAddressPo> takeoutAddressPos = this.list(TAKEOUT_ADDRESS_PO.USER_ID.eq(userId));
        return CollectionUtil.convertList(takeoutAddressPos, v -> BeanUtil.toBean(v, TakeoutAddressResultVo.class));
    }

    @Override
    public TakeoutAddressResultVo getTakeoutDefaultAddress(Long userId) {
        TakeoutAddressPo takeoutAddressPo = this.getOne(TAKEOUT_ADDRESS_PO.USER_ID.eq(userId).and(TAKEOUT_ADDRESS_PO.IS_DEFAULT.eq(1)));
        if (takeoutAddressPo == null) {
            return null;
        }
        return BeanUtil.toBean(takeoutAddressPo, TakeoutAddressResultVo.class);
    }

    @Transactional
    @Override
    public Boolean addTakeoutAddress(AddAddressDto addAddressDto) {
        long userId = StpUtil.getLoginIdAsLong();
        TakeoutAddressPo takeoutAddressPo = BeanUtil.toBean(addAddressDto, TakeoutAddressPo.class);
        takeoutAddressPo.setUserId(userId);
        if (takeoutAddressPo.getIsDefault() == 1) {
            takeoutAddressPo.setIsDefault(0);
            this.save(takeoutAddressPo);
            getSelf().setDefaultAddress(userId, takeoutAddressPo.getId());
            return true;
        } else {
            return this.save(takeoutAddressPo);
        }
    }

    @Override
    public Boolean updateTakeoutAddress(UpdateAddressDto updateAddressDto) {
        TakeoutAddressPo takeoutAddressPo = BeanUtil.toBean(updateAddressDto, TakeoutAddressPo.class);
        return this.updateById(takeoutAddressPo);
    }

    @Override
    public Boolean setDefaultTakeoutAddress(AddressBaseDto addressBaseDto) {
        long userId = StpUtil.getLoginIdAsLong();
        getSelf().setDefaultAddress(userId, addressBaseDto.getId());
        return true;
    }

    @Transactional
    public void setDefaultAddress(Long userId, Long addressId) {
        TakeoutAddressPo takeoutAddressPo = this.getOne(TAKEOUT_ADDRESS_PO.USER_ID.eq(userId).and(TAKEOUT_ADDRESS_PO.IS_DEFAULT.eq(1)));
        if (!ObjectUtil.isNull(takeoutAddressPo)) {
            UpdateChain.of(TakeoutAddressPo.class)
                    .set(TAKEOUT_ADDRESS_PO.IS_DEFAULT, 0)
                    .where(TAKEOUT_ADDRESS_PO.ID.eq(takeoutAddressPo.getId()));

        }
        UpdateChain.of(TakeoutAddressPo.class)
                .set(TAKEOUT_ADDRESS_PO.IS_DEFAULT, 1)
                .where(TAKEOUT_ADDRESS_PO.ID.eq(addressId))
                .update();
    }

    private TakeoutAddressServiceImpl getSelf() {
        return SpringUtil.getBean(this.getClass());
    }
}
