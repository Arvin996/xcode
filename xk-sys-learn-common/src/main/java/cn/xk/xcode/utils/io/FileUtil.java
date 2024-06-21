package cn.xk.xcode.utils.io;

import lombok.SneakyThrows;

import java.io.File;
import java.util.UUID;

/**
 * @Author xuk
 * @Date 2024/5/28 12:51
 * @Version 1.0
 * @Description FileUtil
 */
public class FileUtil
{
    @SneakyThrows
    public static File createTempFile(){
        File tempFile = File.createTempFile(UUID.randomUUID().toString().replace("_", "").substring(11), null);
        tempFile.deleteOnExit();
        return tempFile;
    }

    public static File createTempFile(byte[] data){
        File tempFile = createTempFile();
        cn.hutool.core.io.FileUtil.writeBytes(data, tempFile);
        return tempFile;
    }
}
