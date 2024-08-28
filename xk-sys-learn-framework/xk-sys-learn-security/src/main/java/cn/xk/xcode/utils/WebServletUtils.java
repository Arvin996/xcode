package cn.xk.xcode.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.servlet.ServletUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Administrator
 * @Date 2024/8/27 17:56
 * @Version V1.0.0
 * @Description WebServletUtils
 */
public class WebServletUtils {

    private WebServletUtils() {
    }

    public static String getClientIp() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ObjectUtil.isNull(sra)) {
            return null;
        }
        HttpServletRequest request = sra.getRequest();
        return ServletUtil.getClientIP(request);
    }
}
