package cn.xk.xcode.core.crypt;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.symmetric.RC4;
import cn.xk.xcode.config.XkSysCryptProperties;
import cn.xk.xcode.exception.core.ExceptionUtil;
import lombok.NoArgsConstructor;

import static cn.xk.xcode.core.CryptGlobalConstant.CRYPT_KEY_MUST_NOT_NULL;

/**
 * @Author xuk
 * @Date 2024/10/24 14:15
 * @Version 1.0.0
 * @Description Rc4Crypt
 **/
@NoArgsConstructor
public class Rc4Crypt extends AbstractCrypt{

    private RC4 rc4;

    @Override
    public void init() {
        XkSysCryptProperties.RC4 rc4 = xkSysCryptProperties.getRc4();
        String key = rc4.getKey();
        if (StrUtil.isEmptyIfStr(key)){
            ExceptionUtil.castServerException(CRYPT_KEY_MUST_NOT_NULL);
        }else {
            this.rc4 = new RC4(key);
        }
    }

    @Override
    public String doDecrypt(String decryptStr) {
        return rc4.decrypt(decryptStr);
    }

    @Override
    public String doEncrypt(String encryptStr) {
        return rc4.encryptBase64(encryptStr);
    }

    @Override
    public String getType() {
        return "rc4";
    }
}
