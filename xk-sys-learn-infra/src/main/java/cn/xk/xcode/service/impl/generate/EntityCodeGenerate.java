package cn.xk.xcode.service.impl.generate;

import cn.xk.xcode.entity.CodeGen;
import cn.xk.xcode.entity.dto.GenerateCodeDto;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2024/6/25 10:56
 * @Version 1.0
 * @Description EntityCodeGenerate
 */
@Component(value = "entityCodeGenerate")
public class EntityCodeGenerate implements CodeGenerate{

    @Override
    public CodeGen createCodeGen(GenerateCodeDto generateCodeDto) {
        return CodeGen.builder()
                .basePackage(generateCodeDto.getCode())
                .entityWithLombok(true)
                .mapperXmlGenerateEnable(false)
                .serviceGenerateEnable(false)
                .mapperGenerateEnable(false)
                .entityGenerateEnable(true)
                .serviceImplGenerateEnable(false)
                .tableDefGenerateEnable(false)
                .sourceDir(getTemplatePath())
                .tablePrefix(generateCodeDto.getTablePre())
                .tables(new String[]{generateCodeDto.getTableName()})
                .entityPackage("temp")
                .entityClassSuffix(generateCodeDto.getEntitySuff()).build();
    }

}
