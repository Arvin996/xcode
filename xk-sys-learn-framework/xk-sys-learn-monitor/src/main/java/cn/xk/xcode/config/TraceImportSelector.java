package cn.xk.xcode.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Author xuk
 * @Date 2024/11/5 10:47
 * @Version 1.0.0
 * @Description TraceImportSelector
 **/
public class TraceImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{TraceConfiguration.class.getName()};
    }
}
