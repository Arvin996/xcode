package cn.xk.xcode.core.crypt;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.DESede;
import cn.xk.xcode.config.XkSysCryptProperties;
import cn.xk.xcode.exception.core.ExceptionUtil;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;

import static cn.xk.xcode.core.CryptGlobalConstant.CRYPT_KEY_MUST_NOT_NULL;

/**
 * @Author xuk
 * @Date 2024/10/24 13:38
 * @Version 1.0.0
 * @Description DESEdeCrypt
 **/
@NoArgsConstructor
public class DESEdeCrypt extends AbstractCrypt {

    private DESede deSede;

    @Override
    public void init() {
        XkSysCryptProperties.DESEde xkSysCryptPropertiesDesEde = xkSysCryptProperties.getDesEde();
        String key = xkSysCryptPropertiesDesEde.getKey();
        String iv = xkSysCryptPropertiesDesEde.getIv();
        if (StrUtil.isBlankIfStr(key)){
            ExceptionUtil.castServerException(CRYPT_KEY_MUST_NOT_NULL);
        }
        Mode modeType = xkSysCryptPropertiesDesEde.getMode().getMODE_TYPE();
        Padding pad = xkSysCryptPropertiesDesEde.getPadding().getPADDING_TYPE();
        if (StrUtil.isEmptyIfStr(iv)){
            deSede = new DESede(modeType, pad, key.getBytes(StandardCharsets.UTF_8));
        }else {
            deSede = new DESede(modeType, pad, key.getBytes(StandardCharsets.UTF_8), iv.getBytes(StandardCharsets.UTF_8));
        }
    }

    @Override
    public String doDecrypt(String decryptStr) {
        return deSede.decryptStr(decryptStr);
    }

    @Override
    public String doEncrypt(String encryptStr) {
        return deSede.encryptBase64(encryptStr);
    }

    @Override
    public String getType() {
        return "desede";
    }
}
