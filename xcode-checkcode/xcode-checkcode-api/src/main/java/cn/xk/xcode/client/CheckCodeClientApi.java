package cn.xk.xcode.client;

import cn.xk.xcode.entity.dto.CheckCodeGenReqDto;
import cn.xk.xcode.entity.dto.CheckCodeVerifyReqDto;
import cn.xk.xcode.entity.vo.CheckCodeGenResultVo;
import cn.xk.xcode.factory.CheckCodeClientApiFallBackFactory;
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
 * @Description CheckCodeController
 */
@FeignClient(value = "xk-learn-checkcode", fallback = CheckCodeClientApiFallBackFactory.class)
@Tag(name = "验证码接口")
@RequestMapping("/checkcode")
public interface CheckCodeClientApi {
    @PostMapping("/genCode")
    @Operation(description = "生成验证码")
    CommonResult<CheckCodeGenResultVo> genCheckCode(@Validated CheckCodeGenReqDto checkCodeGenReqDto);

    @PostMapping("/verifyCode")
    @Operation(description = "校验验证码")
    CommonResult<Boolean> verifyCode(@Validated CheckCodeVerifyReqDto checkCodeVerifyReqDto);
}
