package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.CheckCodeGenReqDto;
import cn.xk.xcode.entity.dto.CheckCodeVerifyReqDto;
import cn.xk.xcode.entity.vo.CheckCodeGenResultVo;
import cn.xk.xcode.pojo.CommonResult;

/**
 * @Author xuk
 * @Date 2024/8/3 12:47
 * @Version 1.0
 * @Description CheckCodeService
 */
public interface CheckCodeService
{

    CommonResult<CheckCodeGenResultVo> genCheckCode(CheckCodeGenReqDto checkCodeGenReqDto);

    CommonResult<Boolean> verifyCode(CheckCodeVerifyReqDto checkCodeVerifyReqDto);
}
