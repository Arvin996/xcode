package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.merchant.AddMerchantDto;
import cn.xk.xcode.entity.dto.merchant.UpdateMerchantDto;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.PayMerchantPo;

/**
 *  服务层。
 *
 * @author Administrator
 * @since 2024-09-23
 */
public interface PayMerchantService extends IService<PayMerchantPo> {

    Boolean addPayMerchant(AddMerchantDto addMerchantDto);

    Boolean updatePayMerchant(UpdateMerchantDto updateMerchantDto);
}
