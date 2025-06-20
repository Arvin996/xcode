package cn.xk.xcode.config;

import cn.xk.xcode.core.EnhanceXxlJobService;
import cn.xk.xcode.core.XxlJobAutoRegisterLoader;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author xukai
 * @version 1.0
 * @date 2023/8/29 8:45
 * @Description
 */
@Configuration
@EnableConfigurationProperties(XxlJobProperties.class)
@ConditionalOnProperty(value = "xcode.xxl.job.enable", havingValue = "true")
public class XxlJobConfig {
    private final Logger logger = LoggerFactory.getLogger(XxlJobConfig.class);

    @Resource
    private XxlJobProperties xxlJobProperties;

    @Bean
    public EnhanceXxlJobService enhanceXxlJobService() {
        return new EnhanceXxlJobService(xxlJobProperties);
    }

    @Bean
    public XxlJobAutoRegisterLoader xxlJobAutoRegisterLoader(EnhanceXxlJobService enhanceXxlJobService) {
        return new XxlJobAutoRegisterLoader(enhanceXxlJobService);
    }

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        logger.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(xxlJobProperties.getAdminAddress());
        xxlJobSpringExecutor.setAppname(xxlJobProperties.getAppname());
        xxlJobSpringExecutor.setAddress(xxlJobProperties.getAddress());
        xxlJobSpringExecutor.setIp(xxlJobProperties.getIp());
        xxlJobSpringExecutor.setPort(xxlJobProperties.getPort());
        xxlJobSpringExecutor.setAccessToken(xxlJobProperties.getAccessToken());
        xxlJobSpringExecutor.setLogPath(xxlJobProperties.getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(xxlJobProperties.getLogRetentionDays());
        return xxlJobSpringExecutor;
    }
}
