package cn.xk.xcode.core.crypt;

import lombok.Setter;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @Author xuk
 * @Date 2024/10/25 11:43
 * @Version 1.0.0
 * @Description CostumeCrypt
 **/
@Setter
public abstract class CostumeCrypt extends AbstractCrypt {

    protected PrivateKey privateKey;
    protected PublicKey publicKey;
    protected String secretKey;

    @Override
    public String getType() {
        return "costume";
    }

    @Override
    public void init(){}
}
