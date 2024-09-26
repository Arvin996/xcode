package cn.xk.xcode.core;

import cn.xk.xcode.core.client.AbstractPayClient;
import cn.xk.xcode.core.config.PayClientConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/9/12 8:38
 * @Version 1.0.0
 * @Description GlobalPayClientRegister
 **/
@Getter
@AllArgsConstructor
public class GlobalPayClientRegister {

    private final List<AbstractPayClient<PayClientConfig>> OTHER_PAY_CLIENTS;

    public GlobalPayClientRegister registerPayClient(AbstractPayClient<PayClientConfig>  payClient){
        OTHER_PAY_CLIENTS.add(payClient);
        return this;
    }

    public List<AbstractPayClient<PayClientConfig>> getPayClientList(){
        return OTHER_PAY_CLIENTS;
    }
}
