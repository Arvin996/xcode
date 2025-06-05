//package cn.xk.xcode.config;
//
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.concurrent.*;
//
/// **
// * @author xukai
// * @version 1.0
// * @date 2024/7/4 19:26
// * @description
// */
//@Configuration
//@EnableConfigurationProperties(TheadPoolProperties.class)
//public class TheadPoolConfig
//{
//    @Bean
//    public ThreadPoolExecutor threadPoolExecutor(TheadPoolProperties theadPoolProperties){
//        return new ThreadPoolExecutor(
//                theadPoolProperties.getCorePoolSize(),
//                theadPoolProperties.getMaxPoolSize(),
//                theadPoolProperties.getKeepAliveTime(),
//                TimeUnit.SECONDS,
//                new LinkedBlockingDeque<>(theadPoolProperties.getQueueCapacity()),
//                Executors.defaultThreadFactory(),
//                new ThreadPoolExecutor.DiscardPolicy()
//        );
//    }
//}
