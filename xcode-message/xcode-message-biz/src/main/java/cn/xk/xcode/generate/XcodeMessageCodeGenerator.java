package cn.xk.xcode.generate;

import cn.xk.xcode.entity.CodeGen;
import cn.xk.xcode.entity.DataSourceEntity;
import cn.xk.xcode.generator.ICodeGenerator;

/**
 * @Author xuk
 * @Date 2025/3/10 9:05
 * @Version 1.0.0
 * @Description XcodeMessageCodeGenerator
 **/
public class XcodeMessageCodeGenerator implements ICodeGenerator {

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
                .sourceDir(System.getProperty("user.dir") + "/own_module/xcode-parent/xcode-message/xcode-message-biz/src/main/java")
                .tables(new String[]{"*"})
                .mapperXmlPackage(System.getProperty("user.dir") + "/own_module/xcode-parent/xcode-message/xcode-message-biz/src/main/resources/mapper")
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
                .dbName("xcode-message")
                .username("root")
                .password("123456")
                .build();

    }

    public static void main(String[] args) {
        ICodeGenerator generator = new XcodeMessageCodeGenerator();
        generator.doGenerate();
    }
}
