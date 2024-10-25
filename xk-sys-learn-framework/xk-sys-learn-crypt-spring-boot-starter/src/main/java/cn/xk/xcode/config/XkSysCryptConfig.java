package cn.xk.xcode.config;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.SecureUtil;
import cn.xk.xcode.core.crypt.AbstractCrypt;
import cn.xk.xcode.core.crypt.CostumeCrypt;
import cn.xk.xcode.core.enums.AlgEnum;
import cn.xk.xcode.core.sign.AbstractSignAlgStrange;
import cn.xk.xcode.core.sign.CostumeSignAlgStrange;
import cn.xk.xcode.core.utils.CryptUtil;
import cn.xk.xcode.exception.core.ExceptionUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import static cn.xk.xcode.core.CryptGlobalConstant.*;

/**
 * @Author xuk
 * @Date 2024/10/24 9:38
 * @Version 1.0.0
 * @Description XkSysCryptConfig
 **/
@Configuration
@EnableConfigurationProperties(XkSysCryptProperties.class)
public class XkSysCryptConfig {

    @Resource
    private XkSysCryptProperties xkSysCryptProperties;

    @Bean
    public AbstractCrypt abstractCrypt(){
        XkSysCryptProperties.CipherType cipherType = xkSysCryptProperties.getCipherType();
        if (cipherType.equals(XkSysCryptProperties.CipherType.COSTUME)){
            ServiceLoader<CostumeCrypt> costumeCrypts = ServiceLoader.load(CostumeCrypt.class);
            List<CostumeCrypt> list = new ArrayList<>();
            while (costumeCrypts.iterator().hasNext()){
                list.add(costumeCrypts.iterator().next());
            }
            if (list.isEmpty()){
                ExceptionUtil.castServerException(COSTUME_CRYPT_ALG_NOT_CONFIG);
            }
            if (list.size() > 1){
                ExceptionUtil.castServerException(COSTUME_CRYPT_ALG_MUST_BE_UNIQUE);
            }
            CostumeCrypt costumeCrypt = list.get(0);
            XkSysCryptProperties.CostumeAlg costumeAlg = xkSysCryptProperties.getCostumeAlg();
            XkSysCryptProperties.CostumeAlgEnum algEnum = costumeAlg.getAlgEnum();
            if (algEnum.equals(XkSysCryptProperties.CostumeAlgEnum.SYMMETRIC)){
                String secretKey = costumeAlg.getSecretKey();
                if (StrUtil.isBlank(secretKey)){
                    ExceptionUtil.castServerException(SYMMETRIC_ALG_KEY_MUST_NOT_NULL);
                }else {
                    costumeCrypt.setSecretKey(secretKey);
                    return costumeCrypt;
                }
            }else {
                String privateKey = costumeAlg.getPrivateKey();
                String publicKey = costumeAlg.getPublicKey();
                PrivateKey privateKeyFromPem = CryptUtil.getPrivateKeyFromPem(privateKey);
                PublicKey publicKeyFromPem = CryptUtil.getPublicKeyFromPem(publicKey);
                if (ObjectUtil.isNull(privateKeyFromPem) || ObjectUtil.isNull(publicKeyFromPem)){
                    ExceptionUtil.castServerException(READ_KEY_ERROR);
                }
                costumeCrypt.setPrivateKey(privateKeyFromPem);
                costumeCrypt.setPublicKey(publicKeyFromPem);
                return costumeCrypt;
            }
        }
        // 自定义的算法
        AlgEnum algEnum = xkSysCryptProperties.getAlgEnum();
        AbstractCrypt abstractCrypt = algEnum.getAbstractCrypt();
        abstractCrypt.setXkSysCryptProperties(xkSysCryptProperties);
        abstractCrypt.init();
        return abstractCrypt;
    }

    @Bean
    @ConditionalOnProperty(value = "xk.sys.crypt.isSign", havingValue = "false")
    public AbstractSignAlgStrange abstractSignAlgStrange(){
        XkSysCryptProperties.Sign sign = xkSysCryptProperties.getSign();
        XkSysCryptProperties.SignType signType = sign.getSignType();
        if (signType.equals(XkSysCryptProperties.SignType.COSTUME)){
            ServiceLoader<CostumeSignAlgStrange> services = ServiceLoader.load(CostumeSignAlgStrange.class);
            List<CostumeSignAlgStrange> list = new ArrayList<>();
            while(services.iterator().hasNext()){
                 list.add(services.iterator().next());
            }
            if (list.isEmpty()){
                 ExceptionUtil.castServerException(COSTUME_SIGN_ALG_NOT_CONFIG);
            }
            if (list.size() > 1){
                 ExceptionUtil.castServerException(COSTUME_SIGN_ALG_MUST_BE_UNIQUE);
            }
            CostumeSignAlgStrange costumeSignAlgStrange = list.get(0);
            String privateKey = sign.getPrivateKey();
            String publicKey = sign.getPublicKey();
            XkSysCryptProperties.ASYMMETRIC_KEY_SOURCE sourceKeyType = sign.getSourceKeyType();
            if (sourceKeyType.equals(XkSysCryptProperties.ASYMMETRIC_KEY_SOURCE.PERM_FILE)){
                PrivateKey privateKeyFromPem = CryptUtil.getPrivateKeyFromPem(privateKey);
                PublicKey publicKeyFromPem = CryptUtil.getPublicKeyFromPem(publicKey);
                if (ObjectUtil.isNull(privateKeyFromPem) || ObjectUtil.isNull(publicKeyFromPem)){
                    ExceptionUtil.castServerException(READ_KEY_ERROR);
                }
                costumeSignAlgStrange.setPrivateKey(privateKeyFromPem);
                costumeSignAlgStrange.setPublicKey(publicKeyFromPem);
                return costumeSignAlgStrange;
            }else {
                costumeSignAlgStrange.setPrivateKey(SecureUtil.);
                costumeSignAlgStrange.setPublicKey();
                return costumeSignAlgStrange;
            }
        }
    }
}
