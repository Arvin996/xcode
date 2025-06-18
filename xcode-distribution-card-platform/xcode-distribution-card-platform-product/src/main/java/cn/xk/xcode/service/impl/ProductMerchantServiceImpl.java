package cn.xk.xcode.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.CommonStatusEnum;
import cn.xk.xcode.entity.dto.merchant.MerchantLoginDto;
import cn.xk.xcode.entity.dto.merchant.RegisterMerchantDto;
import cn.xk.xcode.entity.dto.merchant.UpdateMerchantDto;
import cn.xk.xcode.entity.po.ProductMerchantThirdPo;
import cn.xk.xcode.enums.*;
import cn.xk.xcode.exception.core.AssertUtil;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.handler.AbstractLoginHandler;
import cn.xk.xcode.handler.LoginHandlerHolder;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.LoginInfoDto;
import cn.xk.xcode.pojo.LoginVO;
import cn.xk.xcode.rpc.CaptchaProto;
import cn.xk.xcode.rpc.CaptchaServiceGrpc;
import cn.xk.xcode.rpc.SendMessageProto;
import cn.xk.xcode.rpc.SendMessageTaskServiceGrpc;
import cn.xk.xcode.service.ProductMerchantThirdService;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.ProductMerchantPo;
import cn.xk.xcode.mapper.ProductMerchantMapper;
import cn.xk.xcode.service.ProductMerchantService;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

import java.util.HashMap;

import static cn.xk.xcode.config.DistributionCardProductErrorCode.*;
import static cn.xk.xcode.entity.def.ProductMerchantTableDef.PRODUCT_MERCHANT_PO;
import static cn.xk.xcode.entity.def.ProductMerchantThirdTableDef.PRODUCT_MERCHANT_THIRD_PO;
import static cn.xk.xcode.enums.CaptchaGlobalErrorCodeConstants.SEND_CAPTCHA_ERROR;

/**
 * 服务层实现。
 *
 * @author xuk
 * @since 2025-05-30
 */
@Service
@Slf4j
public class ProductMerchantServiceImpl extends ServiceImpl<ProductMerchantMapper, ProductMerchantPo> implements ProductMerchantService {

    @GrpcClient("xcode-captcha")
    private CaptchaServiceGrpc.CaptchaServiceBlockingStub captchaServiceBlockingStub;

    @GrpcClient("xcode-message")
    private SendMessageTaskServiceGrpc.SendMessageTaskServiceBlockingStub sendMessageTaskServiceBlockingStub;

    @Resource
    private LoginHandlerHolder loginHandlerHolder;

    @Resource
    private ProductMerchantThirdService productMerchantThirdService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    @Transactional
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
        if (this.exists(PRODUCT_MERCHANT_PO.EMAIL.eq(registerMerchantDto.getEmail()))) {
            ExceptionUtil.castServiceException(MERCHANT_EMAIL_ALREADY_EXISTS, registerMerchantDto.getEmail());
        }
        productMerchantPo.setEmail(registerMerchantDto.getEmail());
        this.save(productMerchantPo);
        if (StrUtil.isNotBlank(registerMerchantDto.getUnionId())) {
            if (StrUtil.isBlank(registerMerchantDto.getThirdLoginType())) {
                throw new ServerException(LOGIN_PARAMS_ERROR);
            }
            ProductMerchantThirdPo productMerchantThirdPo = productMerchantThirdService.getOne(PRODUCT_MERCHANT_THIRD_PO.UNION_ID.eq(registerMerchantDto.getUnionId()));
            if (ObjectUtil.isNull(productMerchantThirdPo)) {
                productMerchantThirdPo = new ProductMerchantThirdPo();
                productMerchantThirdPo.setMerchantId(productMerchantPo.getId());
                productMerchantThirdPo.setType(registerMerchantDto.getThirdLoginType());
                productMerchantThirdPo.setUnionId(registerMerchantDto.getUnionId());
                return productMerchantThirdService.save(productMerchantThirdPo);
            } else {
                productMerchantThirdPo.setMerchantId(productMerchantPo.getId());
                return productMerchantThirdService.updateById(productMerchantThirdPo);
            }
        }
        return true;
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
        if (StrUtil.isNotBlank(merchantLoginDto.getCode())) {
            CaptchaProto.CaptchaVerifyRequest.Builder builder = CaptchaProto.CaptchaVerifyRequest.newBuilder();
            builder.setCode(merchantLoginDto.getCode());
            builder.setType(CaptchaGenerateType.PIC.getCode());
            builder.setUuid(merchantLoginDto.getUuid());
            checkCaptcha(builder);
        }
        return loginHandler.Login(LoginInfoDto.builder()
                .code(merchantLoginDto.getCode())
                .username(merchantLoginDto.getUsername())
                .password(merchantLoginDto.getPassword())
                .email(merchantLoginDto.getEmail())
                .loginDevType(merchantLoginDto.getLoginDevType())
                .build());
    }

    @Override
    public Boolean sendFindPasswordEmail(String email) {
        // 发送邮件
        SendMessageProto.SendMessageTaskRequest.Builder builder = SendMessageProto.SendMessageTaskRequest.newBuilder();
        builder.setClientAccessToken("sksjnga212sa238hojm");
        builder.setAccountName("系统用户邮箱");
        builder.setShieldType(ShieldType.NIGHT_NO_SHIELD.getCode());
        builder.setMsgType(MessageSendType.NOW.getCode());
        builder.setMsgChannel(MessageChannelEnum.EMAIL.getCode());
        builder.setMsgContentType(MessageContentType.TEMPLATE.getCode());
        builder.setTemplateId("ssrsd4552ffdfer22");
        String url = "http://192.168.1.7:4000/xcode-dc-product/api/product/merchant/findPassword";
        url = url + "?email=" + email;
        String token = IdUtil.fastUUID();
        url = url + "&token=" + token;
        stringRedisTemplate.opsForValue().set(FIND_PASSWORD_TOKEN_PREFIX + email, token, FIND_PASSWORD_TOKEN_EXPIRE_TIME);
        HashMap<String, String> map = MapUtil.of("url", url);
        builder.setContentValueParams(JSON.toJSONString(map));
        builder.setReceiverType(ReceiverTypeEnum.PLAIN.getCode());
        builder.setReceivers(email);
        try {
            SendMessageProto.SendMessageTaskResponse response = sendMessageTaskServiceBlockingStub.sendMessageTask(builder.build());
            if (!CommonResult.isSuccess(response.getCode())) {
                String msg = response.getMsg();
                ExceptionUtil.castServerException0(response.getCode(), msg);
            } else {
                SendMessageProto.SendMessageResponse sendMessageResponse = response.getSendMessageResponse();
                int failCount = sendMessageResponse.getFailCount();
                if (failCount > 0) {
                    log.error("发送邮件失败:{}", sendMessageResponse.getFailMessageDetailList(0));
                    ExceptionUtil.castServiceException(SEND_EMAIL_ERROR);
                }
            }
        } catch (Exception e) {
            log.error("发送邮件失败:{}", e.getMessage());
            ExceptionUtil.castServiceException(SEND_EMAIL_ERROR);
        }
        return null;
    }

    @Override
    public ModelAndView findPassword(String email, String token) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        String msg = "操作成功";
        if (!stringRedisTemplate.hasKey(FIND_PASSWORD_TOKEN_PREFIX + email)) {
            msg = "链接已失效";
            mv.addObject("msg", msg);
            mv.addObject("flag", false);
            return mv;
        }
        stringRedisTemplate.delete(FIND_PASSWORD_TOKEN_PREFIX + email);
        ProductMerchantPo productMerchantPo = this.getOne(PRODUCT_MERCHANT_PO.EMAIL.eq(email));
        if (ObjectUtil.isNull(productMerchantPo)) {
            msg = "邮件未注册";
            mv.addObject("msg", msg);
            mv.addObject("flag", false);
            return mv;
        }
        if (CommonStatusEnum.isDisable(productMerchantPo.getStatus())) {
            msg = "用户已被禁用，无法找回密码";
            mv.addObject("msg", msg);
            mv.addObject("flag", false);
            return mv;
        }
        String newPassword = IdUtil.randomUUID().replace("-", "");
        // 发送邮件
        SendMessageProto.SendMessageTaskRequest.Builder builder = SendMessageProto.SendMessageTaskRequest.newBuilder();
        builder.setClientAccessToken("sksjnga212sa238hojm");
        builder.setAccountName("系统用户邮箱");
        builder.setShieldType(ShieldType.NIGHT_NO_SHIELD.getCode());
        builder.setMsgType(MessageSendType.NOW.getCode());
        builder.setMsgChannel(MessageChannelEnum.EMAIL.getCode());
        builder.setMsgContentType(MessageContentType.TEMPLATE.getCode());
        builder.setTemplateId("sfyb45123fasv");
        HashMap<String, String> map = MapUtil.of("password", newPassword);
        builder.setContentValueParams(JSON.toJSONString(map));
        builder.setReceiverType(ReceiverTypeEnum.PLAIN.getCode());
        builder.setReceivers(email);
        try {
            SendMessageProto.SendMessageTaskResponse response = sendMessageTaskServiceBlockingStub.sendMessageTask(builder.build());
            if (!CommonResult.isSuccess(response.getCode())) {
                log.error("发送邮件失败:{}", response.getMsg());
                msg = "系统异常";
                mv.addObject("msg", msg);
                mv.addObject("flag", false);
                return mv;
            } else {
                SendMessageProto.SendMessageResponse sendMessageResponse = response.getSendMessageResponse();
                int failCount = sendMessageResponse.getFailCount();
                if (failCount > 0) {
                    log.error("发送邮件失败:{}", sendMessageResponse.getFailMessageDetailList(0));
                    msg = "系统异常";
                    mv.addObject("msg", msg);
                    mv.addObject("flag", false);
                    return mv;
                }
            }
        } catch (Exception e) {
            log.error("发送邮件失败:{}", e.getMessage());
            msg = "系统异常";
            mv.addObject("msg", msg);
            mv.addObject("flag", false);
            return mv;
        }
        productMerchantPo.setPassword(newPassword);
        this.updateById(productMerchantPo);
        mv.addObject("msg", newPassword);
        mv.addObject("flag", true);
        return mv;
    }

    private void checkCaptcha(CaptchaProto.CaptchaVerifyRequest.Builder builder) {
        CaptchaProto.CaptchaVerifyResponse captchaVerifyResponse = captchaServiceBlockingStub.verifyCode(builder.build());
        if (!captchaVerifyResponse.getData()) {
            ExceptionUtil.castServiceException0(captchaVerifyResponse.getCode(), captchaVerifyResponse.getMsg());
        }
    }

}
