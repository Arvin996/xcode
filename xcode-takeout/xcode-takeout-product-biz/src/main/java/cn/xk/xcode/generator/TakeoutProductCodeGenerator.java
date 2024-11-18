package cn.xk.xcode.generator;

import cn.xk.xcode.entity.CodeGen;
import cn.xk.xcode.entity.DataSourceEntity;

/**
 * @Author xuk
 * @Date 2024/10/31 13:26
 * @Version 1.0.0
 * @Description TakeoutProductCodeGenerator
 **/
public class TakeoutProductCodeGenerator implements ICodeGenerator{

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
                .sourceDir(System.getProperty("user.dir") + "/own_module/xk-sys-learn/xk-sys-learn-takeout/xk-sys-learn-takeout-product-biz/src/main/java")
                .tables(new String[]{"takeout_category", "takeout_dish", "takeout_flavor", "takeout_dish_flavor", "takeout_setmeal", "takeout_setmeal_dish"})
                .mapperXmlPackage(System.getProperty("user.dir") + "/own_module/xk-sys-learn/xk-sys-learn-takeout/xk-sys-learn-takeout-product-biz/src/main/resources/mapper")
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
                .dbName("xk-learn-takeout")
                .username("root")
                .password("123456")
                .build();
    }

    public static void main(String[] args) {
        ICodeGenerator generator = new TakeoutProductCodeGenerator();
        generator.doGenerate();
    }
}
