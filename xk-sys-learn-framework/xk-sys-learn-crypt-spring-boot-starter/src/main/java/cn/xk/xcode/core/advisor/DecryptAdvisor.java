package cn.xk.xcode.core.advisor;

import cn.xk.xcode.core.decrypt.BaseDecipher;
import cn.xk.xcode.utils.object.ObjectUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * @Author xuk
 * @Date 2024/10/23 16:52
 * @Version 1.0.0
 * @Description DecryptAdvisor
 **/
@RequiredArgsConstructor
@Data
public class DecryptAdvisor {

    private final Map<String, BaseDecipher> decipherList;

    private BaseDecipher getDeCipher(String algorithm) {
        //ObjectUtil.ifNullCastServiceException();
        return decipherList.get(algorithm);
    }
}
