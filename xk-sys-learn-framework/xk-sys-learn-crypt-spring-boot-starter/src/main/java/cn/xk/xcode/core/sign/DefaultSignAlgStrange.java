package cn.xk.xcode.core.sign;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.crypto.SignUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.xk.xcode.config.XkSysCryptProperties;
import cn.xk.xcode.core.utils.CryptUtil;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2024/10/25 10:16
 * @Version 1.0.0
 * @Description DefaultSignAlgStrange
 **/
@Getter
public class DefaultSignAlgStrange extends AbstractSignAlgStrange {

    private final Sign signer;

    public DefaultSignAlgStrange(XkSysCryptProperties xkSysCryptProperties) {
        this.xkSysCryptProperties = xkSysCryptProperties;
        XkSysCryptProperties.Sign sign = xkSysCryptProperties.getSign();
        String publicKey = sign.getPublicKey();
        String privateKey = sign.getPrivateKey();
        XkSysCryptProperties.SignAlgType signAlgType = sign.getSignAlgType();
        XkSysCryptProperties.ASYMMETRIC_KEY_SOURCE sourceKeyType = sign.getSourceKeyType();
        if (sourceKeyType.equals(XkSysCryptProperties.ASYMMETRIC_KEY_SOURCE.PERM_FILE)) {
            PublicKey publicKeyFromPem = CryptUtil.getPublicKeyFromPem(publicKey);
            PrivateKey privateKeyFromPem = CryptUtil.getPrivateKeyFromPem(privateKey);
            signer = new Sign(signAlgType.getSignAlgorithm(), privateKeyFromPem, publicKeyFromPem);
        } else {
            signer = SignUtil.sign(signAlgType.getSignAlgorithm(), privateKey, publicKey);
        }
    }

    @Override
    public String generateSignData(Map<String, Object> bodyMap, Map<String, Object> requestUrlParmasMap) throws Exception {
        XkSysCryptProperties.Sign sign = xkSysCryptProperties.getSign();
        String separator = sign.getSeparator();
        XkSysCryptProperties.SignDateRule signDateRule = sign.getSignDateRule();
        String res = "";
        if (signDateRule.equals(XkSysCryptProperties.SignDateRule.REQUEST_BODY_ONLY)) {
            res = handleSignData(bodyMap, null, separator);
        } else if (signDateRule.equals(XkSysCryptProperties.SignDateRule.REQUEST_URL_PARAMS_ONLY)) {
            res = handleSignData(null, requestUrlParmasMap, separator);
        } else {
            res = handleSignData(bodyMap, requestUrlParmasMap, separator);
        }
        return res;
    }

    private String handleSignData(Map<String, Object> bodyMap, Map<String, Object> requestUrlParmasMap, String separator) {
        StringBuilder stringBuilder = new StringBuilder();
        if (ObjUtil.isNotEmpty(bodyMap)) {
            bodyMap.forEach((k, v) -> stringBuilder.append(k).append("=").append(v).append(separator));
        }
        if (ObjUtil.isNotEmpty(requestUrlParmasMap)) {
            requestUrlParmasMap.forEach((k, v) -> stringBuilder.append(k).append("=").append(v).append(separator));
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    @Override
    public boolean verifySignData(Map<String, Object> bodyMap, Map<String, Object> requestUrlParmasMap, String signData) throws Exception {
        String signDateFromRequest = generateSignData(bodyMap, requestUrlParmasMap);
        return this.signer.verify(signDateFromRequest.getBytes(StandardCharsets.UTF_8), Base64.decode(signData));
    }
}
