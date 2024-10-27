package cn.xk.xcode.config;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.aop.CryptAdvisor;
import cn.xk.xcode.core.aop.CryptInterceptor;
import cn.xk.xcode.core.crypt.AbstractCrypt;
import cn.xk.xcode.core.crypt.CostumeCrypt;
import cn.xk.xcode.core.enums.AlgEnum;
import cn.xk.xcode.core.sign.AbstractSignAlgStrange;
import cn.xk.xcode.core.sign.CostumeSignAlgStrange;
import cn.xk.xcode.core.sign.DefaultSignAlgStrange;
import cn.xk.xcode.core.utils.CryptUtil;
import cn.xk.xcode.exception.core.ExceptionUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

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
@EnableConfigurationProperties({XkSysCryptProperties.class, XkSysSignProperties.class})
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class XkSysCryptConfig {

    @Resource
    private XkSysCryptProperties xkSysCryptProperties;

    @Resource
    private XkSysSignProperties xkSysSignProperties;

    @Bean
    public CryptAdvisor cryptAdvisor(CryptInterceptor cryptInterceptor){
        return new CryptAdvisor(cryptInterceptor);
    }

    @Bean
    public CryptInterceptor cryptInterceptor(AbstractCrypt abstractCrypt, AbstractSignAlgStrange abstractSignAlgStrange){
        return new CryptInterceptor(abstractCrypt, abstractSignAlgStrange);
    }

    @Bean
    public AbstractCrypt abstractCrypt() {
        XkSysCryptProperties.CipherType cipherType = xkSysCryptProperties.getCipherType();
        if (cipherType.equals(XkSysCryptProperties.CipherType.COSTUME)) {
            ServiceLoader<CostumeCrypt> costumeCrypts = ServiceLoader.load(CostumeCrypt.class);
            List<CostumeCrypt> list = new ArrayList<>();
            while (costumeCrypts.iterator().hasNext()) {
                list.add(costumeCrypts.iterator().next());
            }
            if (list.isEmpty()) {
                ExceptionUtil.castServerException(COSTUME_CRYPT_ALG_NOT_CONFIG);
            }
            if (list.size() > 1) {
                ExceptionUtil.castServerException(COSTUME_CRYPT_ALG_MUST_BE_UNIQUE);
            }
            CostumeCrypt costumeCrypt = list.get(0);
            XkSysCryptProperties.CostumeAlg costumeAlg = xkSysCryptProperties.getCostumeAlg();
            XkSysCryptProperties.CostumeAlgEnum algEnum = costumeAlg.getAlgEnum();
            if (algEnum.equals(XkSysCryptProperties.CostumeAlgEnum.SYMMETRIC)) {
                String secretKey = costumeAlg.getSecretKey();
                if (StrUtil.isBlank(secretKey)) {
                    ExceptionUtil.castServerException(SYMMETRIC_ALG_KEY_MUST_NOT_NULL);
                } else {
                    costumeCrypt.setSecretKey(secretKey);
                    return costumeCrypt;
                }
            } else {
                String privateKey = costumeAlg.getPrivateKeyPemPath();
                String publicKey = costumeAlg.getPublicKeyPemPath();
                if (StrUtil.isBlank(privateKey) || StrUtil.isBlank(publicKey)){
                    ExceptionUtil.castServerException(COSTUME_CRYPT_ALG_PEM_PATH_MUST_NOT_NULL);
                }
                PrivateKey privateKeyFromPem = CryptUtil.getPrivateKeyFromPem(privateKey);
                PublicKey publicKeyFromPem = CryptUtil.getPublicKeyFromPem(publicKey);
                if (ObjectUtil.isNull(privateKeyFromPem) || ObjectUtil.isNull(publicKeyFromPem)) {
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
    public AbstractSignAlgStrange abstractSignAlgStrange() {
        XkSysSignProperties.SignType signType = xkSysSignProperties.getSignType();
        if (signType.equals(XkSysSignProperties.SignType.COSTUME)) {
            ServiceLoader<CostumeSignAlgStrange> services = ServiceLoader.load(CostumeSignAlgStrange.class);
            List<CostumeSignAlgStrange> list = new ArrayList<>();
            while (services.iterator().hasNext()) {
                list.add(services.iterator().next());
            }
            if (list.isEmpty()) {
                ExceptionUtil.castServerException(COSTUME_SIGN_ALG_NOT_CONFIG);
            }
            if (list.size() > 1) {
                ExceptionUtil.castServerException(COSTUME_SIGN_ALG_MUST_BE_UNIQUE);
            }
            CostumeSignAlgStrange costumeSignAlgStrange = list.get(0);
            String privateKey = xkSysSignProperties.getPrivateKeyPemPath();
            String publicKey = xkSysSignProperties.getPublicKeyPemPath();
            if (StrUtil.isBlank(privateKey) || StrUtil.isBlank(publicKey)){
                ExceptionUtil.castServerException(COSTUME_SIGN_ALG_PEM_PATH_MUST_NOT_NULL);
            }
            XkSysCryptProperties.ASYMMETRIC_KEY_SOURCE sourceKeyType = xkSysSignProperties.getSourceKeyType();
            if (sourceKeyType.equals(XkSysCryptProperties.ASYMMETRIC_KEY_SOURCE.APPLICATION_FILE)) {
                ExceptionUtil.castServerException(COSTUME_SIGN_ALG_KEY_MUST_STORE_IN_PEM);
            }else {
                PrivateKey privateKeyFromPem = CryptUtil.getPrivateKeyFromPem(privateKey);
                PublicKey publicKeyFromPem = CryptUtil.getPublicKeyFromPem(publicKey);
                if (ObjectUtil.isNull(privateKeyFromPem) || ObjectUtil.isNull(publicKeyFromPem)) {
                    ExceptionUtil.castServerException(READ_KEY_ERROR);
                }
                costumeSignAlgStrange.setPrivateKey(privateKeyFromPem);
                costumeSignAlgStrange.setPublicKey(publicKeyFromPem);
                return costumeSignAlgStrange;
            }
        }
        // 使用内部的签名算法
        return new DefaultSignAlgStrange(xkSysSignProperties);
    }
}
