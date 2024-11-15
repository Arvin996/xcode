package cn.xk.xcode.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Author xuk
 * @Date 2024/11/13 10:49
 * @Version 1.0.0
 * @Description MonitorClientImportSelector
 **/
public class MonitorClientImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{MonitorClientConfiguration.class.getName()};
    }
}
