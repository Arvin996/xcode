package cn.xk.xcode.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author xuk
 * @Date 2024/6/26 10:55
 * @Version 1.0
 * @Description CodeGen
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CodeGen {

    // todo 配置生成的代码包 而不是直接指定路径
    private String basePackage = "cn.xk.xcode";

    private String sourceDir;

    private String tablePrefix;

    private String[] tables;

    private boolean entityGenerateEnable = true;

    private boolean mapperGenerateEnable = true;

    private boolean serviceGenerateEnable = true;

    private boolean serviceImplGenerateEnable = true;

    private boolean mapperXmlGenerateEnable = true;

    private boolean tableDefGenerateEnable = true;

    private boolean entityWithLombok = true;

    private String entityPackage = "cn.xk.xcode.entity.po";

    private String serviceImplPackage = "cn.xk.xcode.service.impl";

    private String servicePackage = "cn.xk.xcode.service";

    private String mapperPackage = "cn.xk.xcode.mapper";

    private String mapperXmlPackage = "cn.xk.xcode.mapper.xml";

    private String tableDefPackage = "cn.xk.xcode.entity.def";

    private String entityClassSuffix = "Po";
}
