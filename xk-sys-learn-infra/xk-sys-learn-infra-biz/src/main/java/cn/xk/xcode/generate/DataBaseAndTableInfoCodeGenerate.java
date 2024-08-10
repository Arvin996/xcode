package cn.xk.xcode.generate;

import cn.xk.xcode.entity.CodeGen;
import cn.xk.xcode.entity.DataSourceEntity;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @Author xuk
 * @Date 2024/6/24 12:51
 * @Version 1.0
 * @Description ClientCodeGenerate
 */
public class DataBaseAndTableInfoCodeGenerate implements ICodeGenerate {

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
                .sourceDir(System.getProperty("user.dir") + "/own_module/xk-sys-learn/xk-sys-learn-infra/xk-sys-learn-infra-biz/src/main/java")
                .tablePrefix("infra_")
                .tables(new String[]{"infra_database_conn_info", "infra_table_info"})
                .mapperXmlPackage(System.getProperty("user.dir") + "/own_module/xk-sys-learn/xk-sys-learn-infra/xk-sys-learn-infra-biz/src/main/resources/mapper")
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
                .dbName("xk-learn-infra")
                .username("root")
                .password("123456")
                .build();
    }

    public static void main(String[] args) {
        DataBaseAndTableInfoCodeGenerate dataBaseAndTableInfoCodeGenerate = new DataBaseAndTableInfoCodeGenerate();
        dataBaseAndTableInfoCodeGenerate.doGenerate();
    }
}
