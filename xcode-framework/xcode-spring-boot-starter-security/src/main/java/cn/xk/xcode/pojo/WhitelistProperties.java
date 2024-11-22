package cn.xk.xcode.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "xk.sys.learn.whitelist")
@Component
public class WhitelistProperties {

    private List<String> whitelist;

}