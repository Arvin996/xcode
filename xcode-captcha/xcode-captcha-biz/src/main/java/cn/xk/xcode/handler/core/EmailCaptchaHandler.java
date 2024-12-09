package cn.xk.xcode.handler.core;

import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.config.CaptchaProperties;
import cn.xk.xcode.entity.GenerateCodeResEntity;
import cn.xk.xcode.entity.dto.CaptchaGenReqDto;
import cn.xk.xcode.entity.dto.CaptchaVerifyReqDto;
import cn.xk.xcode.enums.CaptchaGenerateType;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.handler.CaptchaHandlerStrategy;
import cn.xk.xcode.handler.SaveCaptchaCacheStrategy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Objects;

import static cn.xk.xcode.enums.CaptchaGlobalErrorCodeConstants.CAPTCHA_REPEAT_SEND;
import static cn.xk.xcode.exception.GlobalErrorCodeConstants.*;


/**
 * @Author xuk
 * @Date 2024/7/31 21:10
 * @Version 1.0
 * @Description EmailCaptchaHandler
 */
public class EmailCaptchaHandler extends CaptchaHandlerStrategy {

    private final JavaMailSenderImpl javaMailSender;
    private final Integer expiredTime;

    public EmailCaptchaHandler(SaveCaptchaCacheStrategy saveCaptchaStrategy, CaptchaProperties captchaProperties, JavaMailSenderImpl javaMailSender, Integer expiredTime) {
        super(saveCaptchaStrategy, captchaProperties);
        this.codeGenerator = new RandomGenerator(RandomUtil.BASE_CHAR_NUMBER, captchaProperties.getEmail().getLength());
        this.javaMailSender = javaMailSender;
        this.expiredTime = expiredTime;
    }

    @Override
    public GenerateCodeResEntity generate(CaptchaGenReqDto captchaGenReqDto) {
        if (StrUtil.isNotEmpty(this.saveCaptchaStrategy.get(captchaGenReqDto.getEmail()))){
            throw new ServiceException(CAPTCHA_REPEAT_SEND);
        }
        String code = sendMessage(captchaGenReqDto);
        return GenerateCodeResEntity.builder().code(code).picCode(null).build();
    }

    @Override
    public void doCodeSave(CaptchaGenReqDto captchaGenReqDto, GenerateCodeResEntity generateCodeResEntity) {
        saveCaptchaStrategy.save(captchaGenReqDto.getEmail(), generateCodeResEntity.getCode());
    }

    @Override
    public String getLocalCodeKey(CaptchaVerifyReqDto captchaVerifyReqDto) {
        return captchaVerifyReqDto.getEmail();
    }

    @Override
    public String sendMessage(CaptchaGenReqDto captchaGenReqDto) {
        String code = this.codeGenerator.generate();
        String email = captchaGenReqDto.getEmail();
        SimpleMailMessage message = new SimpleMailMessage();
        // 发送邮箱
        message.setFrom(Objects.requireNonNull(javaMailSender.getUsername()));
        // 接受邮箱
        message.setTo(email);
        // 标题
        message.setSubject("测试服务平台-邮箱验证");
        // 内容
        message.setText("<h1>尊敬的用户您好：</h1><br>" +
                "<h5> 您正在进行邮箱验证，本次验证码为：<span style='color:#ec0808;font-size: 20px;'>" + code + "</span>，请在" + expiredTime + "分钟内进行使用。</h5>" +
                "<h5>如非本人操作，请忽略此邮件，由此给您带来的不便请谅解！</h5> <h5 style='text-align: right;'>--测试服务平台</h5>");
        try {
            // 发送邮件
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new ServiceException(CHECK_CODE_SEND_ERROR);
        }
        return code;
    }

    @Override
    public CaptchaGenerateType getType() {
        return CaptchaGenerateType.EMAIL;
    }
}