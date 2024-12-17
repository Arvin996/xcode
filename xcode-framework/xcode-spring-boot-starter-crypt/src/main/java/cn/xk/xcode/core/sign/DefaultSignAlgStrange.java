package cn.xk.xcode.core.sign;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SignUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.xk.xcode.config.CryptProperties;
import cn.xk.xcode.config.SignProperties;
import cn.xk.xcode.core.utils.CryptUtil;
import cn.xk.xcode.exception.core.ExceptionUtil;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;

import static cn.xk.xcode.core.CryptGlobalConstant.*;

/**
 * @Author xuk
 * @Date 2024/10/25 10:16
 * @Version 1.0.0
 * @Description DefaultSignAlgStrange
 **/
@Getter
public class DefaultSignAlgStrange extends AbstractSignAlgStrange {

    private final Sign signer;

    public DefaultSignAlgStrange(SignProperties signProperties) {
        this.signProperties = signProperties;
        SignProperties.SignAlgType signAlgType = this.signProperties.getSignAlgType();
        CryptProperties.ASYMMETRIC_KEY_SOURCE sourceKeyType = this.signProperties.getSourceKeyType();
        if (sourceKeyType.equals(CryptProperties.ASYMMETRIC_KEY_SOURCE.PERM_FILE)) {
            String privateKeyPemPath = this.signProperties.getPrivateKeyPemPath();
            String publicKeyPemPath = this.signProperties.getPublicKeyPemPath();
            if (StrUtil.isBlank(privateKeyPemPath)){
                ExceptionUtil.castServerException(PRIVATE_KEY_PEM_PATH_IS_NULL);
            }
            if (StrUtil.isBlank(publicKeyPemPath)){
                ExceptionUtil.castServerException(PUBLIC_KEY_PEM_PATH_IS_NULL);
            }
            PublicKey publicKeyFromPem = CryptUtil.getPublicKeyFromPem(publicKeyPemPath);
            PrivateKey privateKeyFromPem = CryptUtil.getPrivateKeyFromPem(privateKeyPemPath);
            if (ObjectUtil.isNull(privateKeyFromPem) || ObjectUtil.isNull(publicKeyFromPem)) {
                ExceptionUtil.castServerException(READ_KEY_ERROR);
            }
            signer = new Sign(signAlgType.getSignAlgorithm(), privateKeyFromPem, publicKeyFromPem);
        } else {
            String publicKey = signProperties.getPublicKey();
            String privateKey = signProperties.getPrivateKey();
            if (StrUtil.isBlank(privateKey)){
                ExceptionUtil.castServerException(PRIVATE_KEY_IS_NULL);
            }
            if (StrUtil.isBlank(publicKey)){
                ExceptionUtil.castServerException(PUBLIC_KEY_IS_NULL);
            }
            signer = SignUtil.sign(signAlgType.getSignAlgorithm(), privateKey, publicKey);
        }
    }

    @Override
    public String generateSignData(Map<String, Object> bodyMap, Map<String, Object> requestUrlParmasMap) throws Exception {
        String separator = signProperties.getSeparator();
        SignProperties.SignDateRule signDateRule = signProperties.getSignDateRule();
        String res = "";
        if (signDateRule.equals(SignProperties.SignDateRule.REQUEST_BODY_ONLY)) {
            res = handleSignData(bodyMap, null, separator);
        } else if (signDateRule.equals(SignProperties.SignDateRule.REQUEST_URL_PARAMS_ONLY)) {
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
