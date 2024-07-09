package cn.xk.xcode.config;

import cn.xk.xcode.utils.collections.CollectionUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/7/4 16:44
 * @Version 1.0
 * @Description DictEnumPackages
 */
@Data
@ConfigurationProperties("xk.sys.dict.enum.packages")
public class DictEnumPackages
{
    private List<String> packages = CollectionUtil.createEmptyList();
}
