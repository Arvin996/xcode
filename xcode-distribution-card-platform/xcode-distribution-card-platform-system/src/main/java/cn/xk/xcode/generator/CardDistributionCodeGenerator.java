package cn.xk.xcode.generator;

import cn.xk.xcode.entity.CodeGen;
import cn.xk.xcode.entity.DataSourceEntity;

/**
 * @Author xuk
 * @Date 2025/5/8 14:51
 * @Version 1.0.0
 * @Description CardDistributionCodeGenerator
 **/
public class CardDistributionCodeGenerator implements ICodeGenerator {

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
                .sourceDir(System.getProperty("user.dir") + "/own_module/xcode-parent/xcode-distribution-card-platform/xcode-distribution-card-platform-system/src/main/java")
                .tablePrefix(null)
                .tables(new String[]{"*"})
                .mapperXmlPackage(System.getProperty("user.dir") + "/own_module/xcode-parent/xcode-distribution-card-platform/xcode-distribution-card-platform-system/src/main/resources/mapper")
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
                .dbName("xcode-distribution-card-system")
                .username("root")
                .password("123456")
                .build();
    }

    public static void main(String[] args) {
        ICodeGenerator codeGenerator = new CardDistributionCodeGenerator();
        codeGenerator.doGenerate();
    }
}
