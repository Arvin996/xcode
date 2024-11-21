package cn.xk.xcode.generator;

import cn.xk.xcode.entity.CodeGen;
import cn.xk.xcode.entity.DataSourceEntity;

/**
 * @Author xuk
 * @Date 2024/10/29 14:27
 * @Version 1.0.0
 * @Description TakeoutUserCodeGenerator
 **/
public class TakeoutUserCodeGenerator implements ICodeGenerator {
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
                .sourceDir(System.getProperty("user.dir") + "/own_module/xcode-parent/xcode-takeout/xcode-takeout-user-biz/src/main/java")
                .tables(new String[]{"takeout_address", "takeout_user", "takeout_permission", "takeout_role_permission", "takeout_role"})
                .mapperXmlPackage(System.getProperty("user.dir") + "/own_module/xcode-parent/xcode-takeout/xcode-takeout-user-biz/src/main/resources/mapper")
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
                .dbName("xcode-takeout")
                .username("root")
                .password("123456")
                .build();
    }

    public static void main(String[] args) {
        TakeoutUserCodeGenerator takeoutUserCodeGenerator = new TakeoutUserCodeGenerator();
        takeoutUserCodeGenerator.doGenerate();
    }
}
