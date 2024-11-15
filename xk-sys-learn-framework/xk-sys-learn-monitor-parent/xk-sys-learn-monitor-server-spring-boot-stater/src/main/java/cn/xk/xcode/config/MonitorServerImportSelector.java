package cn.xk.xcode.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Author xuk
 * @Date 2024/11/14 14:35
 * @Version 1.0.0
 * @Description MonitorServerImportSelector
 **/
public class MonitorServerImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{MonitorServerConfiguration.class.getName()};
    }
}
