package cn.xk.xcode.generator;

import cn.xk.xcode.entity.CodeGen;
import cn.xk.xcode.entity.DataSourceEntity;

/**
 * @Author xuk
 * @Date 2025/2/10 16:11
 * @Version 1.0.0
 * @Description XcodeMallProductGenerator
 **/
public class XcodeMallProductGenerator implements ICodeGenerator{

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
                .sourceDir(System.getProperty("user.dir") + "/own_module/xcode-parent/xcode-member/xcode-member-biz/src/main/java")
                .tables(new String[]{"*"})
                .mapperXmlPackage(System.getProperty("user.dir") + "/own_module/xcode-parent/xcode-member/xcode-member-biz/src/main/resources/mapper")
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
                .dbName("xcode-member")
                .username("root")
                .password("123456")
                .build();

    }

    public static void main(String[] args) {
        XcodeMallProductGenerator xcodeMallProductGenerator = new XcodeMallProductGenerator();
        xcodeMallProductGenerator.doGenerate();
    }
}
