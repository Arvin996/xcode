package cn.xk.xcode.core.crypt;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.SM2;
import cn.xk.xcode.config.XkSysCryptProperties;
import cn.xk.xcode.core.utils.CryptUtil;
import cn.xk.xcode.exception.core.ExceptionUtil;
import lombok.NoArgsConstructor;

import java.security.PrivateKey;
import java.security.PublicKey;

import static cn.xk.xcode.core.CryptGlobalConstant.*;

/**
 * @Author xuk
 * @Date 2024/10/25 9:07
 * @Version 1.0.0
 * @Description Sm2Crypt
 **/
@NoArgsConstructor
public class Sm2Crypt extends AbstractCrypt {

    private SM2 sm2;

    @Override
    public void init() {
        XkSysCryptProperties.SM2 xkSysCryptPropertiesSm2 = xkSysCryptProperties.getSm2();
        XkSysCryptProperties.ASYMMETRIC_KEY_SOURCE sourceKeyType = xkSysCryptPropertiesSm2.getSourceKeyType();
        if (XkSysCryptProperties.ASYMMETRIC_KEY_SOURCE.APPLICATION_FILE.equals(sourceKeyType)) {
            sm2 = SmUtil.sm2(xkSysCryptPropertiesSm2.getPrivateKey(), xkSysCryptPropertiesSm2.getPublicKey());
        } else {
            // 从文件路径中获取 这里强制要求放在resource下
            String privateKey = xkSysCryptPropertiesSm2.getPrivateKeyPemPathName();
            String publicKey = xkSysCryptPropertiesSm2.getPublicKeyPemPathName();
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
            sm2 = SmUtil.sm2(privateKeyFromPem, publicKeyFromPem);
        }
    }

    @Override
    public String doDecrypt(String decryptStr) {
        return "";
    }

    @Override
    public String doEncrypt(String encryptStr) {
        return "";
    }

    @Override
    public String getType() {
        return "sm2";
    }
}
