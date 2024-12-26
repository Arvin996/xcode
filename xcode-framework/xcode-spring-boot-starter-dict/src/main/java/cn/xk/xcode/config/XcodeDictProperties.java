package cn.xk.xcode.config;

import cn.xk.xcode.utils.collections.CollectionUtil;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/7/4 16:44
 * @Version 1.0
 * @Description DictEnumPackages
 */
@Data
@ConfigurationProperties("xcode.dict")
@Component
public class XcodeDictProperties {

    private List<String> enumDictPackages = CollectionUtil.createEmptyList();
    private CacheType cacheType = CacheType.LOCAL;
    private boolean enable = false;

    @Getter
    public enum CacheType {
        LOCAL,
        REDIS;
    }
}
