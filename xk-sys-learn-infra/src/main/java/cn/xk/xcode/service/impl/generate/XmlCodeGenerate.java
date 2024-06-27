package cn.xk.xcode.service.impl.generate;

import cn.xk.xcode.entity.CodeGen;
import cn.xk.xcode.entity.dto.GenerateCodeDto;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2024/6/25 10:56
 * @Version 1.0
 * @Description XmlCodeGenerate
 */
@Component(value = "xmlCodeGenerate")
public class XmlCodeGenerate implements CodeGenerate {

    @Override
    public CodeGen createCodeGen(GenerateCodeDto generateCodeDto) {
        return CodeGen.builder()
                .basePackage(generateCodeDto.getCode())
                .entityWithLombok(true)
                .mapperXmlGenerateEnable(true)
                .serviceGenerateEnable(true)
                .mapperGenerateEnable(false)
                .entityGenerateEnable(false)
                .serviceImplGenerateEnable(false)
                .tableDefGenerateEnable(false)
                .sourceDir(getTemplatePath())
                .tablePrefix(generateCodeDto.getTablePre())
                .tables(new String[]{generateCodeDto.getTableName()})
                .entityPackage("temp")
                .entityClassSuffix(generateCodeDto.getEntitySuff()).build();
    }
}