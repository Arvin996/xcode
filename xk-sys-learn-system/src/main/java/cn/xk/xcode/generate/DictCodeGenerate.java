package cn.xk.xcode.generate;

import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @Author xuk
 * @Date 2024/2/23 15:28
 * @Version 1.0
 * @Description CodeGenerate
 */
public class DictCodeGenerate
{
    public static void main(String[] args) {
        //配置数据源
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/xk-learn-system?characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");

        //创建配置内容，两种风格都可以。
        GlobalConfig globalConfig = createGlobalConfigUseStyle1();
        //GlobalConfig globalConfig = createGlobalConfigUseStyle2();

        //通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig);

        //生成代码
        generator.generate();
    }

    public static GlobalConfig createGlobalConfigUseStyle1() {
        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();

        //设置根包
        globalConfig.setBasePackage("cn.xk.xcode");

        globalConfig.setSourceDir(System.getProperty("user.dir") + "/own_module/xk-sys-learn/xk-sys-learn-system/src/main/java");
//        //设置表前缀和只生成哪些表
        globalConfig.setTablePrefix("system_");
        globalConfig.setGenerateTable("system_dict");
        //设置生成 entity 并启用 Lombok
        globalConfig.setEntityGenerateEnable(true);
        globalConfig.setEntityWithLombok(true);

        globalConfig.setEntityPackage("cn.xk.xcode.entity.po");

        globalConfig.setTableDefPackage("cn.xk.xcode.entity.def");

        globalConfig.setEntityClassSuffix("Po");

        //设置生成 mapper
        globalConfig.setMapperGenerateEnable(true);

        //设置生成 service
        globalConfig.setServiceGenerateEnable(true);

        //设置生成 serviceImpl
        globalConfig.setServiceImplGenerateEnable(true);

        //设置生成 mapperXml
        globalConfig.setMapperXmlGenerateEnable(true);

        globalConfig.setMapperXmlPath(System.getProperty("user.dir") + "/own_module/xk-sys-learn/xk-sys-learn-system/src/main/resources/mapper");

        ////设置生成 表结构对象
        globalConfig.setTableDefGenerateEnable(true);

        return globalConfig;
    }
}
