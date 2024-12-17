package cn.xk.xcode.core.crypt;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.SM4;
import cn.xk.xcode.exception.core.ExceptionUtil;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;

import static cn.xk.xcode.core.CryptGlobalConstant.CRYPT_KEY_MUST_NOT_NULL;

/**
 * @Author xuk
 * @Date 2024/10/24 11:12
 * @Version 1.0.0
 * @Description Sm4Crypt
 **/
@NoArgsConstructor
public class Sm4Crypt extends AbstractCrypt {

    private SM4 sm4;

    @Override
    public void init() {
        String key = cryptProperties.getSm4().getKey();
        String iv = cryptProperties.getSm4().getIv();
        if (StrUtil.isBlankIfStr(key)) {
            ExceptionUtil.castServerException(CRYPT_KEY_MUST_NOT_NULL);
        }
        Mode modeType = cryptProperties.getSm4().getMode().getMODE_TYPE();
        Padding pad = cryptProperties.getSm4().getPadding().getPADDING_TYPE();
        if (StrUtil.isEmptyIfStr(iv)) {
            sm4 = new SM4(modeType, pad, key.getBytes(StandardCharsets.UTF_8));
        } else {
            sm4 = new SM4(modeType, pad, key.getBytes(StandardCharsets.UTF_8), iv.getBytes(StandardCharsets.UTF_8));
        }
    }

    @Override
    public String doDecrypt(String decryptStr) {
        return sm4.decryptStr(decryptStr);
    }

    @Override
    public String doEncrypt(String encryptStr) {
        return sm4.encryptBase64(encryptStr);
    }

    @Override
    public String getType() {
        return "sm4";
    }
}
