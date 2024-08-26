package cn.xk.xcode.config;

import cn.xk.xcode.core.queue.event.QueueExportEventFactory;
import cn.xk.xcode.core.queue.event.QueueExportEventHandler;
import cn.xk.xcode.core.queue.model.ExportModel;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

/**
 * @Author Administrator
 * @Date 2024/8/20 20:11
 * @Version V1.0.0
 * @Description ReportGlobalConfig
 */
@Configuration
public class ReportGlobalConfig {

    //指定ringBuffer字节大小，必须为2的N次方（能将求模运算转为位运算提高效率），否则将影响效率
    public static final int BUFFER_SIZE = 1024 * 256;

    @Bean("exportModelRingBuffer")
    public RingBuffer<ExportModel> exportModelRingBuffer() {
        //指定事件工厂
        QueueExportEventFactory eventFactory = new QueueExportEventFactory();
        Disruptor<ExportModel> disruptor = new Disruptor<>(
                eventFactory,
                BUFFER_SIZE,
                Executors.defaultThreadFactory(),
                ProducerType.MULTI,
                new BlockingWaitStrategy()
        );
        //设置事件业务处理器---消费者
        disruptor.handleEventsWith(new QueueExportEventHandler());
        // 启动disruptor线程
        disruptor.start();
        //获取ringBuffer环，用于接取生产者生产的事件
        return disruptor.getRingBuffer();
    }
}
