package cn.xk.xcode.core.crypt;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.xk.xcode.config.CryptProperties;
import cn.xk.xcode.core.utils.CryptUtil;
import cn.xk.xcode.exception.core.ExceptionUtil;
import lombok.NoArgsConstructor;

import java.security.PrivateKey;
import java.security.PublicKey;

import static cn.xk.xcode.core.CryptGlobalConstant.*;

/**
 * @Author xuk
 * @Date 2024/10/24 16:01
 * @Version 1.0.0
 * @Description RsaCrypt
 **/
@NoArgsConstructor
public class RsaCrypt extends AbstractCrypt{

    private RSA rsa;

    @Override
    public void init() {
        CryptProperties.RSA xkSysCryptPropertiesRsa = cryptProperties.getRsa();
        CryptProperties.RSA_TYPE rsaType = xkSysCryptPropertiesRsa.getRsaType();
        CryptProperties.ASYMMETRIC_KEY_SOURCE sourceKeyType = xkSysCryptPropertiesRsa.getSourceKeyType();
        if (ObjectUtil.isNull(xkSysCryptPropertiesRsa.getPrivateKey())){
            ExceptionUtil.castServerException(PRIVATE_KEY_IS_NULL);
        }
        if (ObjectUtil.isNull(xkSysCryptPropertiesRsa.getPublicKey())){
            ExceptionUtil.castServerException(PUBLIC_KEY_IS_NULL);
        }
        if (CryptProperties.ASYMMETRIC_KEY_SOURCE.APPLICATION_FILE.equals(sourceKeyType)){
            rsa = new RSA(rsaType.getAlgorithm().getValue(), xkSysCryptPropertiesRsa.getPrivateKey(), xkSysCryptPropertiesRsa.getPublicKey());
        }else {
            // 从文件路径中获取 这里强制要求放在resource下
            String privateKey = xkSysCryptPropertiesRsa.getPrivateKeyPemPathName();
            String publicKey = xkSysCryptPropertiesRsa.getPublicKeyPemPathName();
            if (StrUtil.isBlank(privateKey)){
                ExceptionUtil.castServerException(PRIVATE_KEY_PEM_PATH_IS_NULL);
            }
            if (StrUtil.isBlank(publicKey)){
                ExceptionUtil.castServerException(PUBLIC_KEY_PEM_PATH_IS_NULL);
            }
            PrivateKey privateKeyFromPem = CryptUtil.getPrivateKeyFromPem(privateKey);
            PublicKey publicKeyFromPem = CryptUtil.getPublicKeyFromPem(publicKey);
            if (ObjectUtil.isNull(privateKeyFromPem) || ObjectUtil.isNull(publicKeyFromPem)){
                ExceptionUtil.castServerException(READ_KEY_ERROR);
            }
            rsa = new RSA(rsaType.getAlgorithm().getValue(), privateKeyFromPem, publicKeyFromPem);
        }
    }

    @Override
    public String doDecrypt(String decryptStr) {
        return rsa.decryptStr(decryptStr, KeyType.PrivateKey);
    }

    @Override
    public String doEncrypt(String encryptStr) {
        return rsa.encryptBase64(encryptStr, KeyType.PublicKey);
    }

    @Override
    public String getType() {
        return "rsa";
    }
}
