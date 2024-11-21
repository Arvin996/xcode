package cn.xk.xcode.generator;

import cn.xk.xcode.entity.CodeGen;
import cn.xk.xcode.entity.DataSourceEntity;

/**
 * @Author xuk
 * @Date 2024/2/23 15:28
 * @Version 1.0
 * @Description DictCodeGenerator
 */
public class DictCodeGenerator implements ICodeGenerator
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
                .sourceDir(System.getProperty("user.dir") + "/own_module/xcode-parent/xcode-system/src/main/java")
                .tablePrefix("system_")
                .tables(new String[]{"system_dict"})
                .mapperXmlPackage(System.getProperty("user.dir") + "/own_module/xcode-parent/xcode-system-biz/src/main/resources/mapper")
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
                .dbName("xcode-system")
                .username("root")
                .password("123456")
                .build();
    }

    public static void main(String[] args) {
        DictCodeGenerator dictCodeGenerator = new DictCodeGenerator();
        dictCodeGenerator.doGenerate();
    }
}
