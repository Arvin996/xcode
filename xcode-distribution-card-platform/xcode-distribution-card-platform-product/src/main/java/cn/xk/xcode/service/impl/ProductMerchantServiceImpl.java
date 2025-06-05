package cn.xk.xcode.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.CommonStatusEnum;
import cn.xk.xcode.entity.dto.merchant.MerchantLoginDto;
import cn.xk.xcode.entity.dto.merchant.RegisterMerchantDto;
import cn.xk.xcode.entity.dto.merchant.UpdateMerchantDto;
import cn.xk.xcode.enums.CaptchaGenerateType;
import cn.xk.xcode.exception.core.AssertUtil;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.handler.AbstractLoginHandler;
import cn.xk.xcode.handler.LoginHandlerHolder;
import cn.xk.xcode.pojo.LoginInfoDto;
import cn.xk.xcode.pojo.LoginVO;
import cn.xk.xcode.rpc.CaptchaProto;
import cn.xk.xcode.rpc.CaptchaServiceGrpc;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.ProductMerchantPo;
import cn.xk.xcode.mapper.ProductMerchantMapper;
import cn.xk.xcode.service.ProductMerchantService;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static cn.xk.xcode.config.DistributionCardProductErrorCode.*;
import static cn.xk.xcode.entity.def.ProductMerchantTableDef.PRODUCT_MERCHANT_PO;

/**
 * 服务层实现。
 *
 * @author xuk
 * @since 2025-05-30
 */
@Service
public class ProductMerchantServiceImpl extends ServiceImpl<ProductMerchantMapper, ProductMerchantPo> implements ProductMerchantService {

    @GrpcClient("xcode-captcha")
    private CaptchaServiceGrpc.CaptchaServiceBlockingStub captchaServiceBlockingStub;

    @Resource
    private LoginHandlerHolder loginHandlerHolder;

    @Override
    public Boolean registerMerchant(RegisterMerchantDto registerMerchantDto) {
        String username = registerMerchantDto.getUsername();
        if (LogicDeleteManager.execWithoutLogicDelete(() -> this.exists(PRODUCT_MERCHANT_PO.USERNAME.eq(username)))) {
            ExceptionUtil.castServiceException(MERCHANT_USERNAME_ALREADY_EXISTS, username);
        }
        String password = registerMerchantDto.getPassword();
        String confirmPassword = registerMerchantDto.getConfirmPassword();
        if (!password.equals(confirmPassword)) {
            ExceptionUtil.castServiceException(PASSWORD_AND_CONFIRM_PASSWORD_NOT_EQUALS);
        }
        // 验证验证码
        CaptchaProto.CaptchaVerifyRequest.Builder builder = CaptchaProto.CaptchaVerifyRequest.newBuilder();
        builder.setCode(registerMerchantDto.getCode());
        builder.setEmail(registerMerchantDto.getEmail());
        builder.setType("email");
        checkCaptcha(builder);
        ProductMerchantPo productMerchantPo = new ProductMerchantPo();
        productMerchantPo.setUsername(username);
        productMerchantPo.setPassword(password);
        productMerchantPo.setEmail(registerMerchantDto.getEmail());
        return this.save(productMerchantPo);
    }

    @Override
    public Boolean updateMerchant(UpdateMerchantDto updateMerchantDto) {
        ProductMerchantPo productMerchantPo = this.getById(updateMerchantDto.getId());
        AssertUtil.assertNullCastServiceEx(productMerchantPo, MERCHANT_NOT_EXISTS);
        AssertUtil.assertTrueCastServiceEx(CommonStatusEnum.isDisable(productMerchantPo.getStatus()), MERCHANT_IS_DISABLED);
        if (StrUtil.isNotBlank(updateMerchantDto.getNickname())) {
            productMerchantPo.setNickname(updateMerchantDto.getNickname());
        }
        if (StrUtil.isNotBlank(updateMerchantDto.getAvatar())) {
            productMerchantPo.setAvatar(updateMerchantDto.getAvatar());
        }
        return this.updateById(productMerchantPo);
    }

    @Override
    public LoginVO login(MerchantLoginDto merchantLoginDto) {
        AbstractLoginHandler loginHandler = loginHandlerHolder.routeLoginHandler(merchantLoginDto.getLoginType());
        CaptchaProto.CaptchaVerifyRequest.Builder builder = CaptchaProto.CaptchaVerifyRequest.newBuilder();
        builder.setCode(merchantLoginDto.getCode());
        builder.setType(CaptchaGenerateType.PIC.getCode());
        builder.setUuid(merchantLoginDto.getUuid());
        checkCaptcha(builder);
        return loginHandler.Login(LoginInfoDto.builder()
                .code(merchantLoginDto.getCode())
                .username(merchantLoginDto.getUsername())
                .password(merchantLoginDto.getPassword())
                .email(merchantLoginDto.getEmail())
                .loginDevType(merchantLoginDto.getLoginDevType())
                .build());
    }

    private void checkCaptcha(CaptchaProto.CaptchaVerifyRequest.Builder builder) {
        CaptchaProto.CaptchaVerifyResponse captchaVerifyResponse = captchaServiceBlockingStub.verifyCode(builder.build());
        if (!captchaVerifyResponse.getData()) {
            ExceptionUtil.castServiceException0(captchaVerifyResponse.getCode(), captchaVerifyResponse.getMsg());
        }
    }

}
