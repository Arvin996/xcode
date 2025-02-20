package cn.xk.xcode.config;

import cn.xk.xcode.core.OssClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.transfer.s3.S3TransferManager;

import javax.annotation.Resource;
import java.net.URI;

/**
 * @Author xuk
 * @Date 2025/2/18 13:41
 * @Version 1.0.0
 * @Description XcodeOssConfiguration
 **/
@Configuration
@EnableConfigurationProperties(XcodeOssProperties.class)
public class XcodeOssConfiguration {

    @Resource
    private XcodeOssProperties xcodeOssProperties;

    @Bean
    public S3AsyncClient s3AsyncClient() {
        StaticCredentialsProvider credentialsProvider = credentialsProvider();
        return S3AsyncClient.crtBuilder().region(getRegion()).forcePathStyle(isPathStyle()).credentialsProvider(credentialsProvider).endpointOverride(getUri())
                .targetThroughputInGbps(20.0).checksumValidationEnabled(false).build();
    }

    @Bean
    public S3Client s3Client() {
        StaticCredentialsProvider credentialsProvider = credentialsProvider();
        return S3Client.builder().region(getRegion()).forcePathStyle(isPathStyle()).credentialsProvider(credentialsProvider).endpointOverride(getUri()).build();
    }

    private boolean isPathStyle() {
        return xcodeOssProperties.isMinIoCClient();
    }

    @Bean
    public S3TransferManager s3TransferManager(S3AsyncClient s3AsyncClient) {
        return S3TransferManager.builder().s3Client(s3AsyncClient).build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        software.amazon.awssdk.services.s3.S3Configuration config = software.amazon.awssdk.services.s3.S3Configuration.builder()
                .pathStyleAccessEnabled(isPathStyle()).chunkedEncodingEnabled(false).build();
        return S3Presigner.builder().region(getRegion()).credentialsProvider(credentialsProvider()).endpointOverride(getUri()).serviceConfiguration(config)
                .build();
    }

    @Bean
    public OssClient ossClient(S3TransferManager s3TransferManager, S3Presigner s3Presigner, S3Client s3Client, S3AsyncClient s3AsyncClient) {
        return new OssClient(xcodeOssProperties, s3Client, s3AsyncClient, s3Presigner, s3TransferManager);
    }

    private Region getRegion() {
        return Region.US_EAST_1;
    }

    private StaticCredentialsProvider credentialsProvider() {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(xcodeOssProperties.getAccessKey(), xcodeOssProperties.getSecretKey()));
    }

    private URI getUri() {
        if (xcodeOssProperties.getEndpoint().startsWith("https://") || xcodeOssProperties.getEndpoint().startsWith("http://")) {
            return URI.create(xcodeOssProperties.getEndpoint());
        }
        String scheme = xcodeOssProperties.isHttps() ? "https" : "http";
        return URI.create(scheme + "://" + xcodeOssProperties.getEndpoint());
    }
}
