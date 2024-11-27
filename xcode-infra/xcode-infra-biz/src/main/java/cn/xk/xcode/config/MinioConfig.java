package cn.xk.xcode.config;

import io.minio.MinioClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties({InfraMinioConfigProperties.class})
public class MinioConfig {

    @Resource
    private InfraMinioConfigProperties infraMinioConfigProperties;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(infraMinioConfigProperties.getEndpoint())
                .credentials(infraMinioConfigProperties.getAccessKey()
                        , infraMinioConfigProperties.getSecretKey())
                .build();
    }
}
