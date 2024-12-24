package cn.xk.xcode.utils.encrypt;

import cn.hutool.core.codec.Base64;

/**
 * Base64 工具类
 */
public class Base64Util {

    private Base64Util() {
    }

    public static String encode(byte[] from) {
        return Base64.encode(from);
    }

    public static byte[] decode(String from) {
        return Base64.decode(from);
    }
}
