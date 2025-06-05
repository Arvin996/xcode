package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.merchant.MerchantLoginDto;
import cn.xk.xcode.entity.dto.merchant.RegisterMerchantDto;
import cn.xk.xcode.entity.dto.merchant.UpdateMerchantDto;
import cn.xk.xcode.pojo.LoginVO;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.ProductMerchantPo;

/**
 *  服务层。
 *
 * @author xuk
 * @since 2025-05-30
 */
public interface ProductMerchantService extends IService<ProductMerchantPo> {

    Boolean registerMerchant(RegisterMerchantDto registerMerchantDto);

    Boolean updateMerchant(UpdateMerchantDto updateMerchantDto);

    LoginVO login(MerchantLoginDto merchantLoginDto);
}
