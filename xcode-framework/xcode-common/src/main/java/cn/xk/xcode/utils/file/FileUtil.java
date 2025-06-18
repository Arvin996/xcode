package cn.xk.xcode.utils.file;

import cn.hutool.core.util.IdUtil;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.utils.object.ObjectUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.UUID;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.CONVERT_TO_MULTIPART_FILE_FAILED;

/**
 * 文件读取工具类
 */
@Slf4j
public class FileUtil {

    /**
     * 读取文件内容，作为字符串返回
     */
    public static String readFileAsString(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        }

        if (file.length() > 1024 * 1024 * 1024) {
            throw new IOException("File is too large");
        }

        StringBuilder sb = new StringBuilder((int) (file.length()));
        // 创建字节输入流  
        FileInputStream fis = new FileInputStream(filePath);
        // 创建一个长度为10240的Buffer
        byte[] bbuf = new byte[10240];
        // 用于保存实际读取的字节数  
        int hasRead;
        while ((hasRead = fis.read(bbuf)) > 0) {
            sb.append(new String(bbuf, 0, hasRead));
        }
        fis.close();
        return sb.toString();
    }

    /**
     * 根据文件路径读取byte[] 数组
     */
    public static byte[] readFileByBytes(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
            BufferedInputStream in = null;

            try {
                in = new BufferedInputStream(Files.newInputStream(file.toPath()));
                short bufSize = 1024;
                byte[] buffer = new byte[bufSize];
                int len1;
                while (-1 != (len1 = in.read(buffer, 0, bufSize))) {
                    bos.write(buffer, 0, len1);
                }
                return bos.toByteArray();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException var14) {
                    log.error(var14.getMessage());
                }

                bos.close();
            }
        }
    }

    @SneakyThrows
    public static File createTempFile(String data) {
        File file = createTempFile();
        // 写入内容
        cn.hutool.core.io.FileUtil.writeUtf8String(data, file);
        return file;
    }

    @SneakyThrows
    public static File createTempFile(byte[] data) {
        File file = createTempFile();
        // 写入内容
        cn.hutool.core.io.FileUtil.writeBytes(data, file);
        return file;
    }

    @SneakyThrows
    public static File createTempFile() {
        // 创建文件，通过 UUID 保证唯一
        File file = File.createTempFile(IdUtil.simpleUUID(), null);
        // 标记 JVM 退出时，自动删除
        file.deleteOnExit();
        return file;
    }

    public static MultipartFile convertByteToMultipartFile(byte[] imageBytes, String fileName, String contentType) {
        if (ObjectUtil.isNull(imageBytes)) {
            log.error("获取微信小程序码图片信息失败，接口返回为空");
            ExceptionUtil.castServerException(CONVERT_TO_MULTIPART_FILE_FAILED);
        }
        FileItem item;
        try {
            FileItemFactory factory = new DiskFileItemFactory();
            item = factory.createItem("file", contentType, false, fileName);
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream(imageBytes.length);
                 OutputStream os = item.getOutputStream()) {
                bos.write(imageBytes);
                os.write(bos.toByteArray());
            }
            return new CommonsMultipartFile(item);
        } catch (IOException e) {
            log.error("转换微信小程序码图片信息为MultipartFile时发生错误:{}", e.getMessage());
            throw new ServerException(CONVERT_TO_MULTIPART_FILE_FAILED);
        }
    }

    public static MultipartFile convertFileToMultipartFile(File file, String fileName, String contentType) {
        return convertByteToMultipartFile(cn.hutool.core.io.FileUtil.readBytes(file), fileName, contentType);
    }

}
