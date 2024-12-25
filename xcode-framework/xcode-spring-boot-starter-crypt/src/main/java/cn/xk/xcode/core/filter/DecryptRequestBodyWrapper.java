package cn.xk.xcode.core.filter;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.crypt.AbstractCrypt;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @Author xuk
 * @Date 2024/12/17 8:52
 * @Version 1.0.0
 * @Description DecryptRequestBodyWrapper
 **/
@Slf4j
public class DecryptRequestBodyWrapper extends HttpServletRequestWrapper {

    private byte[] body = null;
    private static final String BODY_KEY = "body";

    public DecryptRequestBodyWrapper(HttpServletRequest request, AbstractCrypt crypt) throws IOException {
        super(request);
        byte[] readBytes = IoUtil.readBytes(request.getInputStream(), false);
        // 获取加密body
        String requestBody = new String(readBytes, StandardCharsets.UTF_8);
        // 解密
        String decryptBody;
        if (StrUtil.isNotEmpty(requestBody)) {
            JSONObject jsonObject = JSONObject.parseObject(requestBody);
            String bodyStr = jsonObject.getString(BODY_KEY);
            if (StrUtil.isNotBlank(bodyStr)) {
                decryptBody = crypt.decrypt(bodyStr);
                body = UriUtils.decode(decryptBody, StandardCharsets.UTF_8).getBytes(StandardCharsets.UTF_8);
            }else {
                log.warn("请求报文未空，或者情况报文中不包含key为body的值，格式错误");
            }
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

//    @Override
//    public String[] getParameterValues(String name) {
//        if (paramsName.contains(name)){
//            String[] values = parameterMap.get(name);
//            if (values != null && values.length > 0) {
//                for (int i = 0; i < values.length; i++) {
//                    values[i] = modifyParam(values[i]);
//                }
//            }
//            return values;
//        }else {
//            return parameterMap.get(name);
//        }
//    }

//    @Override
//    public Enumeration<String> getHeaders(String name){
//        Enumeration<String> headers = super.getHeaders(name);
//        if (headerName.contains(name)){
//            List<String> list= new ArrayList<>();
//            while (headers.hasMoreElements()){
//                String element = headers.nextElement();
//                if(StrUtil.isNotBlank(element)){
//                    list.add(modifyParam(element));
//                }else {
//                    list.add(element);
//                }
//            }
//            return Collections.enumeration(list);
//        }else {
//            return headers;
//        }
//
//    }
//
//    @Override
//    public String getHeader(String name){
//        String header = super.getHeader(name);
//        if (headerName.contains(name)){
//            if (StrUtil.isNotBlank(header)){
//                return modifyParam(header);
//            }
//            return header;
//        }else {
//            return header;
//        }
//    }



//    @Override
//    public String getParameter(String name) {
//        String[] strings = parameterMap.get(name);
//        if (strings != null && strings.length > 0) {
//            if (parameterMap.containsKey(name)){
//                return modifyParam(strings[0]);
//            }else {
//                return strings[0];
//            }
//        }
//        return super.getParameter(name);
//    }

//    private String modifyParam(String param) {
//        return crypt.decrypt(param);
//    }

}
