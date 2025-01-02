package cn.xk.xcode.util;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.SM4;
import cn.hutool.json.JSONUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2024/10/29 15:44
 * @Version 1.0.0
 * @Description Sm4Utils
 **/
public class Sm4Utils {

    private final static String key1 = "c1e05a0ed9b04f54";
    private final static String IV1 = "bd993e635dc34889";
    public static final SM4 sm4 = new SM4(Mode.CBC, Padding.PKCS5Padding, key1.getBytes(), IV1.getBytes());

    public static String decrypt(String data) {
        return sm4.decryptStr(data);
    }

    public static String encrypt(String data) {
        return sm4.encryptBase64(data);
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("aa", "bb");
        System.out.println(encrypt("157523654223"));
        System.out.println(encrypt("bb"));
        System.out.println(encrypt("gVxlu90X1cTyjYwSVQSsCAWwlVFify+8Hs5brdy2sKJJsxdeVRGTwTyakDCeIwx7lg50h6npKdF01MjX+cLVcmVpvqPR626qj8PoMFaPiG5mPjYx4B2ek8aZnPe5YLw+cgSYEA6R4DIU331iVWHOqE4UsdNq+FS3+Hu+eS3Nn80="));
        System.out.println(encrypt(JSONUtil.toJsonStr(map)));
    }
}
