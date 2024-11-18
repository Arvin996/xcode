package cn.xk.xcode.handler;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.config.CheckCodeProperties;
import cn.xk.xcode.entity.GenerateCodeResEntity;
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

    protected final SaveCheckCodeCacheStrategy saveCheckCodeStrategy;

    protected final CheckCodeProperties checkCodeProperties;

    public abstract GenerateCodeResEntity generate(CheckCodeGenReqDto checkCodeGenReqDto, int len);

    public CheckCodeGenResultVo doGenerateCode(CheckCodeGenReqDto checkCodeGenReqDto) {
        GenerateCodeResEntity generateCodeResEntity = generate(checkCodeGenReqDto, checkCodeProperties.getCodeLength());
        doCodeSave(checkCodeGenReqDto, generateCodeResEntity);
        return CheckCodeGenResultVo.builder().success(true).picCode(generateCodeResEntity.getPicCode()).build();
    }

    public abstract void doCodeSave(CheckCodeGenReqDto checkCodeGenReqDto, GenerateCodeResEntity generateCodeResEntity);

    public CommonResult<Boolean> verifyCode(CheckCodeVerifyReqDto checkCodeVerifyReqDto) {
        String code = checkCodeVerifyReqDto.getCode();
        String key = getLocalCodeKey(checkCodeVerifyReqDto);
        String localCode = saveCheckCodeStrategy.get(key);
        if (!StringUtils.hasLength(localCode)) {
            return CommonResult.error(CHECK_CODE_IS_EXPIRED);
        }
        if (!StrUtil.equalsIgnoreCase(code, localCode)) {
            return CommonResult.error(CHECK_CODE_IS_ERROR);
        }

        saveCheckCodeStrategy.remove(key);
        return CommonResult.success(true);
    }

    public abstract String getLocalCodeKey(CheckCodeVerifyReqDto checkCodeVerifyReqDto);

    public abstract String sendMessage(CheckCodeGenReqDto checkCodeGenReqDto, int len);

    public abstract CheckCodeGenerateType getType();
}
