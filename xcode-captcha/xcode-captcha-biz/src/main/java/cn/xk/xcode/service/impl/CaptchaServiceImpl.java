package cn.xk.xcode.service.impl;

import cn.xk.xcode.entity.dto.CaptchaGenReqDto;
import cn.xk.xcode.entity.dto.CaptchaVerifyReqDto;
import cn.xk.xcode.entity.vo.CaptchaGenResultVo;
import cn.xk.xcode.handler.CaptchaHandlerStrategy;
import cn.xk.xcode.handler.core.CaptchaAdvisor;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.CaptchaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2024/8/3 12:47
 * @Version 1.0
 * @Description CaptchaServiceImpl
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Resource
    private CaptchaAdvisor CaptchaAdvisor;

    @Override
    public CommonResult<CaptchaGenResultVo> genCaptcha(CaptchaGenReqDto CaptchaGenReqDto) {
        CaptchaHandlerStrategy CaptchaHandlerStrategy = CaptchaAdvisor.getCaptchaHandlerStrategy(CaptchaGenReqDto.getType());
        return CommonResult.success(CaptchaHandlerStrategy.doGenerateCode(CaptchaGenReqDto));
    }

    @Override
    public CommonResult<Boolean> verifyCode(CaptchaVerifyReqDto CaptchaVerifyReqDto) {
        CaptchaHandlerStrategy CaptchaHandlerStrategy = CaptchaAdvisor.getCaptchaHandlerStrategy(CaptchaVerifyReqDto.getType());
        return CaptchaHandlerStrategy.verifyCode(CaptchaVerifyReqDto);
    }
}
