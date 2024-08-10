package cn.xk.xcode.handler.core;

import cn.xk.xcode.config.CheckCodeProperties;
import cn.xk.xcode.entity.dto.CheckCodeGenReqDto;
import cn.xk.xcode.entity.vo.CheckCodeGenResultVo;
import cn.xk.xcode.enums.CheckCodeGenerateType;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.handler.CheckCodeHandlerStrategy;
import cn.xk.xcode.handler.SaveCheckCodeCacheStrategy;
import cn.xk.xcode.utils.CheckCodeGenUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Objects;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.CHECK_CODE_SEND_ERROR;


/**
 * @Author xuk
 * @Date 2024/7/31 21:10
 * @Version 1.0
 * @Description EmailCheckCodeHandler
 */
public class EmailCheckCodeHandler extends CheckCodeHandlerStrategy {

    private final JavaMailSenderImpl javaMailSender;
    private final Integer expiredTime;

    public EmailCheckCodeHandler(SaveCheckCodeCacheStrategy saveCheckCodeStrategy, CheckCodeProperties checkCodeProperties, JavaMailSenderImpl javaMailSender, Integer expiredTime) {
        super(saveCheckCodeStrategy, checkCodeProperties);
        this.javaMailSender = javaMailSender;
        this.expiredTime = expiredTime;
    }

    @Override
    public CheckCodeGenResultVo generate(CheckCodeGenReqDto checkCodeGenReqDto, int len) {
        String code = sendMessage(checkCodeGenReqDto, len);
        return CheckCodeGenResultVo.builder().code(code).picCode(null).build();
    }

    @Override
    public String sendMessage(CheckCodeGenReqDto checkCodeGenReqDto, int len) {
        String code = CheckCodeGenUtils.genCode(len);
        String email = checkCodeGenReqDto.getEmail();
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
        try{
            // 发送邮件
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new ServiceException(CHECK_CODE_SEND_ERROR);
        }
        return code;
    }

    @Override
    public CheckCodeGenerateType getType() {
        return CheckCodeGenerateType.EMAIL;
    }
}