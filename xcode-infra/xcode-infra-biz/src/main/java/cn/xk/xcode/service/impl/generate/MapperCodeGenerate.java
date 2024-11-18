package cn.xk.xcode.service.impl.generate;

import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.entity.CodeGen;
import cn.xk.xcode.entity.dto.GenerateCodeDto;
import com.mybatisflex.codegen.config.GlobalConfig;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @Author xuk
 * @Date 2024/6/25 10:55
 * @Version 1.0
 * @Description MapperCodeGenerate
 */
@Component(value = "mapperCodeGenerate")
public class MapperCodeGenerate implements CodeGenerate{

    @Override
    public CodeGen createCodeGen(GenerateCodeDto generateCodeDto) {
        CodeGen codeGen = CodeGen.builder()
                .entityWithLombok(false)
                .mapperXmlGenerateEnable(false)
                .serviceGenerateEnable(false)
                .mapperGenerateEnable(true)
                .entityGenerateEnable(false)
                .serviceImplGenerateEnable(false)
                .tableDefGenerateEnable(false)
                .sourceDir(getTemplatePath())
                .mapperPackage("temp." + generateCodeDto.getCode())
                .tablePrefix(generateCodeDto.getTablePre())
                .tables(new String[]{generateCodeDto.getTableName()})
                .entityClassSuffix(generateCodeDto.getEntitySuff()).build();
        if (ObjectUtil.isNotNull(generateCodeDto.getPackageName())){
            codeGen.setMapperPackage(generateCodeDto.getPackageName().replace(".", "/"));
            codeGen.setSourceDir(getTemplatePath() + "/" + "temp");
        }
        return codeGen;

    }
}
