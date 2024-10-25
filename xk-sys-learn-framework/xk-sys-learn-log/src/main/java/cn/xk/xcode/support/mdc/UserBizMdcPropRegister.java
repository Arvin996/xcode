package cn.xk.xcode.support.mdc;

/**
 * @Author xuk
 * @Date 2024/10/24 8:41
 * @Version 1.0.0
 * @Description UserBizMdcPropRegister
 **/
public class UserBizMdcPropRegister {

    public UserBizMdcPropRegister putProp(String prop, String expression){
        UserBizMdcContext.putMdcProp(prop, expression);
        return this;
    }
}
