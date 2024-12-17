package cn.xk.xcode.core.crypt;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.DES;
import cn.xk.xcode.config.CryptProperties;
import cn.xk.xcode.exception.core.ExceptionUtil;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;

import static cn.xk.xcode.core.CryptGlobalConstant.CRYPT_KEY_MUST_NOT_NULL;

/**
 * @Author xuk
 * @Date 2024/10/24 13:28
 * @Version 1.0.0
 * @Description DesCrypt
 **/
@NoArgsConstructor
public class DesCrypt extends AbstractCrypt {

    private DES des;

    @Override
    public void init() {
        CryptProperties.DES xkSysCryptPropertiesDes = cryptProperties.getDes();
        String key = xkSysCryptPropertiesDes.getKey();
        String iv = xkSysCryptPropertiesDes.getIv();
        if (StrUtil.isBlankIfStr(key)){
            ExceptionUtil.castServerException(CRYPT_KEY_MUST_NOT_NULL);
        }
        Mode modeType = xkSysCryptPropertiesDes.getMode().getMODE_TYPE();
        Padding pad = xkSysCryptPropertiesDes.getPadding().getPADDING_TYPE();
        if (StrUtil.isEmptyIfStr(iv)){
            des = new DES(modeType, pad, key.getBytes(StandardCharsets.UTF_8));
        }else {
            des = new DES(modeType, pad, key.getBytes(StandardCharsets.UTF_8), iv.getBytes(StandardCharsets.UTF_8));
        }
    }

    @Override
    public String doDecrypt(String decryptStr) {
        return des.decryptStr(decryptStr);
    }

    @Override
    public String doEncrypt(String encryptStr) {
        return des.decryptStr(encryptStr);
    }

    @Override
    public String getType() {
        return "des";
    }
}
