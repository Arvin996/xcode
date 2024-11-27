package cn.xk.xcode.generator;

import cn.xk.xcode.entity.CodeGen;
import cn.xk.xcode.entity.DataSourceEntity;

/**
 * @Author xuk
 * @Date 2024/11/25 11:38
 * @Version 1.0.0
 * @Description GenCodeGenerator
 **/
public class GenCodeGenerator implements ICodeGenerator{
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
                .sourceDir(System.getProperty("user.dir") + "/own_module/xcode-parent/xcode-infra/xcode-infra-biz/src/main/java")
                .tablePrefix("infra_")
                .tables(new String[]{"infra_gen_table_column", "infra_gen_table"})
                .mapperXmlPackage(System.getProperty("user.dir") + "/own_module/xcode-parent/xcode-infra/xcode-infra-biz/src/main/resources/mapper")
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
                .dbName("xcode-infra")
                .username("root")
                .password("123456")
                .build();
    }

    public static void main(String[] args) {
        GenCodeGenerator generator = new GenCodeGenerator();
        generator.doGenerate();
    }
}
