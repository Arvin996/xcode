package cn.xk.xcode.core.utils;

import cn.hutool.crypto.PemUtil;
import cn.xk.xcode.exception.core.ExceptionUtil;

import java.security.PrivateKey;
import java.security.PublicKey;

import static cn.xk.xcode.core.CryptGlobalConstant.*;

/**
 * @Author xuk
 * @Date 2024/10/24 16:09
 * @Version 1.0.0
 * @Description CryptUtil
 **/
public class CryptUtil {

    public static PrivateKey getPrivateKeyFromPem(String fileName) {
        try {
           return PemUtil.readPemPrivateKey(CryptUtil.class.getClassLoader().getResourceAsStream(fileName));
        } catch (Exception e) {
            ExceptionUtil.castServerException(READ_PRIVATE_KEY_ERROR, fileName, e.getMessage());
        }
        return null;
    }

    public static PublicKey getPublicKeyFromPem(String fileName) {
        try {
            return PemUtil.readPemPublicKey(CryptUtil.class.getClassLoader().getResourceAsStream(fileName));
        } catch (Exception e) {
            ExceptionUtil.castServerException(READ_PUBLIC_KEY_ERROR, fileName, e.getMessage());
        }
        return null;
    }
}
