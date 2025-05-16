package cn.xk.xcode.handler.corn;

import cn.xk.xcode.core.factory.ThreadPoolProduceDecider;
import cn.xk.xcode.core.factory.ThreadPoolTypeEnums;
import cn.xk.xcode.core.lazy.AbstractLazyTaskProcessor;
import cn.xk.xcode.core.lazy.LazyTaskProcessorParamsBuilder;
import cn.xk.xcode.entity.discard.message.BatchSendMessageRequest;
import cn.xk.xcode.handler.csv.CsvReceiverEntity;
import cn.xk.xcode.service.message.MessageService;
import cn.xk.xcode.utils.collections.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

/**
 * @Author xuk
 * @Date 2025/3/11 10:03
 * @Version 1.0.0
 * @Description CrowBatchHandleMessageProcessor
 * 用于处理定时批量csv发消息的
 **/
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CrowBatchHandleMessageProcessor extends AbstractLazyTaskProcessor<CsvReceiverEntity> {

    @Resource(name = "messageServiceImpl")
    private MessageService messageService;

    private static final Integer CROW_BATCH_QUEUE_SIZE = 100;
    private static final Integer CROW_BATCH_NUM_THRESHOLD = 100;
    private static final Long CROW_BATCH_TIME_THRESHOLD = 1000L;

    @Resource
    private ThreadPoolProduceDecider threadPoolProduceDecider;

    public CrowBatchHandleMessageProcessor() {
        LazyTaskProcessorParamsBuilder<CsvReceiverEntity> builder = LazyTaskProcessorParamsBuilder.create();
        builder.executorService(threadPoolProduceDecider.decide(ThreadPoolTypeEnums.COMMON, true))
                .queue(new ArrayBlockingQueue<>(CROW_BATCH_QUEUE_SIZE))
                .numThreshold(CROW_BATCH_NUM_THRESHOLD)
                .timeThreshold(CROW_BATCH_TIME_THRESHOLD)
                .build();
        this.params = builder.build();
    }

    @Override
    public void doProcess(List<CsvReceiverEntity> taskRef) {
        if (CollectionUtil.isEmpty(taskRef)) {
            return;
        }
        BatchSendMessageRequest request = new BatchSendMessageRequest();
        request.setMessageTaskId(taskRef.get(0).getTaskId());
        request.setReceivers(taskRef.stream().map(CsvReceiverEntity::getReceiver).collect(Collectors.toList()));
    //    messageService.sendMessage()
    //    messageService.batchSendMessage(request);
    }
}
