package cn.xk.xcode.config;

import cn.xk.xcode.entity.CodeGen;
import cn.xk.xcode.entity.DataSourceEntity;
import cn.xk.xcode.generator.ICodeGenerator;

/**
 * @Author xuk
 * @Date 2024/12/27 11:09
 * @Version 1.0.0
 * @Description CodeGen
 **/
public class CodeGenerator implements ICodeGenerator {
    @Override
    public CodeGen createCodeGen() {
        return CodeGen.builder()
                .basePackage("cn.xk.xcode")
                .entityWithLombok(true)
                .mapperXmlGenerateEnable(true)
                .serviceGenerateEnable(true)
                .mapperGenerateEnable(true)
                .entityGenerateEnable(true)
                .serviceImplGenerateEnable(true)
                .tableDefGenerateEnable(true)
                .sourceDir(System.getProperty("user.dir") + "/own_module/test1-module/src/main/java")
                .tablePrefix("infra_")
                .tables(new String[]{"*"})
                .mapperXmlPackage(System.getProperty("user.dir") + "/own_module/test1-module/src/main/resources/mapper")
                .entityPackage("cn.xk.xcode.entity.po")
                .tableDefPackage("cn.xk.xcode.entity.def")
                .entityClassSuffix("Po").build();
    }

    @Override
    public DataSourceEntity createDataSourceEntity() {
        return DataSourceEntity
                .builder()
                .dbIp("127.0.0.1")
                .dbPort("3306")
                .dbName("xcode-test1")
                .username("root")
                .password("123456")
                .build();
    }

    public static void main(String[] args) {
        CodeGenerator generator = new CodeGenerator();
        generator.doGenerate();
    }
}
