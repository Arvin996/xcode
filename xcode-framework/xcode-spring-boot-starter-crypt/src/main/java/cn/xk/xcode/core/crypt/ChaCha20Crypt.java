package cn.xk.xcode.core.crypt;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.symmetric.ChaCha20;
import cn.xk.xcode.config.CryptProperties;
import cn.xk.xcode.exception.core.ExceptionUtil;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;

import static cn.xk.xcode.core.CryptGlobalConstant.CRYPT_IV_MUST_NOT_NULL;
import static cn.xk.xcode.core.CryptGlobalConstant.CRYPT_KEY_MUST_NOT_NULL;

/**
 * @Author xuk
 * @Date 2024/10/24 11:31
 * @Version 1.0.0
 * @Description ChaCha20Crypt
 **/
@NoArgsConstructor
public class ChaCha20Crypt extends AbstractCrypt{

    private ChaCha20 chaCha20;

    @Override
    public void init() {
        CryptProperties.ChaCha20 chacha20 = cryptProperties.getChacha20();
        String key = chacha20.getKey();
        if (StrUtil.isBlankIfStr(key)){
            ExceptionUtil.castServerException(CRYPT_KEY_MUST_NOT_NULL);
        }
        if (StrUtil.isEmptyIfStr(chacha20.getIv())){
            ExceptionUtil.castServerException(CRYPT_IV_MUST_NOT_NULL);
        }
        chaCha20 = new ChaCha20(key.getBytes(StandardCharsets.UTF_8),chacha20.getIv().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String doDecrypt(String decryptStr) {
        return chaCha20.decryptStr(decryptStr);
    }

    @Override
    public String doEncrypt(String encryptStr) {
        return chaCha20.encryptBase64(encryptStr);
    }

    @Override
    public String getType() {
        return "chacha20";
    }
}
