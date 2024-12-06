package cn.xk.xcode.core;

import cn.xk.xcode.exception.core.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.READ_HTTP_BODY_ERROR;

/**
 * @Author xuk
 * @Date 2024/11/22 15:53
 * @Version 1.0.0
 * @Description HttpBodyReader
 **/
@Slf4j
public class HttpBodyReader {

    public static String getBodyString(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try (InputStream inputStream = request.getInputStream()) {
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.warn("getBodyString出现问题！");
            ExceptionUtil.castServerException(READ_HTTP_BODY_ERROR, e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error(ExceptionUtils.getMessage(e));
                    ExceptionUtil.castServerException(READ_HTTP_BODY_ERROR, e.getMessage());
                }
            }
        }
        return sb.toString();
    }
}
