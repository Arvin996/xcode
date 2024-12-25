package cn.xk.xcode.core.utils;

import cn.hutool.crypto.PemUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.xk.xcode.exception.core.ExceptionUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.nio.file.Files;
import java.nio.file.Paths;
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
            return PemUtil.readPemPrivateKey(Files.newInputStream(Paths.get(fileName)));
        } catch (Exception e) {
            ExceptionUtil.castServerException(READ_PRIVATE_KEY_ERROR, fileName, e.getMessage());
        }
        return null;
    }

    public static PublicKey getPublicKeyFromPem(String fileName) {
        try {
            return PemUtil.readPemPublicKey(Files.newInputStream(Paths.get(fileName)));
        } catch (Exception e) {
            ExceptionUtil.castServerException(READ_PUBLIC_KEY_ERROR, fileName, e.getMessage());
        }
        return null;
    }
}
