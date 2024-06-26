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
public class CodeGen
{
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

    private String mapperXmlPath;

    private String entityPackage = "cn.xk.xcode.entity.po";

    private String tableDefPackage = "cn.xk.xcode.entity.def";

    private String entityClassSuffix = "Po";
}
