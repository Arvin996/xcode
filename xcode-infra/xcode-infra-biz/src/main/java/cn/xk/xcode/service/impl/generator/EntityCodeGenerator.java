package cn.xk.xcode.service.impl.generator;

import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.entity.CodeGen;
import cn.xk.xcode.entity.dto.GenerateCodeDto;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2024/6/25 10:56
 * @Version 1.0
 * @Description EntityCodeGenerate
 */
@Component(value = "entityCodeGenerator")
public class EntityCodeGenerator implements CodeGenerator{

    @Override
    public CodeGen createCodeGen(GenerateCodeDto generateCodeDto) {
        // 这里要另外判断
        CodeGen codeGen = CodeGen.builder()
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
                .entityPackage("temp." + generateCodeDto.getCode())
                .entityClassSuffix(generateCodeDto.getEntitySuff()).build();
        if (ObjectUtil.isNotNull(generateCodeDto.getPackageName())){
           codeGen.setEntityPackage(generateCodeDto.getPackageName().replace(".", "/"));
           codeGen.setSourceDir(getTemplatePath() + "/" + "temp");
        }

        return codeGen;
    }

}
