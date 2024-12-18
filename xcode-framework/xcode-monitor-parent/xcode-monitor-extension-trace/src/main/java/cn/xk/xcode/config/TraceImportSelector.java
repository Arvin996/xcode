package cn.xk.xcode.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Author xuk
 * @Date 2024/12/18 15:35
 * @Version 1.0.0
 * @Description TraceImportSelector
 **/
@SuppressWarnings("all")
public class TraceImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{TraceConfiguration.class.getName()};
    }
}
