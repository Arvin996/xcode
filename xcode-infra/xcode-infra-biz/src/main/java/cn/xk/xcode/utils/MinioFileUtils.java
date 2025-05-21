package cn.xk.xcode.utils;

import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.exception.core.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static cn.xk.xcode.config.InfraGlobalErrorCodeConstants.UPLOAD_FILE_ERROR;

/**
 * @Author xuk
 * @Date 2024/6/28 12:45
 * @Version 1.0
 * @Description MinioFileUtils
 */
@Slf4j
public class MinioFileUtils {

    // 生成规则：yyyy/MM/dd/fileId/filename
    public static String getObjectName(String fileName, String fileId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date()).replace("-", "/");
        return String.join("/", date, fileId, fileName);
    }

    // 获取文件md5
    public static String getFileMd5Id(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            return DigestUtils.md5Hex(fileInputStream);
        } catch (Exception e) {
            log.error("获取文件md5失败:{}", e.getMessage());
            throw new ServiceException(UPLOAD_FILE_ERROR);
        }
    }
}
