package cn.xk.xcode.handler.corn;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.xk.xcode.core.annotation.Handler;
import cn.xk.xcode.entity.po.MessageTaskPo;
import cn.xk.xcode.enums.ReceiverTypeEnum;
import cn.xk.xcode.handler.csv.CsvReceiverEntity;
import cn.xk.xcode.handler.csv.CsvRowCountHandler;
import cn.xk.xcode.service.MessageTaskService;
import cn.xk.xcode.utils.CsvCountUtil;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Author xuk
 * @Date 2025/3/10 17:17
 * @Version 1.0.0
 * @Description CsvReceiversCronTaskHandler
 **/
@Handler
@Slf4j
public class CsvReceiversCronTaskHandler {

    @Resource
    private MessageTaskService messageTaskService;

    public void handle(Integer taskId){
        MessageTaskPo messageTaskPo = messageTaskService.getById(taskId);
        if (Objects.isNull(messageTaskPo)){
            return;
        }
        if (StrUtil.isBlank(messageTaskPo.getReceivers())){
            log.error("CsvReceiversCronTaskHandler#handle receivers is null");
            return;
        }
       if (!ReceiverTypeEnum.isCsv(messageTaskPo.getReceiverType())){
           log.error("CsvReceiversCronTaskHandler#handle receiverType is not csv");
           return;
       }
        long countedCsvRow = CsvCountUtil.countCsvRow(messageTaskPo.getReceivers(), new CsvRowCountHandler());
        log.info("CsvReceiversCronTaskHandler#handle countedCsvRow:{}", countedCsvRow);
        CrowBatchHandleMessageProcessor crowBatchHandleMessageProcessor = SpringUtil.getBean(CrowBatchHandleMessageProcessor.class);
        CsvCountUtil.handleCsv(messageTaskPo.getReceivers(), row -> {
            log.info("CsvReceiversCronTaskHandler#handle row:{}", row);
            String receiver = row.get(0);
            CsvReceiverEntity csvReceiverEntity = new CsvReceiverEntity();
            csvReceiverEntity.setReceiver(receiver);
            csvReceiverEntity.setTaskId(taskId);
            crowBatchHandleMessageProcessor.push(csvReceiverEntity);
            if (row.getOriginalLineNumber() == countedCsvRow){
                crowBatchHandleMessageProcessor.setStop(true);
                log.info("任务{}批量处理结束", taskId);
            }
        });
    }
}
