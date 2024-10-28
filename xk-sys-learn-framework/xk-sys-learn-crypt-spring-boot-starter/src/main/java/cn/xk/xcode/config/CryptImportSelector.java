package cn.xk.xcode.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/10/26 14:54
 * @description CryptImportSelector
 */
public class CryptImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{XkSysCryptConfiguration.class.getName()};
    }
}
