package cn.xk.xcode.core.sign;

import cn.xk.xcode.config.XkSysCryptProperties;

import java.util.Map;

/**
 * @Author xuk
 * @Date 2024/10/25 13:41
 * @Version 1.0.0
 * @Description AbstractSignAlgStrange
 **/
public abstract class AbstractSignAlgStrange {

    protected XkSysCryptProperties xkSysCryptProperties;

    public abstract String generateSignData(Map<String, Object> bodyMap, Map<String, Object> requestUrlParmasMap) throws Exception;

    public abstract boolean verifySignData(Map<String, Object> bodyMap, Map<String, Object> requestUrlParmasMap, String signData) throws Exception;
}
