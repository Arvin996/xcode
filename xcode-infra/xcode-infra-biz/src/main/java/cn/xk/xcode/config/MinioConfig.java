package cn.xk.xcode.config;

import io.minio.MinioClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties({SysInfraMinioConfigProperties.class})
public class MinioConfig {

    @Resource
    private SysInfraMinioConfigProperties sysInfraMinioConfigProperties;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(sysInfraMinioConfigProperties.getEndpoint())
                .credentials(sysInfraMinioConfigProperties.getAccessKey()
                        , sysInfraMinioConfigProperties.getSecretKey())
                .build();
    }
}
