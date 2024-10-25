package cn.xk.xcode.support.mdc;

import cn.hutool.core.lang.mutable.MutablePair;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author xuk
 * @Date 2024/10/24 8:37
 * @Version 1.0.0
 * @Description UserBizMdcContext
 **/
public class UserBizMdcContext {

    private static final Set<MutablePair<String, String>> MDC_PROP_CONTEXT  = new HashSet<>();

    public static void putMdcProp(String prop, String expression){
        if (contains(prop)){
            return;
        }
        MDC_PROP_CONTEXT.add(new MutablePair<>(prop, expression));
    }

    public static Set<MutablePair<String, String>> getMdcPropContext(){
        return MDC_PROP_CONTEXT;
    }

    public static boolean contains(String prop){
        return MDC_PROP_CONTEXT.stream().anyMatch(v -> v.getKey().equals(prop));
    }
}
