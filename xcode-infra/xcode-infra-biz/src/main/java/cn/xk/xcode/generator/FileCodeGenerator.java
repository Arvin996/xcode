package cn.xk.xcode.generator;

import cn.xk.xcode.entity.CodeGen;
import cn.xk.xcode.entity.DataSourceEntity;

/**
 * @Author xuk
 * @Date 2024/6/26 09:26
 * @Version 1.0
 * @Description FileCodeGenerator
 */
public class FileCodeGenerator implements ICodeGenerator{

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
                .sourceDir(System.getProperty("user.dir") + "/own_module/xk-sys-learn/xk-sys-learn-infra/src/main/java")
                .tablePrefix("infra_")
                .tables(new String[]{"infra_sys_files", "infra_sys_files_process"})
                .mapperXmlPackage(System.getProperty("user.dir") + "/own_module/xk-sys-learn/xk-sys-learn-infra/src/main/resources/mapper")
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
        FileCodeGenerator fileCodeGenerator = new FileCodeGenerator();
        fileCodeGenerator.doGenerate();
    }
}
