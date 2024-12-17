package cn.xk.xcode.core.crypt;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.ECIES;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.xk.xcode.config.CryptProperties;
import cn.xk.xcode.core.utils.CryptUtil;
import cn.xk.xcode.exception.core.ExceptionUtil;
import lombok.NoArgsConstructor;

import java.security.PrivateKey;
import java.security.PublicKey;

import static cn.xk.xcode.core.CryptGlobalConstant.*;

/**
 * @Author xuk
 * @Date 2024/10/24 17:25
 * @Version 1.0.0
 * @Description ECIESCrypt
 **/
@NoArgsConstructor
public class ECIESCrypt extends AbstractCrypt {

    private ECIES ecies;

    @Override
    public void init() {
        CryptProperties.ECIES xkSysCryptPropertiesEcies = cryptProperties.getEcies();
        CryptProperties.ASYMMETRIC_KEY_SOURCE sourceKeyType = xkSysCryptPropertiesEcies.getSourceKeyType();
        if (CryptProperties.ASYMMETRIC_KEY_SOURCE.APPLICATION_FILE.equals(sourceKeyType)) {
            ecies = new ECIES(xkSysCryptPropertiesEcies.getPrivateKey(), xkSysCryptPropertiesEcies.getPublicKey());
        } else {
            // 从文件路径中获取 这里强制要求放在resource下
            String privateKey = xkSysCryptPropertiesEcies.getPrivateKeyPemPathName();
            String publicKey = xkSysCryptPropertiesEcies.getPublicKeyPemPathName();
            if (StrUtil.isBlank(privateKey)){
                ExceptionUtil.castServerException(PRIVATE_KEY_PEM_PATH_IS_NULL);
            }
            if (StrUtil.isBlank(publicKey)){
                ExceptionUtil.castServerException(PUBLIC_KEY_PEM_PATH_IS_NULL);
            }
            PrivateKey privateKeyFromPem = CryptUtil.getPrivateKeyFromPem(privateKey);
            PublicKey publicKeyFromPem = CryptUtil.getPublicKeyFromPem(publicKey);
            if (ObjectUtil.isNull(privateKeyFromPem) || ObjectUtil.isNull(publicKeyFromPem)) {
                ExceptionUtil.castServerException(READ_KEY_ERROR);
            }
            ecies = new ECIES(privateKeyFromPem, publicKeyFromPem);
        }
    }

    @Override
    public String doDecrypt(String decryptStr) {
        return ecies.decryptStr(decryptStr, KeyType.PrivateKey);
    }

    @Override
    public String doEncrypt(String encryptStr) {
        return ecies.encryptBase64(encryptStr, KeyType.PublicKey);
    }

    @Override
    public String getType() {
        return "ecies";
    }
}
