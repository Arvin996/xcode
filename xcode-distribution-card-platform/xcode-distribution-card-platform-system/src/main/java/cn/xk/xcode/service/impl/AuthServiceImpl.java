package cn.xk.xcode.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.entity.dto.user.RegisterUserDto;
import cn.xk.xcode.entity.po.SystemUserPo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.rpc.CaptchaProto;
import cn.xk.xcode.rpc.CaptchaServiceGrpc;
import cn.xk.xcode.service.AuthService;
import cn.xk.xcode.service.SystemUserService;
import cn.xk.xcode.utils.object.BeanUtil;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static cn.xk.xcode.config.DistributionCardSystemErrorCode.PASSWORD_CONFIRM_NOT_EQUAL;
import static cn.xk.xcode.config.DistributionCardSystemErrorCode.USERNAME_ALREADY_EXISTS;
import static cn.xk.xcode.entity.def.SystemUserTableDef.SYSTEM_USER_PO;

/**
 * @Author xuk
 * @Date 2025/5/9 10:56
 * @Version 1.0.0
 * @Description AuthServiceImpl
 **/
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private SystemUserService systemUserService;

    @GrpcClient("captcha-server")
    private CaptchaServiceGrpc.CaptchaServiceBlockingStub captchaServiceBlockingStub;

    private static final String DEFAULT_AVATAR = "XXX";

    @Override
    public Boolean register(RegisterUserDto registerUserDto) {
        if (systemUserService.exists(SYSTEM_USER_PO.USERNAME.eq(registerUserDto.getUsername()))) {
            ExceptionUtil.castServiceException(USERNAME_ALREADY_EXISTS);
        }
        if (!registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
            ExceptionUtil.castServiceException(PASSWORD_CONFIRM_NOT_EQUAL);
        }
        if (StrUtil.isBlank(registerUserDto.getNickname())) {
            // 随机生成一个昵称
            registerUserDto.setNickname(IdUtil.fastSimpleUUID());
        }
        if (StrUtil.isBlank(registerUserDto.getAvatar())) {
            registerUserDto.setAvatar(DEFAULT_AVATAR);
        }
        // 验证验证码
        CaptchaProto.CaptchaVerifyRequest.Builder builder = CaptchaProto.CaptchaVerifyRequest.newBuilder();
        builder.setCode(registerUserDto.getCode());
        builder.setEmail(registerUserDto.getEmail());
        builder.setType(registerUserDto.getType());
        builder.setPhone(registerUserDto.getMobile());
        CaptchaProto.CaptchaVerifyResponse captchaVerifyResponse = captchaServiceBlockingStub.verifyCode(builder.build());
        if (!captchaVerifyResponse.getData()){
            ExceptionUtil.castServiceException0(captchaVerifyResponse.getCode(), captchaVerifyResponse.getMsg());
        }
        return systemUserService.save(BeanUtil.toBean(registerUserDto, SystemUserPo.class));
    }
}
