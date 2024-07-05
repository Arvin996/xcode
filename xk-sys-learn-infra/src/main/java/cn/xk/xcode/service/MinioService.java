package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.UploadFileDto;
import cn.xk.xcode.enums.MinioBucketType;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.utils.MinioFileUtils;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author xuk
 * @Date 2024/6/28 10:53
 * @Version 1.0
 * @Description MinioUtils
 */
@Component
public class MinioService {
    @Resource
    private MinioClient minioClient;

    public Boolean uploadFile(File file, String objectName, String bucketType, String type) {
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
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
        return true;
    }

    public InputStream downloadFile(String bucket, String objectName) {
        InputStream inputStream;
        try {
            inputStream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
        return inputStream;
    }

    public boolean delFile(String bucket, String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
        return true;
    }

}
