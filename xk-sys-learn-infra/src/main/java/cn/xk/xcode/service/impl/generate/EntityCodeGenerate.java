package cn.xk.xcode.service.impl.generate;

import cn.xk.xcode.entity.dto.GenerateCodeDto;
import com.mybatisflex.codegen.config.GlobalConfig;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author xuk
 * @Date 2024/6/25 10:56
 * @Version 1.0
 * @Description EntityCodeGenerate
 */
@Component(value = "entityCodeGenerate")
public class EntityCodeGenerate implements CodeGenerate{

    @Override
    public GlobalConfig createGlobalConfig(File file, GenerateCodeDto generateCodeDto) {
        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();
        //设置根包
        globalConfig.setBasePackage(generateCodeDto.getCode());
        globalConfig.setSourceDir(file.getParent());
//        //设置表前缀和只生成哪些表
        globalConfig.setTablePrefix(generateCodeDto.getTablePre());
        globalConfig.setGenerateTable(generateCodeDto.getTableName());
        //设置生成 entity 并启用 Lombok
        globalConfig.setEntityGenerateEnable(true);
        globalConfig.setEntityWithLombok(true);
        globalConfig.setEntityPackage(null);
        globalConfig.setEntityClassSuffix(generateCodeDto.getEntitySuff());
        //设置生成 mapper
        globalConfig.setMapperGenerateEnable(false);
        //设置生成 service
        globalConfig.setServiceGenerateEnable(false);
        //设置生成 serviceImpl
        globalConfig.setServiceImplGenerateEnable(false);
        //设置生成 mapperXml
        globalConfig.setMapperXmlGenerateEnable(false);
        ////设置生成 表结构对象
        globalConfig.setTableDefGenerateEnable(false);
        return globalConfig;
    }

    @Override
    public File createOutputFile() throws IOException {
        String fid = UUID.randomUUID().toString();
        fid = fid.replace("-", "").substring(0, 6);
        File tempFile = File.createTempFile("temp" + fid, "entity");
        tempFile.deleteOnExit();
        return tempFile;
    }
}
