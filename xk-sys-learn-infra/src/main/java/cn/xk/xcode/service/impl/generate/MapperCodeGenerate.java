package cn.xk.xcode.service.impl.generate;

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
        return CodeGen.builder()
                .basePackage(generateCodeDto.getCode())
                .entityWithLombok(true)
                .mapperXmlGenerateEnable(false)
                .serviceGenerateEnable(false)
                .mapperGenerateEnable(true)
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
