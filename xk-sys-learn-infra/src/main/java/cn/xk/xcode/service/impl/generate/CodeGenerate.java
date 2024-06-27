package cn.xk.xcode.service.impl.generate;

import cn.xk.xcode.entity.CodeGen;
import cn.xk.xcode.entity.DataSourceEntity;
import cn.xk.xcode.entity.dto.GenerateCodeDto;
import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.generate.ICodeGenerate;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author xuk
 * @Date 2024/6/25 10:55
 * @Version 1.0
 * @Description CodeGenerate
 */
public interface CodeGenerate extends ICodeGenerate{

    default String getTemplatePath() {
        return System.getProperty("user.dir");
    }

    default CodeGen createCodeGen() {
        return null;
    }

    default DataSourceEntity createDataSourceEntity() {
        return null;
    }

    CodeGen createCodeGen(GenerateCodeDto generateCodeDto);

    default DataSourceEntity createDataSourceEntity(GenerateCodeDto generateCodeDto){
        String url = generateCodeDto.getUrl();
        String dbIp = url.substring(0, url.lastIndexOf(":"));
        String dbPort = url.substring(url.lastIndexOf(":") + 1, url.lastIndexOf("/"));
        return DataSourceEntity
                .builder()
                .dbIp(dbIp)
                .dbPort(dbPort)
                .dbName(generateCodeDto.getDatabaseName())
                .username(generateCodeDto.getUserName())
                .password(generateCodeDto.getPassword())
                .build();
    }

    default void doGenerate(GenerateCodeDto generateCodeDto){
        Generator generator = new Generator(createDataSource(createDataSourceEntity(generateCodeDto))
                , createGlobalConfig(createCodeGen(generateCodeDto)));
        //生成代码
        generator.generate();
    }

    default String getGenerateFileContent(GenerateCodeDto generateCodeDto) throws IOException {
        // 生成代码
        // 这里要考虑并发问题 文件重复生成的问题
        String pathPrefix = UUID.randomUUID().toString().replace("_", "").substring(0, 6);
        generateCodeDto.setCode(pathPrefix);
        doGenerate(generateCodeDto);
        String fileUrl = getTemplatePath() + "/"
                + generateCodeDto.getCode() + "/"
                + "temp";
        File file = new File(fileUrl);
        File[] files = file.listFiles();
        if (files == null || 0 == files.length) {
            throw new ServerException(500, "生成代码失败");
        }
        File rFile = files[0];
        rFile.deleteOnExit();
        // 将文件内容输出为字符串
        byte[] buffer = new byte[10];
        StringBuilder sb = new StringBuilder();
        FileInputStream fis = new FileInputStream(fileUrl);
        while (fis.read(buffer) != -1) {
            sb.append(new String(buffer));
            buffer = new byte[10];
        }
        fis.close();
        rFile.deleteOnExit();
        return sb.toString();
    }
}
