package cn.xk.xcode.config;

import cn.xk.xcode.mq.XxlMqTemplate;
import com.xxl.mq.client.factory.impl.XxlMqSpringClientFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author xuk
 * @Date 2025/3/5 17:11
 * @Version 1.0.0
 * @Description MessageThreadPoolConfiguration
 **/
@Configuration
@EnableConfigurationProperties({ThreadPoolExecutorProperties.class, XxlMqProperties.class})
public class MessageConfiguration {

    @Resource
    private ThreadPoolExecutorProperties threadPoolExecutorProperties;

    @Resource
    private XxlMqProperties xxlMqProperties;

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(
                threadPoolExecutorProperties.getCORE_POOL_SIZE(),
                threadPoolExecutorProperties.getMAX_POOL_SIZE(),
                threadPoolExecutorProperties.getKEEP_ALIVE_TIME(),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(threadPoolExecutorProperties.getQUEUE_CAPACITY()),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @Bean
    public XxlMqSpringClientFactory getXxlMqConsumer() {
        XxlMqSpringClientFactory xxlMqSpringClientFactory = new XxlMqSpringClientFactory();
        xxlMqSpringClientFactory.setAdminAddress(xxlMqProperties.getAdminAddress());
        return xxlMqSpringClientFactory;
    }

    @Bean
    public XxlMqTemplate xxlMqTemplate() {
        return new XxlMqTemplate();
    }
}
