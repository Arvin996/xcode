package cn.xk.xcode.factory;

import cn.xk.xcode.client.CaptchaClientApi;
import cn.xk.xcode.entity.dto.CaptchaGenReqDto;
import cn.xk.xcode.entity.dto.CaptchaVerifyReqDto;
import cn.xk.xcode.entity.vo.CaptchaGenResultVo;
import cn.xk.xcode.pojo.CommonResult;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * @Author xuk
 * @Date 2024/8/3 12:42
 * @Version 1.0
 * @Description CaptchaClientApiFallBack
 */
public class CaptchaClientApiFallBackFactory implements FallbackFactory<CaptchaClientApi> {

    @Override
    public CaptchaClientApi create(Throwable cause) {
        return new CaptchaClientApi() {
            @Override
            public CommonResult<CaptchaGenResultVo> genCaptcha(CaptchaGenReqDto CaptchaGenReqDto) {
                return CommonResult.error(500, "服务熔断");
            }

            @Override
            public CommonResult<Boolean> verifyCode(CaptchaVerifyReqDto CaptchaVerifyReqDto) {
                return CommonResult.error(500, "服务熔断");
            }
        };
    }
}
