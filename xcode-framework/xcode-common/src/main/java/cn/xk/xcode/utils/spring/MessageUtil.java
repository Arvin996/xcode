package cn.xk.xcode.utils.spring;

import cn.hutool.extra.spring.SpringUtil;
import cn.xk.xcode.utils.http.ServletUtil;
import org.springframework.context.MessageSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @Author xuk
 * @Date 2024/11/22 16:50
 * @Version 1.0.0
 * @Description MessageUtil
 **/
public class MessageUtil {

    public static String getMessages(String code, Object[] args) {
        return getMessages(code, args, null);
    }

    public static String getMessages(String code) {
        return getMessages(code, null, null);
    }

    public static String getMessages(String code, Object[] args, Locale locale) {
        if (locale == null) {
            HttpServletRequest request = ServletUtil.getRequest();
            String language = request.getHeader("ss-Language");
            locale = new Locale(language);
        }
        MessageSource messageSource = (MessageSource) SpringUtil.getBean("messageSource");
        return messageSource.getMessage(code, args, locale);
    }
}
