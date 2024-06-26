package cn.xk.xcode.generate;

import cn.xk.xcode.entity.CodeGen;
import cn.xk.xcode.entity.DataSourceEntity;

/**
 * @Author xuk
 * @Date 2024/6/24 12:51
 * @Version 1.0
 * @Description ClientCodeGenerate
 */
public class ClientCodeGenerate implements ICodeGenerate
{

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
                .sourceDir(System.getProperty("user.dir") + "/own_module/xk-sys-learn/xk-sys-learn-system/src/main/java")
                .tablePrefix("system_")
                .tables(new String[]{"system_client"})
                .mapperXmlPath(System.getProperty("user.dir") + "/own_module/xk-sys-learn/xk-sys-learn-system/src/main/resources/mapper")
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
                .dbName("xk-learn-system")
                .username("root")
                .password("123456")
                .build();
    }

    public static void main(String[] args) {
        ClientCodeGenerate clientCodeGenerate = new ClientCodeGenerate();
        clientCodeGenerate.doGenerate();
    }
}
