package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.CaptchaGenReqDto;
import cn.xk.xcode.entity.dto.CaptchaVerifyReqDto;
import cn.xk.xcode.entity.vo.CaptchaGenResultVo;
import cn.xk.xcode.pojo.CommonResult;

/**
 * @Author xuk
 * @Date 2024/8/3 12:47
 * @Version 1.0
 * @Description CaptchaService
 */
public interface CaptchaService {
    CommonResult<CaptchaGenResultVo> genCaptcha(CaptchaGenReqDto CaptchaGenReqDto);

    CommonResult<Boolean> verifyCode(CaptchaVerifyReqDto CaptchaVerifyReqDto);
}
