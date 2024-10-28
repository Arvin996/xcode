package cn.xk.xcode.handler.core;

import cn.xk.xcode.config.CheckCodeProperties;
import cn.xk.xcode.entity.GenerateCodeResEntity;
import cn.xk.xcode.entity.dto.CheckCodeGenReqDto;
import cn.xk.xcode.entity.dto.CheckCodeVerifyReqDto;
import cn.xk.xcode.entity.vo.CheckCodeGenResultVo;
import cn.xk.xcode.enums.CheckCodeGenerateType;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.handler.CheckCodeHandlerStrategy;
import cn.xk.xcode.handler.SaveCheckCodeCacheStrategy;
import cn.xk.xcode.utils.CheckCodeGenUtil;
import cn.xk.xcode.utils.encrypt.EncryptUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.CHECK_CODE_GEN_ERROR;

/**
 * @Author xuk
 * @Date 2024/7/31 21:11
 * @Version 1.0
 * @Description PicCheckCodeHandler
 */
@Slf4j
public class PicCheckCodeHandler extends CheckCodeHandlerStrategy {

    public final DefaultKaptcha kaptcha;

    public PicCheckCodeHandler(SaveCheckCodeCacheStrategy saveCheckCodeStrategy, CheckCodeProperties checkCodeProperties, DefaultKaptcha kaptcha) {
        super(saveCheckCodeStrategy, checkCodeProperties);
        this.kaptcha = kaptcha;
    }

    @Override
    public GenerateCodeResEntity generate(CheckCodeGenReqDto checkCodeGenReqDto, int len) {
        String code = CheckCodeGenUtil.genCode(len);
        // 生成图片
        BufferedImage image = kaptcha.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String imgBase64Encoder;
        try{
            ImageIO.write(image, "png", outputStream);
            imgBase64Encoder = "data:image/png;base64," + EncryptUtil.encodeBase64(outputStream.toByteArray());
        }catch(Exception e){
            throw new ServiceException(CHECK_CODE_GEN_ERROR);
        }finally {
            try{
                outputStream.close();
            }catch(Exception e){
                log.error(e.getMessage());
            }
        }
        return GenerateCodeResEntity.builder().code(code).picCode(imgBase64Encoder).build();
    }

    @Override
    public void doCodeSave(CheckCodeGenReqDto checkCodeGenReqDto, GenerateCodeResEntity generateCodeResEntity) {
        saveCheckCodeStrategy.save(generateCodeResEntity.getCode(), generateCodeResEntity.getCode());
    }

    @Override
    public String getLocalCodeKey(CheckCodeVerifyReqDto checkCodeVerifyReqDto) {
        return checkCodeVerifyReqDto.getCode();
    }

    @Override
    public String sendMessage(CheckCodeGenReqDto checkCodeGenReqDto, int len) {
        return "";
    }

    @Override
    public CheckCodeGenerateType getType() {
        return CheckCodeGenerateType.PIC;
    }
}
