package cn.xk.xcode.controller;

import cn.xk.xcode.client.CheckCodeClientApi;
import cn.xk.xcode.entity.dto.CheckCodeGenReqDto;
import cn.xk.xcode.entity.dto.CheckCodeVerifyReqDto;
import cn.xk.xcode.entity.vo.CheckCodeGenResultVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.CheckCodeService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2024/8/3 12:45
 * @Version 1.0
 * @Description CheckCodeController
 */
@Controller
public class CheckCodeController implements CheckCodeClientApi {

    @Resource
    private CheckCodeService checkCodeService;

    @Override
    public CommonResult<CheckCodeGenResultVo> genCheckCode(CheckCodeGenReqDto checkCodeGenReqDto) {
        return checkCodeService.genCheckCode(checkCodeGenReqDto);
    }

    @Override
    public CommonResult<Boolean> verifyCode(CheckCodeVerifyReqDto checkCodeVerifyReqDto) {
        return checkCodeService.verifyCode(checkCodeVerifyReqDto);
    }
}
