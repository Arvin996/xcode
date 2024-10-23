package cn.xk.xcode.core.advisor;

import cn.xk.xcode.core.encrypt.BaseCipher;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * @Author xuk
 * @Date 2024/10/23 16:53
 * @Version 1.0.0
 * @Description EncryptAdvisor
 **/
@RequiredArgsConstructor
@Data
public class EncryptAdvisor {

    private final Map<String, BaseCipher> cipherMap;

    private BaseCipher getCipher(String algorithm) {
        return cipherMap.get(algorithm);
    }
}
