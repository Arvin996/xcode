package cn.xk.xcode.service.impl.generate;

import cn.xk.xcode.entity.CodeGen;
import cn.xk.xcode.entity.DataSourceEntity;
import cn.xk.xcode.entity.dto.GenerateCodeDto;
import cn.xk.xcode.exception.core.ServerException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

/**
 * @Author xuk
 * @Date 2024/6/25 10:56
 * @Version 1.0
 * @Description EntityCodeGenerate
 */
@Component(value = "entityCodeGenerate")
public class EntityCodeGenerate implements CodeGenerate{

    @Override
    public CodeGen createCodeGen(Object o) {
        GenerateCodeDto generateCodeDto = (GenerateCodeDto) o;
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

    @Override
    public DataSourceEntity createDataSourceEntity(Object o) {
        GenerateCodeDto generateCodeDto = (GenerateCodeDto) o;
        String url = generateCodeDto.getUrl();
        String dbIp = url.substring(0, url.lastIndexOf(":"));
        String dbPort = url.substring(url.lastIndexOf(":") + 1, url.lastIndexOf("/"));
        return DataSourceEntity
                .builder()
                .dbIp(dbIp)
                .dbPort(dbPort)
                .dbName(generateCodeDto.getDatabaseName())
                .username(generateCodeDto.getUserName())
                .password(generateCodeDto.getPassword())
                .build();
    }

}
