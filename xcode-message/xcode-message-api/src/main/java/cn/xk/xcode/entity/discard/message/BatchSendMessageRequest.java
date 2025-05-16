package cn.xk.xcode.entity.discard.message;

import lombok.Data;

import java.util.List;

/**
 * @Author xuk
 * @Date 2025/3/11 13:50
 * @Version 1.0.0
 * @Description BatchSendMessageRequest
 **/
@Data
public class BatchSendMessageRequest {

    private Integer messageTaskId;
    private List<String> receivers;
}
