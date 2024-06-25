package cn.xk.xcode.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "xk.sys.learn.whitelist")
public class WhitelistProperties {

    private List<String> whitelist;

}