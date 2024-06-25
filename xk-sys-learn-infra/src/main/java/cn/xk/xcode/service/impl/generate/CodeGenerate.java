package cn.xk.xcode.service.impl.generate;

import cn.xk.xcode.entity.dto.GenerateCodeDto;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.File;
import java.io.IOException;

/**
 * @Author xuk
 * @Date 2024/6/25 10:55
 * @Version 1.0
 * @Description CodeGenerate
 */
public interface CodeGenerate
{
    default String generate(GenerateCodeDto generateCodeDto) throws IOException {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setUsername(generateCodeDto.getUserName());
        dataSource.setPassword(generateCodeDto.getPassword());
        String url = generateCodeDto.getUrl();
        String databaseName = generateCodeDto.getDatabaseName();
        String jdbcUrl = "jdbc:mysql://" + url + "/" + databaseName + "?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        dataSource.setJdbcUrl(jdbcUrl);
        File outputFile = createOutputFile();
        GlobalConfig globalConfig = createGlobalConfig(outputFile, generateCodeDto);
        Generator generator = new Generator(dataSource, globalConfig);
        //生成代码
        generator.generate();
        return outputFile.getParent() + "/" + generateCodeDto.getCode() + "/";
    }

    GlobalConfig createGlobalConfig(File file, GenerateCodeDto generateCodeDto);

    File createOutputFile() throws IOException;
}
