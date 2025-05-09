package cn.xk.xcode.handler.csv;

import lombok.Data;

/**
 * @Author xuk
 * @Date 2025/3/11 9:42
 * @Version 1.0.0
 * @Description CsvReceiverEntity
 **/
@Data
public class CsvReceiverEntity {

    /**
     * 接收者id
     */
    private String receiver;

    /**
     * 任务id
     */
    private Integer taskId;
}
