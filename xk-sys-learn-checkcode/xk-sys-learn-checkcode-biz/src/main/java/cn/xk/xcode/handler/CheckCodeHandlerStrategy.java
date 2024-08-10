package cn.xk.xcode.handler;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.config.CheckCodeProperties;
import cn.xk.xcode.entity.dto.CheckCodeGenReqDto;
import cn.xk.xcode.entity.dto.CheckCodeVerifyReqDto;
import cn.xk.xcode.entity.vo.CheckCodeGenResultVo;
import cn.xk.xcode.enums.CheckCodeGenerateType;
import cn.xk.xcode.pojo.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.CHECK_CODE_IS_ERROR;
import static cn.xk.xcode.exception.GlobalErrorCodeConstants.CHECK_CODE_IS_EXPIRED;

/**
 * @Author xuk
 * @Date 2024/7/31 18:59
 * @Version 1.0
 * @Description BaseCheckCodeGenerate
 */
@RequiredArgsConstructor
public abstract class CheckCodeHandlerStrategy {

    private final SaveCheckCodeCacheStrategy saveCheckCodeStrategy;

    private final CheckCodeProperties checkCodeProperties;

    public abstract CheckCodeGenResultVo generate(CheckCodeGenReqDto checkCodeGenReqDto, int len);

    public CheckCodeGenResultVo doGenerateCode(CheckCodeGenReqDto checkCodeGenReqDto) {
        CheckCodeGenResultVo checkCodeGenResultVo = generate(checkCodeGenReqDto, checkCodeProperties.getCodeLength());
        saveCheckCodeStrategy.save(checkCodeGenResultVo.getCode());
        return checkCodeGenResultVo;
    }

    public CommonResult<Boolean> verifyCode(CheckCodeVerifyReqDto checkCodeVerifyReqDto){
        String code = checkCodeVerifyReqDto.getCode();
        String localCode = saveCheckCodeStrategy.get(code);
        if (!StringUtils.hasLength(localCode)){
            return CommonResult.error(CHECK_CODE_IS_EXPIRED);
        }

        if (!StrUtil.equalsIgnoreCase(code, localCode)){
            return CommonResult.error(CHECK_CODE_IS_ERROR);
        }

        saveCheckCodeStrategy.remove(code);
        return CommonResult.success(true);
    }

    public abstract String sendMessage(CheckCodeGenReqDto checkCodeGenReqDto, int len);

    public abstract CheckCodeGenerateType getType();
}
