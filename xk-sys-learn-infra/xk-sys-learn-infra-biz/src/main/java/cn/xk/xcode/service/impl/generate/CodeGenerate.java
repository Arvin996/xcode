package cn.xk.xcode.service.impl.generate;

import cn.xk.xcode.entity.CodeGen;
import cn.xk.xcode.entity.DataSourceEntity;
import cn.xk.xcode.entity.dto.GenerateCodeDto;
import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.generator.ICodeGenerate;
import com.alibaba.cloud.commons.io.FileUtils;
import com.mybatisflex.codegen.Generator;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

import static cn.xk.xcode.config.InfraGlobalErrorCodeConstants.GEN_CODE_FILE_FAILED;

/**
 * @Author xuk
 * @Date 2024/6/25 10:55
 * @Version 1.0
 * @Description CodeGenerate
 */
public interface CodeGenerate extends ICodeGenerate {

    default String getTemplatePath() {
        return System.getProperty("java.io.tmpdir");
    }

    default CodeGen createCodeGen() {
        return null;
    }

    default DataSourceEntity createDataSourceEntity() {
        return null;
    }

    CodeGen createCodeGen(GenerateCodeDto generateCodeDto);

    default DataSourceEntity createDataSourceEntity(GenerateCodeDto generateCodeDto) {
        String url = generateCodeDto.getUrl();
        String dbIp = url.substring(0, url.lastIndexOf(":"));
        String dbPort = url.substring(url.lastIndexOf(":") + 1);
        return DataSourceEntity
                .builder()
                .dbIp(dbIp)
                .dbPort(dbPort)
                .dbName(generateCodeDto.getDatabaseName())
                .username(generateCodeDto.getUserName())
                .password(generateCodeDto.getPassword())
                .build();
    }

    default void doGenerate(GenerateCodeDto generateCodeDto) {
        Generator generator = new Generator(createDataSource(createDataSourceEntity(generateCodeDto))
                , createGlobalConfig(createCodeGen(generateCodeDto)));
        //生成代码
        generator.generate();
    }

    default String getGenerateFileContent(GenerateCodeDto generateCodeDto) throws IOException {
        // 生成代码
        // 这里要考虑并发问题 文件重复生成的问题
        String pathPrefix = UUID.randomUUID().toString().replace("_", "").substring(0, 6);
        String code = generateCodeDto.getCode();
        generateCodeDto.setCode(pathPrefix);
        doGenerate(generateCodeDto);
        // 这里的fileUrl得判断一下了
        String baseFileUrl = getTemplatePath() + "temp/";
        String delFileUrl;
        String fileUrl;
        if ("xml".equals(code)) {
            fileUrl = baseFileUrl + generateCodeDto.getCode();
            delFileUrl = fileUrl;
        } else {
            if (StringUtils.hasLength(generateCodeDto.getPackageName())) {
                String packageName = generateCodeDto.getPackageName();
                String packUrl = packageName.replace(".", "/");
                fileUrl = baseFileUrl + packUrl;
                delFileUrl = baseFileUrl + packageName.substring(0, packageName.indexOf("."));
            } else {
                fileUrl = baseFileUrl + generateCodeDto.getCode();
                delFileUrl = fileUrl;
            }
        }
        File file = new File(fileUrl);

        File[] files = file.listFiles();
        if (files == null || 0 == files.length) {
            throw new ServerException(GEN_CODE_FILE_FAILED);
        }
        //
        File rFile = files[0];
        String content = FileUtils.readFileToString(rFile, "UTF-8");
        System.out.println(FileSystemUtils.deleteRecursively(Paths.get(delFileUrl)));
        return content;
    }

}
