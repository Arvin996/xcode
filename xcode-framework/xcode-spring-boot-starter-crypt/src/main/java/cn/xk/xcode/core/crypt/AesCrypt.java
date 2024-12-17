package cn.xk.xcode.core.crypt;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.xk.xcode.config.CryptProperties;
import cn.xk.xcode.exception.core.ExceptionUtil;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;

import static cn.xk.xcode.core.CryptGlobalConstant.CRYPT_KEY_MUST_NOT_NULL;

/**
 * @Author xuk
 * @Date 2024/10/24 11:01
 * @Version 1.0.0
 * @Description AesCrypt
 **/
@NoArgsConstructor
public class AesCrypt extends AbstractCrypt{

    private AES aes;

    @Override
    public void init() {
        CryptProperties.AES cryptPropertiesAes = cryptProperties.getAes();
        String key = cryptPropertiesAes.getKey();
        String iv = cryptPropertiesAes.getIv();
        if (StrUtil.isBlankIfStr(key)){
            ExceptionUtil.castServerException(CRYPT_KEY_MUST_NOT_NULL);
        }
        if (StrUtil.isEmptyIfStr(iv)){
            aes = new AES(cryptPropertiesAes.getMode().getMODE_TYPE(), cryptPropertiesAes.getPadding().getPADDING_TYPE(), key.getBytes(StandardCharsets.UTF_8));
        }else {
            aes = new AES(cryptPropertiesAes.getMode().getMODE_TYPE(), cryptPropertiesAes.getPadding().getPADDING_TYPE(), key.getBytes(StandardCharsets.UTF_8), iv.getBytes(StandardCharsets.UTF_8));
        }
    }

    @Override
    public String doDecrypt(String decryptStr) {
        return aes.decryptStr(decryptStr);
    }

    @Override
    public String doEncrypt(String encryptStr) {
        return aes.decryptStr(encryptStr);
    }

    @Override
    public String getType() {
        return "aes";
    }
}
