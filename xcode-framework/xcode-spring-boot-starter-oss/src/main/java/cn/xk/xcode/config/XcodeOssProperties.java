package cn.xk.xcode.config;

import cn.xk.xcode.utils.collections.CollectionUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import software.amazon.awssdk.regions.Region;

import java.util.List;

/**
 * @Author xuk
 * @Date 2025/2/18 13:41
 * @Version 1.0.0
 * @Description XcodeOssProperties
 **/
@Data
@ConfigurationProperties("xcode.oss")
public class XcodeOssProperties {

    // 单位秒
    private static final Long DEFAULT_EXPIRE_TIME = 3600L;


    // 服务提供商
    private OssProvider ossProvider = OssProvider.MINIO;

    // 私有文件访问过期时间
    private Long preSingerExpireTime = DEFAULT_EXPIRE_TIME;

    // 访问站点
    private String endpoint;

    // access_key
    private String accessKey;

    // secret_key
    private String secretKey;

    private String region = Region.US_EAST_1.id();

    private RequestProto proto = RequestProto.HTTP;

    // 域名
    private String domain;

    // 默认的bucket 若是不传 框架默认是这个 用来解决只有一个bucket的项目
    private String defaultBucket;

    // 项目启动需要初始化的bucket defaultBucket也会初始化
    private List<String> initBuckets = CollectionUtil.createEmptyList();

    // 文件桶权限
    private S3Policy s3Policy = S3Policy.READ_WRITE;

    // 文件名称方式
    private FileNamingEnum naming = FileNamingEnum.ORIGINAL;

    public enum FileNamingEnum {
        ORIGINAL,
        UUID
    }

    public enum OssProvider {
        MINIO,
        ALI_YUN,
        TENCENT_YUN,
        QI_NIU_YUN,
        HUA_WEI_YUN,
    }

    @Getter
    @AllArgsConstructor
    public enum RequestProto {
        HTTP("http"),
        HTTPS("https");
        private final String proto;
    }

    public enum S3Policy {
        READ,
        WRITE,
        READ_WRITE
    }
}
