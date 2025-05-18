package cn.xk.xcode.entity.vo.task;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xukai
 * @version 1.0
 * @date 2025/5/18 10:42
 * @description MessageTaskDetailVo
 */
@Data
@Schema(name = "MessageTaskDetailVo", description = "MessageTaskDetailVo 任务详情返回vo")
public class MessageTaskDetailVo {

    /**
     * 自增id
     */
    @Schema(description = "id")
    private Long id;

    /**
     * 任务id
     */
    @Schema(description = "taskId")
    private Long taskId;

    /**
     * 接收人
     */
    @Schema(description = "接收人")
    private String receiver;

    /**
     * 执行状态 0成功 1失败
     */
    @Schema(description = "执行状态 0成功 1失败")
    private String status;

    /**
     * 重试次数
     */
    @Schema(description = "重试次数")
    private Integer retryTimes;

    /**
     * 失败原因
     */
    @Schema(description = "失败原因")
    private String failMsg;

    /**
     * 执行时间
     */
    @Schema(description = "执行时间")
    private LocalDateTime execTime;

    /**
     * 成功时间
     */
    @Schema(description = "成功时间")
    private LocalDateTime successTime;
}
