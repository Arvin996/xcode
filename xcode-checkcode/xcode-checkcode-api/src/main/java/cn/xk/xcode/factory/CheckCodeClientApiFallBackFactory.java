package cn.xk.xcode.factory;

import cn.xk.xcode.client.CheckCodeClientApi;
import cn.xk.xcode.entity.dto.CheckCodeGenReqDto;
import cn.xk.xcode.entity.dto.CheckCodeVerifyReqDto;
import cn.xk.xcode.entity.vo.CheckCodeGenResultVo;
import cn.xk.xcode.pojo.CommonResult;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * @Author xuk
 * @Date 2024/8/3 12:42
 * @Version 1.0
 * @Description CheckCodeClientApiFallBack
 */
public class CheckCodeClientApiFallBackFactory implements FallbackFactory<CheckCodeClientApi> {

    @Override
    public CheckCodeClientApi create(Throwable cause) {
        return new CheckCodeClientApi() {
            @Override
            public CommonResult<CheckCodeGenResultVo> genCheckCode(CheckCodeGenReqDto checkCodeGenReqDto) {
                return CommonResult.error(500, "服务熔断");
            }

            @Override
            public CommonResult<Boolean> verifyCode(CheckCodeVerifyReqDto checkCodeVerifyReqDto) {
                return CommonResult.error(500, "服务熔断");
            }
        };
    }
}
