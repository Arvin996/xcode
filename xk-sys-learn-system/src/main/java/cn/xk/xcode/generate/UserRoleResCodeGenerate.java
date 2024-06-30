package cn.xk.xcode.generate;

import cn.xk.xcode.entity.CodeGen;
import cn.xk.xcode.entity.DataSourceEntity;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/6/21 19:11
 * @description
 */
public class UserRoleResCodeGenerate implements ICodeGenerate {

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
                .tables(new String[]{"system_user", "system_role", "system_resource", "system_user_role", "system_role_resource"})
                .mapperXmlPackage(System.getProperty("user.dir") + "/own_module/xk-sys-learn/xk-sys-learn-system/src/main/resources/mapper")
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
        UserRoleResCodeGenerate userRoleResCodeGenerate = new UserRoleResCodeGenerate();
        userRoleResCodeGenerate.doGenerate();
    }
}
