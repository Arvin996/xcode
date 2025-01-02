package cn.xk.xcode.config;

import cn.xk.xcode.enums.TestFlexTransEnum;
import cn.xk.xcode.support.enums.TransEnumConfigurer;
import cn.xk.xcode.support.enums.TransEnumsRegistry;
import org.springframework.context.annotation.Configuration;

/**
 * @Author xuk
 * @Date 2024/12/30 9:14
 * @Version 1.0.0
 * @Description TestFlexTransEnumConfig
 **/
@Configuration
public class TestFlexTransEnumConfig implements TransEnumConfigurer {

    @Override
    public void registry(TransEnumsRegistry registry) {
        registry.register(TestFlexTransEnum.class);
    }
}
