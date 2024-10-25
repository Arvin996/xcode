package cn.xk.xcode.core.sign;



import lombok.Setter;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @Author xuk
 * @Date 2024/10/25 10:01
 * @Version 1.0.0
 * @Description CostumeSignAlgStrange
 **/
@Setter
public abstract class CostumeSignAlgStrange extends AbstractSignAlgStrange {

    protected PrivateKey privateKey;

    protected PublicKey publicKey;

}
