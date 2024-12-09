package cn.xk.xcode.controller;

import cn.xk.xcode.client.CaptchaClientApi;
import cn.xk.xcode.entity.dto.CaptchaGenReqDto;
import cn.xk.xcode.entity.dto.CaptchaVerifyReqDto;
import cn.xk.xcode.entity.vo.CaptchaGenResultVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.CaptchaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2024/8/3 12:45
 * @Version 1.0
 * @Description CaptchaController
 */
@Controller
@RequestMapping("/Captcha")
public class CaptchaController implements CaptchaClientApi {

    @Resource
    private CaptchaService CaptchaService;

    @Override
    @PostMapping("/genCode")
    public CommonResult<CaptchaGenResultVo> genCaptcha(CaptchaGenReqDto CaptchaGenReqDto) {
        return CaptchaService.genCaptcha(CaptchaGenReqDto);
    }

    @Override
    @PostMapping("/verifyCode")
    public CommonResult<Boolean> verifyCode(CaptchaVerifyReqDto CaptchaVerifyReqDto) {
        return CaptchaService.verifyCode(CaptchaVerifyReqDto);
    }
}
