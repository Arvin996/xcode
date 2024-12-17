package cn.xk.xcode.core.filter;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.crypt.AbstractCrypt;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2024/12/17 8:52
 * @Version 1.0.0
 * @Description DecryptRequestBodyWrapper
 **/
public class DecryptRequestBodyWrapper extends HttpServletRequestWrapper {

    private byte[] body = null;
    private final AbstractCrypt crypt;
    private Map<String, String[]> parameterMap;
    private final int count;
    private static final int FIRST_PATH_VARIABLE_INDEX = 3;

    public DecryptRequestBodyWrapper(HttpServletRequest request, AbstractCrypt crypt, int count) throws IOException {
        super(request);
        parameterMap = request.getParameterMap();
        this.crypt = crypt;
        this.count = count;
        byte[] readBytes = IoUtil.readBytes(request.getInputStream(), false);
        // 获取加密body
        String requestBody = new String(readBytes, StandardCharsets.UTF_8);
        // 解密
        String decryptBody;
        if (StrUtil.isNotEmpty(requestBody)) {
            decryptBody = crypt.decrypt(requestBody);
            body = UriUtils.decode(decryptBody, StandardCharsets.UTF_8).getBytes(StandardCharsets.UTF_8);
        }
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public int getContentLength() {
        return body.length;
    }

    @Override
    public long getContentLengthLong() {
        return body.length;
    }

    @Override
    public String getContentType() {
        return MediaType.APPLICATION_JSON_VALUE;
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public int read() {
                return bais.read();
            }

            @Override
            public int available() {
                return body.length;
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = parameterMap.get(name);
        if (values != null && values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                values[i] = modifyParam(values[i]);
            }
        }
        return values;
    }

    @Override
    public String getParameter(String name) {
        String[] strings = parameterMap.get(name);
        if (strings != null && strings.length > 0) {
            return modifyParam(strings[0]);
        }
        return super.getParameter(name);
    }

    private String modifyParam(String param) {
        return crypt.decrypt(param);
    }

    @Override
    public StringBuffer getRequestURL() {
        StringBuffer requestURL = super.getRequestURL();
        if (count == 0) {
            return requestURL;
        }
        // 去除前缀http:// 或者https://
        int index1 = requestURL.indexOf(":");
        int index2 = index1 + FIRST_PATH_VARIABLE_INDEX;
        String newStr = requestURL.substring(index2);
        String s = buildURI(newStr);
        s = s.substring(1);
        return new StringBuffer(requestURL.substring(0, index2) + s);
    }

    @Override
    public String getRequestURI() {
        String requestURI = super.getRequestURI();
        if (count == 0) {
            return requestURI;
        }
        return buildURI(requestURI);
    }

    public String buildURI(String requestURI){
        // 使用”/“进行分割
        String[] split = requestURI.split("/");
        StringBuilder stringBuilder = new StringBuilder();
        int len = split.length - 1;
        for (int i = 0; i < count; i++) {
            split[len] = crypt.decrypt(split[len]);
            len--;
        }
        for (String s : split) {
            stringBuilder.append("/").append(s);
        }
        return stringBuilder.toString();
    }

}
