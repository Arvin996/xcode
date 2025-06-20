package cn.xk.xcode.service.impl.generator;

import cn.xk.xcode.entity.CodeGen;
import cn.xk.xcode.entity.dto.GenerateCodeDto;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2024/6/25 10:56
 * @Version 1.0
 * @Description XmlCodeGenerate
 */
@Component(value = "xmlCodeGenerator")
public class XmlCodeGenerator implements CodeGenerator {

    @Override
    public CodeGen createCodeGen(GenerateCodeDto generateCodeDto) {
        return CodeGen.builder()
                .basePackage(generateCodeDto.getPackageName())
                .entityWithLombok(false)
                .mapperXmlGenerateEnable(true)
                .serviceGenerateEnable(false)
                .mapperGenerateEnable(false)
                .entityGenerateEnable(false)
                .serviceImplGenerateEnable(false)
                .tableDefGenerateEnable(false)
                .tablePrefix(generateCodeDto.getTablePre())
                .mapperXmlPackage(getTemplatePath() + "/temp/" + generateCodeDto.getCode())
                .tables(new String[]{generateCodeDto.getTableName()})
                .entityClassSuffix(generateCodeDto.getEntitySuff()).build();
    }
}