package cn.xk.xcode.controller;

import cn.xk.xcode.entity.dto.CaptchaGenReqDto;
import cn.xk.xcode.entity.dto.CaptchaVerifyReqDto;
import cn.xk.xcode.entity.vo.CaptchaGenResultVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.CaptchaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2024/8/3 12:45
 * @Version 1.0
 * @Description CaptchaController
 */
@RestController
@RequestMapping("/Captcha")
@Tag(name = "CaptchaController", description = "验证码相关接口")
public class CaptchaController {

    @Resource
    private CaptchaService CaptchaService;

    @PostMapping("/genCode")
    @Operation(summary = "生成验证码")
    public CommonResult<CaptchaGenResultVo> genCaptcha(@Validated @RequestBody CaptchaGenReqDto CaptchaGenReqDto) {
        return CaptchaService.genCaptcha(CaptchaGenReqDto);
    }

    @PostMapping("/verifyCode")
    @Operation(summary = "验证验证码")
    public CommonResult<Boolean> verifyCode(@Validated @RequestBody CaptchaVerifyReqDto CaptchaVerifyReqDto) {
        return CaptchaService.verifyCode(CaptchaVerifyReqDto);
    }
}
