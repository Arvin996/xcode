package cn.xk.xcode.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.StpSystemUtil;
import cn.xk.xcode.entity.dto.user.LoginUserDto;
import cn.xk.xcode.entity.dto.user.RegisterUserDto;
import cn.xk.xcode.entity.po.SystemUserPo;
import cn.xk.xcode.enums.CaptchaGenerateType;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.handler.AbstractLoginHandler;
import cn.xk.xcode.pojo.LoginInfoDto;
import cn.xk.xcode.pojo.LoginVO;
import cn.xk.xcode.rpc.CaptchaProto;
import cn.xk.xcode.rpc.CaptchaServiceGrpc;
import cn.xk.xcode.service.AuthService;
import cn.xk.xcode.service.SystemUserService;
import cn.xk.xcode.utils.object.BeanUtil;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
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

    @Resource
    private AbstractLoginHandler loginHandler;

    @GrpcClient("xcode-captcha")
    private CaptchaServiceGrpc.CaptchaServiceBlockingStub captchaServiceBlockingStub;

    private static final String DEFAULT_AVATAR = "XXX";

    @Override
    public Boolean register(RegisterUserDto registerUserDto) {
        if (LogicDeleteManager.execWithoutLogicDelete(() -> systemUserService.exists(SYSTEM_USER_PO.USERNAME.eq(registerUserDto.getUsername())))) {
            ExceptionUtil.castServiceException(USERNAME_ALREADY_EXISTS);
        }
        if (!registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
            ExceptionUtil.castServiceException(PASSWORD_CONFIRM_NOT_EQUAL);
        }
        if (StrUtil.isBlank(registerUserDto.getNickname())) {
            // 随机生成一个昵称
            registerUserDto.setNickname(IdUtil.fastSimpleUUID().replace("-", ""));
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
        checkCaptcha(builder);
        return systemUserService.save(BeanUtil.toBean(registerUserDto, SystemUserPo.class));
    }

    @Override
    public LoginVO Login(LoginUserDto loginUserDto) {
        CaptchaProto.CaptchaVerifyRequest.Builder builder = CaptchaProto.CaptchaVerifyRequest.newBuilder();
        builder.setCode(loginUserDto.getCode());
        builder.setType(CaptchaGenerateType.PIC.getCode());
        checkCaptcha(builder);
        return loginHandler.Login(LoginInfoDto.builder()
                .code(loginUserDto.getCode())
                .username(loginUserDto.getUsername())
                .password(loginUserDto.getPassword())
                .loginDevType(loginUserDto.getLoginDevType())
                .build());
    }

    @Override
    public Boolean logout() {
        StpSystemUtil.logout();
        // todo ws
        return null;
    }

    private void checkCaptcha(CaptchaProto.CaptchaVerifyRequest.Builder builder) {
        CaptchaProto.CaptchaVerifyResponse captchaVerifyResponse = captchaServiceBlockingStub.verifyCode(builder.build());
        if (!captchaVerifyResponse.getData()) {
            ExceptionUtil.castServiceException0(captchaVerifyResponse.getCode(), captchaVerifyResponse.getMsg());
        }
    }
}
