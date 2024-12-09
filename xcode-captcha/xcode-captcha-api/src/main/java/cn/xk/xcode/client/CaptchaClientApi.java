package cn.xk.xcode.client;

import cn.xk.xcode.entity.dto.CaptchaGenReqDto;
import cn.xk.xcode.entity.dto.CaptchaVerifyReqDto;
import cn.xk.xcode.entity.vo.CaptchaGenResultVo;
import cn.xk.xcode.factory.CaptchaClientApiFallBackFactory;
import cn.xk.xcode.pojo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author xuk
 * @Date 2024/8/3 12:34
 * @Version 1.0
 * @Description CaptchaController
 */
@FeignClient(value = "xcode-captcha", fallback = CaptchaClientApiFallBackFactory.class)
@Tag(name = "验证码接口")
@RequestMapping("/captcha")
public interface CaptchaClientApi {
    @PostMapping("/genCode")
    @Operation(description = "生成验证码")
    CommonResult<CaptchaGenResultVo> genCaptcha(@Validated CaptchaGenReqDto CaptchaGenReqDto);

    @PostMapping("/verifyCode")
    @Operation(description = "校验验证码")
    CommonResult<Boolean> verifyCode(@Validated CaptchaVerifyReqDto CaptchaVerifyReqDto);
}
