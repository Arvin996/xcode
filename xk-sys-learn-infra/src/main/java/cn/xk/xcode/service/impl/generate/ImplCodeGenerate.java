package cn.xk.xcode.service.impl.generate;

import cn.xk.xcode.entity.CodeGen;
import cn.xk.xcode.entity.dto.GenerateCodeDto;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2024/6/25 10:57
 * @Version 1.0
 * @Description ImplCodeGenerate
 */
@Component(value = "implCodeGenerate")
public class ImplCodeGenerate implements CodeGenerate
{

    @Override
    public CodeGen createCodeGen(GenerateCodeDto generateCodeDto) {
        return CodeGen.builder()
                .basePackage(generateCodeDto.getCode())
                .entityWithLombok(true)
                .mapperXmlGenerateEnable(false)
                .serviceGenerateEnable(false)
                .mapperGenerateEnable(false)
                .entityGenerateEnable(false)
                .serviceImplGenerateEnable(true)
                .tableDefGenerateEnable(false)
                .sourceDir(getTemplatePath())
                .tablePrefix(generateCodeDto.getTablePre())
                .tables(new String[]{generateCodeDto.getTableName()})
                .entityPackage("temp")
                .entityClassSuffix(generateCodeDto.getEntitySuff()).build();
    }
}
