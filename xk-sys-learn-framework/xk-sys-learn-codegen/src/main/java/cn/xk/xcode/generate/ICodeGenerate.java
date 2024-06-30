package cn.xk.xcode.generate;

import cn.xk.xcode.entity.CodeGen;
import cn.xk.xcode.entity.DataSourceEntity;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.Objects;

/**
 * @Author xuk
 * @Date 2024/6/26 10:52
 * @Version 1.0
 * @Description ICodeGenerate
 */
public interface ICodeGenerate {

    default void doGenerate() {
        Generator generator = new Generator(createDataSource(createDataSourceEntity())
                , createGlobalConfig(createCodeGen()));
        //生成代码
        generator.generate();
    }

    CodeGen createCodeGen();

    DataSourceEntity createDataSourceEntity();

    default HikariDataSource createDataSource(DataSourceEntity dataSourceEntity) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setUsername(dataSourceEntity.getUsername());
        dataSource.setPassword(dataSourceEntity.getPassword());
        String jdbcUrl = "jdbc:mysql://" + dataSourceEntity.getDbIp()
                + ":" + dataSourceEntity.getDbPort() + "/" + dataSourceEntity.getDbName()
                + "?characterEncoding=utf-8";
        dataSource.setJdbcUrl(jdbcUrl);
        return dataSource;
    }

    default GlobalConfig createGlobalConfig(CodeGen codeGen) {
        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();
        //设置根包
        globalConfig.setBasePackage(codeGen.getBasePackage());
        globalConfig.setSourceDir(codeGen.getSourceDir());
        //设置表前缀和只生成哪些表
        globalConfig.setTablePrefix(codeGen.getTablePrefix());
        globalConfig.setGenerateTable(codeGen.getTables());
        //设置生成 entity 并启用 Lombok
        globalConfig.setEntityGenerateEnable(codeGen.isEntityGenerateEnable());
        globalConfig.setEntityWithLombok(codeGen.isEntityWithLombok());

        // ******
        globalConfig.setEntityPackage(codeGen.getEntityPackage());
        globalConfig.setTableDefPackage(codeGen.getTableDefPackage());
        globalConfig.setServicePackage(codeGen.getServicePackage());
        globalConfig.setServiceImplPackage(codeGen.getServiceImplPackage());
        globalConfig.setMapperPackage(codeGen.getMapperPackage());
        globalConfig.setMapperXmlPath(codeGen.getMapperXmlPackage());


        globalConfig.setEntityClassSuffix(codeGen.getEntityClassSuffix());
        //设置生成 mapper
        globalConfig.setMapperGenerateEnable(codeGen.isMapperGenerateEnable());
        //设置生成 service
        globalConfig.setServiceGenerateEnable(codeGen.isServiceGenerateEnable());
        //设置生成 serviceImpl
        globalConfig.setServiceImplGenerateEnable(codeGen.isServiceImplGenerateEnable());
        //设置生成 mapperXml
        globalConfig.setMapperXmlGenerateEnable(codeGen.isMapperXmlGenerateEnable());

        ////设置生成 表结构对象
        globalConfig.setTableDefGenerateEnable(codeGen.isTableDefGenerateEnable());
        return globalConfig;
    }
}
