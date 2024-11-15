package cn.xk.xcode.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Author xuk
 * @Date 2024/11/5 11:05
 * @Version 1.0.0
 * @Description MetricsImportSelector
 **/
public class MetricsImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{MetricsConfiguration.class.getName()};
    }
}
