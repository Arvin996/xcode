package cn.xk.xcode.core;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import cn.xk.xcode.config.XcodeOssProperties;
import cn.xk.xcode.entity.*;
import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.IntErrorCode;
import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.pojo.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.core.async.BlockingInputStreamAsyncRequestBody;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.CompletedUpload;
import software.amazon.awssdk.transfer.s3.model.Upload;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @Author xuk
 * @Date 2025/2/18 13:42
 * @Version 1.0.0
 * @Description S3Service
 **/
@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("all")
public class OssClient implements InitializingBean {

    private final XcodeOssProperties properties;
    private final S3Client s3Client;
    private final S3AsyncClient s3AsyncClient;
    private final S3Presigner s3Presigner;
    private final S3TransferManager transferManager;
    private static final String FILE_SEPARATOR = "/";
    private static final ErrorCode UPLOAD_FILE_ERROR = new IntErrorCode(1600_0_500, "上传文件失败, 错误信息:{}");
    private static final ErrorCode DOWNLOAD_FILE_ERROR = new IntErrorCode(1600_0_501, "下载文件失败, 错误信息:{}");
    private static final ErrorCode DELETE_FILE_ERROR = new IntErrorCode(1600_0_502, "删除文件失败, 错误信息:{}");
    private static final ErrorCode INIT_UPLOAD_CHUNK_FILE_ERROR = new IntErrorCode(1600_0_503, "初始化分片上传失败, 错误信息:{}");
    private static final ErrorCode UPLOAD_CHUNK_FILE_ERROR = new IntErrorCode(1600_0_504, "上传分片失败, 错误信息:{}");
    private static final ErrorCode CHECK_CHUNK_FILE_EXISTS_ERROR = new IntErrorCode(1600_0_505, "检查分片文件是否存在失败, 错误信息:{}");
    private static final ErrorCode MERGE_CHUNK_FILE_ERROR = new IntErrorCode(1600_0_506, "合并分片文件失败, 错误信息:{}");

    public CommonResult<InitUploadChunkFileRespDto> initUploadChunkFile(InitUploadChunkFileReqDto initUploadChunkFileReqDto) {
        try {
            if (StrUtil.isEmpty(initUploadChunkFileReqDto.getBucketName())) {
                initUploadChunkFileReqDto.setBucketName(properties.getDefaultBucket());
            }
            XcodeOssProperties.FileNamingEnum naming = properties.getNaming();
            String objectName;
            if (naming.equals(XcodeOssProperties.FileNamingEnum.ORIGINAL)) {
                objectName = generateByOriginalFileName(initUploadChunkFileReqDto.getFilename());
            } else {
                objectName = generateFileNameByUuid(initUploadChunkFileReqDto.getFilename());
            }
            objectName = initUploadChunkFileReqDto.getDirTag() + objectName;
            CreateMultipartUploadRequest createMultipartUploadRequest = CreateMultipartUploadRequest
                    .builder()
                    .bucket(initUploadChunkFileReqDto.getBucketName())
                    .key(objectName)
                    .build();
            CreateMultipartUploadResponse multipartUpload = s3Client.createMultipartUpload(createMultipartUploadRequest);
            InitUploadChunkFileRespDto chunkFileRespDto = new InitUploadChunkFileRespDto();
            chunkFileRespDto.setFileUploadId(multipartUpload.uploadId());
            chunkFileRespDto.setObjectName(objectName);
            return CommonResult.success(chunkFileRespDto);
        } catch (Exception e) {
            log.error("初始化分片上传失败, 错误信息:{}", e.getMessage());
            throw new ServerException(INIT_UPLOAD_CHUNK_FILE_ERROR, e.getMessage());
        }
    }

    public CommonResult<UploadChunkFileRespDto> uploadChunkFile(UploadChunkFileReqDto uploadChunkFileReqDto) {
        try {
            if (StrUtil.isEmpty(uploadChunkFileReqDto.getBucketName())) {
                uploadChunkFileReqDto.setBucketName(properties.getDefaultBucket());
            }
            UploadPartRequest uploadPartRequest = UploadPartRequest
                    .builder()
                    .bucket(uploadChunkFileReqDto.getBucketName())
                    .key(uploadChunkFileReqDto.getObjectName())
                    .partNumber(uploadChunkFileReqDto.getCurrentChunk())
                    .uploadId(uploadChunkFileReqDto.getUploadId())
                    .build();
            UploadPartResponse uploadPartResponse = s3Client.uploadPart(uploadPartRequest, RequestBody.fromBytes(IOUtils.toByteArray(uploadChunkFileReqDto.getFile().getInputStream())));
            UploadChunkFileRespDto uploadChunkFileResp = new UploadChunkFileRespDto();
            uploadChunkFileResp.setFileUploadId(uploadChunkFileReqDto.getUploadId());
            uploadChunkFileResp.setCurrentChunk(uploadChunkFileReqDto.getCurrentChunk());
            uploadChunkFileResp.setObjectName(uploadChunkFileReqDto.getObjectName());
            uploadChunkFileResp.setFileName(uploadChunkFileReqDto.getFile().getOriginalFilename());
            uploadChunkFileResp.setETag(uploadPartResponse.eTag());
            return CommonResult.success(uploadChunkFileResp);
        } catch (Exception e) {
            log.error("上传分片失败, 错误信息:{}, 分片参数：{}", e.getMessage(), uploadChunkFileReqDto);
            throw new ServerException(UPLOAD_CHUNK_FILE_ERROR, e.getMessage());
        }
    }

    public CommonResult<Boolean> checkChunkFileExists(CheckChunkFileExistsReqDto checkChunkFileExistsReqDto) {
        try {
            if (StrUtil.isEmpty(checkChunkFileExistsReqDto.getBucketName())) {
                checkChunkFileExistsReqDto.setBucketName(properties.getDefaultBucket());
            }
            ListPartsRequest listPartsRequest = ListPartsRequest
                    .builder()
                    .bucket(checkChunkFileExistsReqDto.getBucketName())
                    .key(checkChunkFileExistsReqDto.getObjectName())
                    .uploadId(checkChunkFileExistsReqDto.getUploadId())
                    .build();
            List<Part> parts = s3Client.listParts(listPartsRequest).parts();
            boolean b = parts.stream().anyMatch(part -> Objects.equals(part.partNumber(), checkChunkFileExistsReqDto.getChunkName()) && part.eTag().equals(checkChunkFileExistsReqDto.getETag()));
            return CommonResult.success(b);
        } catch (Exception e) {
            log.error("检查分片文件是否存在失败, 错误信息:{}, 分片参数：{}", e.getMessage(), checkChunkFileExistsReqDto);
            throw new ServerException(CHECK_CHUNK_FILE_EXISTS_ERROR, e.getMessage());
        }
    }

    public CommonResult<MergeUploadChunkFliesRespDto> mergerChunkFile(MergeUploadChunkFliesReqDto mergeUploadChunkFliesReqDto) {
        try {
            if (StrUtil.isEmpty(mergeUploadChunkFliesReqDto.getBucketName())) {
                mergeUploadChunkFliesReqDto.setBucketName(properties.getDefaultBucket());
            }
            CompleteMultipartUploadRequest completeMultipartUploadRequest = CompleteMultipartUploadRequest
                    .builder()
                    .bucket(mergeUploadChunkFliesReqDto.getBucketName())
                    .key(mergeUploadChunkFliesReqDto.getObjectName())
                    .uploadId(mergeUploadChunkFliesReqDto.getUploadId())
                    .multipartUpload(CompletedMultipartUpload
                            .builder()
                            .parts(mergeUploadChunkFliesReqDto
                                    .getChunkFileInfos()
                                    .stream()
                                    .map(chunkInfo -> CompletedPart
                                            .builder()
                                            .partNumber(chunkInfo.getCurrentChunk())
                                            .eTag(chunkInfo.getETag())
                                            .build())
                                    .collect(Collectors.toList()))
                            .build())
                    .build();
            // 合并文件
            CompleteMultipartUploadResponse uploadResponse = s3AsyncClient.completeMultipartUpload(completeMultipartUploadRequest).join();
            return CommonResult.success(MergeUploadChunkFliesRespDto.builder()
                    .url(getUrl(mergeUploadChunkFliesReqDto.getBucketName()) + "/" + mergeUploadChunkFliesReqDto.getObjectName())
                    .eTag(normalizeETag(uploadResponse.eTag()))
                    .build());
        } catch (Exception e) {
            log.error("合并分片文件失败, 错误信息:{}, 参数：{}", e.getMessage(), mergeUploadChunkFliesReqDto);
            throw new ServerException(MERGE_CHUNK_FILE_ERROR, e.getMessage());
        }
    }

    public UploadResult uploadToDefaultBucket(MultipartFile file) throws IOException {
        return uploadToDefaultBucket(file, autoGenerateDirTag(file));
    }

    public UploadResult uploadToDefaultBucket(MultipartFile file, String dirTag) throws IOException {
        return upload(file, properties.getDefaultBucket(), dirTag);
    }

    public UploadResult upload(MultipartFile file, String bucketName) throws IOException {
        return upload(file, bucketName, autoGenerateDirTag(file));
    }


    public UploadResult upload(MultipartFile file, String bucketName, String dirTag) throws IOException {
        XcodeOssProperties.FileNamingEnum naming = properties.getNaming();
        String objectName;
        if (naming.equals(XcodeOssProperties.FileNamingEnum.ORIGINAL)) {
            objectName = dirTag + generateByOriginalFileName(file.getOriginalFilename());
            if (isFileExists(bucketName, objectName)) {
                String ext = StringUtils.getFilenameExtension(objectName);
                assert ext != null;
                DateTimeFormatter df = DateTimeFormatter.ofPattern("HHmmss.SSS"); // 获取时分秒.毫秒
                String localTime = df.format(LocalDateTime.now());
                objectName = objectName.split("." + ext)[0] + " (" + localTime + ")." + ext;
            }
        } else {
            objectName = dirTag + generateFileNameByUuid(file.getOriginalFilename());
        }
        return upload(file.getInputStream(), bucketName, objectName, file.getSize(), file.getContentType());
    }


    public UploadResult upload(InputStream inputStream, String bucketName, String objectName, Long size, String contextType) {
        try {
            BlockingInputStreamAsyncRequestBody body = AsyncRequestBody.forBlockingInputStream(size);
            Upload upload = transferManager.upload(x -> x.requestBody(body)
                    .putObjectRequest(y -> y.bucket(bucketName).key(objectName).contentType(contextType).build()).build());
            body.writeInputStream(inputStream);
            CompletedUpload uploadResult = upload.completionFuture().join();
            String eTag = normalizeETag(uploadResult.response().eTag());
            String url = getUrl(bucketName) + "/" + objectName;
            return UploadResult.builder().url(url).objectName(objectName).dirTag(getDirTag(objectName)).filename(getFileName(objectName))
                    .contextType(contextType).size(size).eTag(eTag).build();
        } catch (Exception e) {
            throw new ServerException(UPLOAD_FILE_ERROR, e.getMessage());
        }
    }

    public boolean isFileExists(String bucketName, String objectName) {
        try {
            s3Client.headObject(b -> b.bucket(bucketName).key(objectName));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getDefaultBucketPrivateUrl(String objectName) {
        return getPrivateUrl(properties.getDefaultBucket(), objectName);
    }

    /**
     * 获取私有文件链接
     *
     * @param objectName 对象名（唯一，可包含路径）
     * @return 链接
     */
    public String getPrivateUrl(String bucketName, String objectName) {
        return getPrivateUrl(bucketName, objectName, properties.getPreSingerExpireTime());
    }

    /**
     * 获取私有文件的访问链接。
     * <p>
     * 该方法生成一个带有签名的私有文件访问链接，允许在指定的有效期内访问存储在 S3 兼容服务中的私有文件。 例如：
     *
     * @param objectName 对象名（唯一，可包含路径），用于指定要访问的文件
     * @param second     链接的有效期（秒），决定该链接在多长时间内有效
     * @return 生成的私有文件访问链接
     */

    public String getPrivateUrl(String bucketName, String objectName, Long second) {
        URL url = s3Presigner.presignGetObject(x -> x.signatureDuration(Duration.ofSeconds(second))
                .getObjectRequest(y -> y.bucket(bucketName).key(objectName).build()).build()).url();
        return url.toString();
    }

    public void downloadFileFromDefaultBucket(String objectName, OutputStream outputStream) {
        download(properties.getDefaultBucket(), objectName, outputStream);
    }

    public void downloadFileFromDefaultBucket(String objectName, HttpServletResponse response, String showFileName) {
        download(properties.getDefaultBucket(), objectName, response, showFileName);
    }

    public CommonResult<String> downloadFileFromDefaultBucket(String objectName) {
        return download(properties.getDefaultBucket(), objectName);
    }

    /**
     * 文件下载
     *
     * @param objectName   对象名（唯一，可包含路径）
     * @param outputStream 输出流
     */
    public void download(String bucketName, String objectName, OutputStream outputStream) {
        try {
            // 构建请求
            GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(objectName).build();

            // 下载对象到内存字节缓冲区
            s3AsyncClient.getObject(getObjectRequest, AsyncResponseTransformer.toBytes()).thenAccept(response -> {
                try {
                    // 写入输出流
                    outputStream.write(response.asByteArray());
                    outputStream.flush();
                } catch (Exception e) {
                    throw new ServerException(DOWNLOAD_FILE_ERROR, e.getMessage());
                }
            }).join(); // 同步等待完成
        } catch (Exception e) {
            throw new ServerException(DOWNLOAD_FILE_ERROR, e.getMessage());
        }
    }

    /**
     * 文件下载
     *
     * @param objectName 对象名（唯一，可包含路径）
     * @param response   响应
     */
    public void download(String bucketName, String objectName, HttpServletResponse response, String showFileName) {
        try {
            // 构建请求
            GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(objectName).build();
            String filename = showFileName;
            if (showFileName.isEmpty()) {
                filename = getFileName(objectName);
            }
            OutputStream os = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
            // 下载对象到内存字节缓冲区
            s3AsyncClient.getObject(getObjectRequest, AsyncResponseTransformer.toBytes()).thenAccept(responseBytes -> {
                try {
                    // 写入响应流
                    os.write(responseBytes.asByteArray());
                    os.flush();
                } catch (IOException e) {
                    throw new ServerException(DOWNLOAD_FILE_ERROR, e.getMessage());
                }
            }).join(); // 同步等待完成
        } catch (Exception e) {
            throw new ServerException(DOWNLOAD_FILE_ERROR, e.getMessage());
        }
    }

    public CommonResult<String> download(String bucketName, String objectName) {
        try {
            CommonResult<String> commonResult = new CommonResult<>();
            // 构建请求
            GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(objectName).build();
            // 下载对象到内存字节缓冲区
            s3AsyncClient.getObject(getObjectRequest, AsyncResponseTransformer.toBytes()).thenAccept(response -> {
                commonResult.setData("base64:" + response.response().contentType() + "," + Base64Utils.encodeToString(response.asByteArray()));
            }).join(); // 同步等待完成
            return commonResult;
        } catch (Exception e) {
            throw new ServerException(DOWNLOAD_FILE_ERROR, e.getMessage());
        }
    }

    public CommonResult<String> deleteFileFromDefaultBucket(String objectName) {
        return deleteFile(properties.getDefaultBucket(), objectName);
    }

    public CommonResult<String> deleteFile(String bucketName, String objectName) {
        try {
            s3Client.deleteObject(b -> b.bucket(bucketName).key(objectName));
            return CommonResult.success("success");
        } catch (Exception e) {
            log.error("删除文件失败, 桶信息: {} 对象信息: {}, 错误信息:{}", bucketName, objectName, e.getMessage());
            return CommonResult.error(DELETE_FILE_ERROR, e.getMessage());
        }
    }

    private String getUrl(String bucketName) {
        String domain = properties.getDomain();
        String endpoint = properties.getEndpoint();
        String scheme = properties.isHttps() ? "https" : "http";
        // 非 MinIO 处理
        if (!properties.isMinIoCClient()) {
            if (StrUtil.isNotEmpty(domain)) {
                return (domain.startsWith("https://") || domain.startsWith("http://")) ? domain : scheme + domain;
            } else {
                return scheme + bucketName + "." + endpoint; // eg: https://your_bucket_name.oss-cn-beijing.aliyuncs.com
            }
        }
        if (StrUtil.isNotEmpty(domain)) {
            return (domain.startsWith("https://") || domain.startsWith("http://"))
                    ? domain + "/" + bucketName
                    : scheme + domain + "/" + bucketName;
        } else {
            return scheme + endpoint + "/" + bucketName;
        }
    }

    /**
     * 生成文件名 eg: /20231212/4150e7c6-b92f-419d-b804-0e8be80f5e71.png
     *
     * @param originalFilename 原始文件名
     * @return 文件名
     */
    private String generateFileNameByUuid(String originalFilename) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        String localTime = df.format(LocalDate.now());
        String pathStr = FILE_SEPARATOR + localTime + FILE_SEPARATOR;
        String extension = StringUtils.getFilenameExtension(originalFilename);
        String fileName = UUID.randomUUID().toString();
        return pathStr + fileName + "." + extension;
    }

    /**
     * 生成文件名 eg: /20231212/logo.jpg
     *
     * @param originalFilename 原始文件名
     * @return 文件名
     */
    private String generateByOriginalFileName(String originalFilename) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        String localTime = df.format(LocalDate.now());
        String pathStr = FILE_SEPARATOR + localTime + FILE_SEPARATOR;
        return pathStr + originalFilename;
    }

    private String getFileName(String path) {
        int lastSlashIndex = path.lastIndexOf('/');
        return (lastSlashIndex != -1) ? path.substring(lastSlashIndex + 1) : path;
    }

    private String getDirTag(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        // 找到第一个'/'的位置
        int index = str.indexOf('/');
        // 如果第一个字符是'/'，找到第二个'/'的位置
        if (index == 0) {
            index = str.indexOf('/', index + 1);
        }
        if (index == -1) {
            return str; // 如果没有找到'/'，返回整个字符串
        }
        return str.substring(0, index);
    }

    private String normalizeETag(String eTag) {
        if (eTag == null || eTag.isEmpty()) {
            return eTag;
        }
        // 替换掉前后的双引号
        return eTag.replaceAll("(^\")|(\"$)", "");
    }

    private String autoGenerateDirTag(MultipartFile file) throws IOException {
        return FILE_SEPARATOR + MD5.create().digestHex(file.getInputStream());
    }

    private boolean isBucketExists(String bucketName) {
        return s3Client.listBuckets().buckets().stream().anyMatch(bucket -> bucket.name().equals(bucketName));
    }

    private void createBucket(String bucketName) {
        CreateBucketRequest createBucketRequest = CreateBucketRequest
                .builder()
                .bucket(bucketName)
                .build();
        s3Client.createBucket(createBucketRequest);
    }

    private String generateChunkFileObjectName(String fileName, int currentChunk) {
        return generateByOriginalFileName(fileName) + FILE_SEPARATOR + "chunk" + FILE_SEPARATOR + currentChunk;
    }

    @Override
    public void afterPropertiesSet() {
        String defaultBucket = properties.getDefaultBucket();
        List<String> initBuckets = properties.getInitBuckets();
        initBuckets.add(defaultBucket);
        initBuckets.stream().filter(StrUtil::isNotEmpty).distinct().forEach(bucket -> {
            if (!isBucketExists(bucket)) {
                createBucket(bucket);
            }
        });
    }
}
