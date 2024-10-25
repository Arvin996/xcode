package cn.xk.xcode.core.enums;

import cn.xk.xcode.core.crypt.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author xuk
 * @Date 2024/10/25 15:31
 * @Version 1.0.0
 * @Description AlgEnum
 **/
@AllArgsConstructor
@Getter
public enum AlgEnum {

    AES("aes", new AesCrypt()),
    CHACHA20("chacha20", new ChaCha20Crypt()),
    DES("des", new DesCrypt()),
    DESEDE("desede", new DESEdeCrypt()),
    ECIESC("ecies", new ECIESCrypt()),
    RC4("rc4", new Rc4Crypt()),
    RSA("rsa", new RsaCrypt()),
    SM2("sm2", new Sm2Crypt()),
    SM4("sm4", new Sm4Crypt());

    private final String algName;
    private final AbstractCrypt abstractCrypt;
}
