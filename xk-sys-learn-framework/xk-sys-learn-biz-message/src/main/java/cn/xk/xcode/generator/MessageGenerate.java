package cn.xk.xcode.generator;

import cn.xk.xcode.entity.CodeGen;
import cn.xk.xcode.entity.DataSourceEntity;

/**
 * @Author xuk
 * @Date 2024/7/16 09:35
 * @Version 1.0
 * @Description MessageGenerate
 */
public final class MessageGenerate implements ICodeGenerate{

    @Override
    public CodeGen createCodeGen() {
        return CodeGen.builder()
                .basePackage("cn.xk.xcode")
                .entityWithLombok(true)
                .mapperXmlGenerateEnable(true)
                .serviceGenerateEnable(true)
                .mapperGenerateEnable(true)
                .entityGenerateEnable(true)
                .tablePrefix(null)
                .serviceImplGenerateEnable(true)
                .tableDefGenerateEnable(true)
                .sourceDir(System.getProperty("user.dir") + "/own_module/xk-sys-learn/xk-sys-learn-framework/xk-sys-learn-biz-message/src/main/java")
                .tables(new String[]{"biz_message", "his_biz_message"})
                .mapperXmlPackage(System.getProperty("user.dir") + "/own_module/xk-sys-learn/xk-sys-learn-framework/xk-sys-learn-biz-message/src/main/resources/mapper")
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
                .dbName("xk-learn-message")
                .username("root")
                .password("123456")
                .build();
    }

    public static void main(String[] args) {
        MessageGenerate messageGenerate = new MessageGenerate();
        messageGenerate.doGenerate();
    }
}
