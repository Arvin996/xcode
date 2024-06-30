package cn.xk.xcode.service.impl.generate;

import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.entity.CodeGen;
import cn.xk.xcode.entity.dto.GenerateCodeDto;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2024/6/25 10:56
 * @Version 1.0
 * @Description ServiceCodeGenerate
 */
@Component(value = "serviceCodeGenerate")
public class ServiceCodeGenerate implements CodeGenerate {

    @Override
    public CodeGen createCodeGen(GenerateCodeDto generateCodeDto) {
        CodeGen codeGen = CodeGen.builder()
                .entityWithLombok(true)
                .mapperXmlGenerateEnable(false)
                .serviceGenerateEnable(true)
                .mapperGenerateEnable(false)
                .entityGenerateEnable(false)
                .serviceImplGenerateEnable(false)
                .tableDefGenerateEnable(false)
                .sourceDir(getTemplatePath())
                .tablePrefix(generateCodeDto.getTablePre())
                .servicePackage("temp." + generateCodeDto.getCode())
                .tables(new String[]{generateCodeDto.getTableName()})
                .entityClassSuffix(generateCodeDto.getEntitySuff()).build();
        if (ObjectUtil.isNotNull(generateCodeDto.getPackageName())){
            codeGen.setServicePackage(generateCodeDto.getPackageName().replace(".", "/"));
            codeGen.setSourceDir(getTemplatePath() + "/" + "temp");
        }
        return codeGen;

    }
}

