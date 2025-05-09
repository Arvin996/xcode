package cn.xk.xcode.mq;

import cn.xk.xcode.entity.po.MessageChannelAccountPo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @Author xuk
 * @Date 2025/3/14 10:21
 * @Version 1.0.0
 * @Description GeTuiDelayTaskDetail
 **/
@Data
@Builder
public class GeTuiDelayTaskDetail {

    private Set<String> cids;
    private String taskId;
    private Integer MessageTaskId;
    private MessageChannelAccountPo messageChannelAccountPo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime execTime;
}
