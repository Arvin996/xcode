package cn.xk.xcode.config;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.utils.collections.SetUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Set;

/**
 * @Author xuk
 * @Date 2024/11/29 8:49
 * @Version 1.0.0
 * @Description XcodeEnvironmentPostProcessor
 **/
public class XcodeEnvironmentPostProcessor implements EnvironmentPostProcessor {

    public static final Set<String> TARGET_TAG_KEYS = SetUtil.asSet(
            "spring.cloud.nacos.discovery.metadata.tag",
            "xcode.rocketmq.enhance.env"
    );

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        // 1.1 如果没有 xcode.env.isolation.tag 配置项，则不进行配置项的修改
        String tag = environment.getProperty("xcode.env.isolation.tag");
        if (StrUtil.isEmpty(tag)) {
            return;
        }
        // 1.2 需要修改的配置项
        for (String targetTagKey : TARGET_TAG_KEYS) {
            String targetTagValue = environment.getProperty(targetTagKey);
            if (StrUtil.isNotEmpty(targetTagValue)) {
                continue;
            }
            environment.getSystemProperties().put(targetTagKey, tag);
        }
    }
}
