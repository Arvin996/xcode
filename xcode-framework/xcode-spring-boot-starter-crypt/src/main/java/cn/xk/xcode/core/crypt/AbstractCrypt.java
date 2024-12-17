package cn.xk.xcode.core.crypt;

import cn.xk.xcode.config.CryptProperties;
import cn.xk.xcode.exception.core.ExceptionUtil;
import lombok.Setter;

import static cn.xk.xcode.core.CryptGlobalConstant.ERROR_DECRYPT;
import static cn.xk.xcode.core.CryptGlobalConstant.ERROR_ENCRYPT;

/**
 * @Author xuk
 * @Date 2024/10/24 11:00
 * @Version 1.0.0
 * @Description AbstractCrypt
 **/
@Setter
public abstract class AbstractCrypt {

    protected CryptProperties cryptProperties;

    public abstract void init();

    public abstract String doDecrypt(String decryptStr);

    public String decrypt(String decryptStr) {
        String str = null;
        try {
            str = doDecrypt(decryptStr);
        } catch (Exception e) {
            ExceptionUtil.castServerException(ERROR_DECRYPT, getType(), decryptStr, e.getMessage());
        }
        return str;
    }

    public String encrypt(String data) {
        String res = null;
        try {
            res = doEncrypt(data);
        } catch (Exception e) {
            ExceptionUtil.castServerException(ERROR_ENCRYPT, getType(), data, e.getMessage());
        }
        return res;
    }

    public abstract String doEncrypt(String encryptStr);

    public abstract String getType();

    public static void main(String[] args) {

    }
}
