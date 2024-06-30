package cn.xk.xcode.service.impl.generate;

import cn.hutool.core.util.ObjectUtil;
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
        CodeGen codeGen = CodeGen.builder()
                .entityWithLombok(false)
                .mapperXmlGenerateEnable(false)
                .serviceGenerateEnable(false)
                .mapperGenerateEnable(false)
                .entityGenerateEnable(false)
                .serviceImplGenerateEnable(true)
                .tableDefGenerateEnable(false)
                .sourceDir(getTemplatePath())
                .serviceImplPackage("temp." + generateCodeDto.getCode())
                .tablePrefix(generateCodeDto.getTablePre())
                .tables(new String[]{generateCodeDto.getTableName()})
                .entityClassSuffix(generateCodeDto.getEntitySuff()).build();
        if (ObjectUtil.isNotNull(generateCodeDto.getPackageName())){
            codeGen.setServiceImplPackage(generateCodeDto.getPackageName().replace(".", "/"));
            codeGen.setSourceDir(getTemplatePath() + "/" + "temp");
        }
        return codeGen;
    }
}
