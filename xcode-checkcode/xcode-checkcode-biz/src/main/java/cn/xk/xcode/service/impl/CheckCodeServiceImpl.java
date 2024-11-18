package cn.xk.xcode.service.impl;

import cn.xk.xcode.entity.dto.CheckCodeGenReqDto;
import cn.xk.xcode.entity.dto.CheckCodeVerifyReqDto;
import cn.xk.xcode.entity.vo.CheckCodeGenResultVo;
import cn.xk.xcode.handler.CheckCodeHandlerStrategy;
import cn.xk.xcode.handler.core.CheckCodeAdvisor;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.CheckCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2024/8/3 12:47
 * @Version 1.0
 * @Description CheckCodeServiceImpl
 */
@Service
public class CheckCodeServiceImpl implements CheckCodeService {

    @Resource
    private CheckCodeAdvisor checkCodeAdvisor;

    @Override
    public CommonResult<CheckCodeGenResultVo> genCheckCode(CheckCodeGenReqDto checkCodeGenReqDto) {
        CheckCodeHandlerStrategy checkCodeHandlerStrategy = checkCodeAdvisor.getCheckCodeHandlerStrategy(checkCodeGenReqDto.getType());
        return CommonResult.success(checkCodeHandlerStrategy.doGenerateCode(checkCodeGenReqDto));
    }

    @Override
    public CommonResult<Boolean> verifyCode(CheckCodeVerifyReqDto checkCodeVerifyReqDto) {
        CheckCodeHandlerStrategy checkCodeHandlerStrategy = checkCodeAdvisor.getCheckCodeHandlerStrategy(checkCodeVerifyReqDto.getType());
        return checkCodeHandlerStrategy.verifyCode(checkCodeVerifyReqDto);
    }
}
