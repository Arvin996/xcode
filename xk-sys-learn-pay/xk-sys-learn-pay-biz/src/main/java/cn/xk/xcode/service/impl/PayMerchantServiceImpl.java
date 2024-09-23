package cn.xk.xcode.service.impl;

import cn.xk.xcode.convert.merchant.MerchantConvert;
import cn.xk.xcode.entity.dto.merchant.AddMerchantDto;
import cn.xk.xcode.entity.dto.merchant.UpdateMerchantDto;
import cn.xk.xcode.exception.core.ExceptionUtil;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.PayMerchantPo;
import cn.xk.xcode.mapper.PayMerchantMapper;
import cn.xk.xcode.service.PayMerchantService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static cn.xk.xcode.entity.def.PayMerchantTableDef.PAY_MERCHANT_PO;
import static cn.xk.xcode.enums.PayModuleErrorCodeConstants.MERCHANT_NAME_ALREADY_EXIST;
import static cn.xk.xcode.enums.PayModuleErrorCodeConstants.MERCHANT_NO_ALREADY_EXIST;

/**
 * 服务层实现。
 *
 * @author Administrator
 * @since 2024-09-23
 */
@Service
public class PayMerchantServiceImpl extends ServiceImpl<PayMerchantMapper, PayMerchantPo> implements PayMerchantService {

    @Override
    public Boolean addPayMerchant(AddMerchantDto addMerchantDto) {
        validate(addMerchantDto.getMerchantNo(), addMerchantDto.getMerchantName());
        return this.save(MerchantConvert.convert(addMerchantDto));
    }

    @Override
    public Boolean updatePayMerchant(UpdateMerchantDto updateMerchantDto) {
        if (this.count(PAY_MERCHANT_PO.ID.ne(updateMerchantDto.getId()).and(
                PAY_MERCHANT_PO.MERCHANT_NAME.eq(updateMerchantDto.getMerchantName())
        )) > 0) {
            ExceptionUtil.castServerException(MERCHANT_NAME_ALREADY_EXIST, updateMerchantDto.getMerchantName());
        }
        return UpdateChain
                .of(PayMerchantPo.class)
                .set(PAY_MERCHANT_PO.MERCHANT_NAME, updateMerchantDto.getMerchantName())
                .set(PAY_MERCHANT_PO.STATUS, updateMerchantDto.getStatus())
                .set(PAY_MERCHANT_PO.REMARK, updateMerchantDto.getRemark())
                .update();
    }

    private void validate(String merchantNo, String merchantName) {
        if (this.count(PAY_MERCHANT_PO.MERCHANT_NO.eq(merchantNo)) > 0) {
            ExceptionUtil.castServerException(MERCHANT_NO_ALREADY_EXIST, merchantNo);
        }
        if (this.count(PAY_MERCHANT_PO.MERCHANT_NAME.eq(merchantName)) > 0) {
            ExceptionUtil.castServerException(MERCHANT_NAME_ALREADY_EXIST, merchantName);
        }
    }
}
