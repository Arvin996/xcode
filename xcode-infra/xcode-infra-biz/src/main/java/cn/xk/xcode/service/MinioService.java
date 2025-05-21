package cn.xk.xcode.service;

import cn.xk.xcode.exception.core.ServiceException;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;

import static cn.xk.xcode.config.InfraGlobalErrorCodeConstants.DOWNLOAD_FILE_ERROR;

/**
 * @Author xuk
 * @Date 2024/6/28 10:53
 * @Version 1.0
 * @Description MinioUtils
 */
@Service
@Slf4j
public class MinioService {

    @Resource
    private MinioClient minioClient;

    private  static final int MINIO_FILE_URL_EXPIRED_TIME = 60;

    public Boolean uploadFile(File file, String objectName, String bucketType, String type) {
        boolean b = false;
        try {
            file.deleteOnExit();
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketType)
                            .object(objectName)
                            .filename(file.getAbsolutePath())
                            .contentType(type)
                            .build()
            );
            b = true;
            return b;
        } catch (Exception e) {
            log.error("上传文件失败:{}", e.getMessage());
            return b;
        }
    }

    public InputStream downloadFile(String bucket, String objectName) {
        InputStream inputStream;
        try {
            inputStream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            log.error("下载文件失败:{}", e.getMessage());
            return null;
        }
        return inputStream;
    }

    public boolean delFile(String bucket, String objectName) {
        boolean b = false;
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .build());
            b = true;
            return b;
        } catch (Exception e) {
            log.error("删除文件失败:{}", e.getMessage());
            return b;
        }
    }

    public String getFileUrl(String bucket, String objectName) {
        String url;
        try {
            url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .expiry(MINIO_FILE_URL_EXPIRED_TIME)
                    .build());
        } catch (Exception e) {
            log.error("获取文件url失败:{}", e.getMessage());
            return null;
        }
        return url;
    }

}
