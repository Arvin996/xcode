package cn.xk.xcode.service.impl.generate;

import cn.xk.xcode.entity.dto.GenerateCodeDto;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.File;
import java.io.FileInputStream;
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
        File file = new File(outputFile.getParent() + "/" + generateCodeDto.getCode() + "/" + generateCodeDto.getCode());
        File[] files = file.listFiles();
        File file1 = files[0];
        StringBuilder sb = new StringBuilder();
        if ("java".equals(file1.getName().substring(file1.getName().lastIndexOf(".") + 1))){
            //将文件内容输出为文本
            FileInputStream fis = new FileInputStream(file1);
            byte[] buffer = new byte[10];
            while (fis.read(buffer) != -1) {
                sb.append(new String(buffer));
                buffer = new byte[10];
            }
            fis.close();

        }
        return sb.toString();
    }

    GlobalConfig createGlobalConfig(File file, GenerateCodeDto generateCodeDto);

    File createOutputFile() throws IOException;
}
