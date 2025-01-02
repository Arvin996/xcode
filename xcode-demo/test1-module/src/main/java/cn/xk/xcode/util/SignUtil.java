package cn.xk.xcode.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.PemUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.digest.MD5;
import cn.xk.xcode.core.utils.CryptUtil;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import static cn.hutool.crypto.asymmetric.SignAlgorithm.MD5withRSA;

/**
 * @Author xuk
 * @Date 2024/12/25 13:55
 * @Version 1.0.0
 * @Description SignUtil
 **/
public class SignUtil {

    public static void main(String[] args) throws IOException {
        // 生成公私钥对
//        KeyPair keyPair = SecureUtil.generateKeyPair("RSA");
//
//        // 创建PEM对象
//        PemObject privatePem = new PemObject("RSA PRIVATE KEY", keyPair.getPrivate().getEncoded());
//        PemObject publicPem = new PemObject("RSA PUBLIC KEY", keyPair.getPublic().getEncoded());
//
//        // 将公私钥写入到PEM文件
//        final String privateKeyPath = "D:\\midend-service-center\\own_module\\test1-module\\src\\main\\resources\\pem\\private.pem";
//        final String publicKeyPath = "D:\\midend-service-center\\own_module\\test1-module\\src\\main\\resources\\pem\\public.pem";
//
//        // 使用PemWriter写入文件
//        PemWriter pemWriter = new PemWriter(FileUtil.getWriter(privateKeyPath, CharsetUtil.CHARSET_UTF_8, true));
//        pemWriter.writeObject(privatePem);
//        pemWriter.close();
//
//        pemWriter = new PemWriter(FileUtil.getWriter(publicKeyPath, CharsetUtil.CHARSET_UTF_8, true));
//        pemWriter.writeObject(publicPem);
//        pemWriter.close();
        PublicKey publicKey = PemUtil.readPemPublicKey(Files.newInputStream(Paths.get("D:\\midend-service-center\\own_module\\test1-module\\src\\main\\resources\\pem\\public.pem")));
        PrivateKey privateKey = PemUtil.readPemPrivateKey(Files.newInputStream(Paths.get("D:\\midend-service-center\\own_module\\test1-module\\src\\main\\resources\\pem\\private.pem")));
        Sign sign = new Sign(SignAlgorithm.MD5withRSA, privateKey, publicKey);
        //签名成功
        byte[] signed = sign.sign("aa=bb".getBytes());
        //base64编码
        String base64 = cn.hutool.core.codec.Base64.encode(signed);
        System.out.println(base64);
        //使用公钥 解密

        //首先Base64解密
        byte[] decode = cn.hutool.core.codec.Base64.decode(base64);
        //选择验证方式 以及 设置公钥
        boolean verify = sign.verify("aa=bb".getBytes(), decode);
        System.out.println("是否验收成功：" + verify);
    }
}
