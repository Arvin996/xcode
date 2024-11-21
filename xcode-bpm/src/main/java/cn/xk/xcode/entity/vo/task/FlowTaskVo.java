package cn.xk.xcode.entity.vo.task;

import lombok.*;
import org.dromara.warm.flow.orm.entity.FlowTask;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlowTaskVo extends FlowTask {

    /**
     * 计划审批人
     */
    @Setter
    @Getter
    private String approver;

    /**
     * 转办人
     */
    private String transferredBy;

    /**
     * 委派人
     */
    private String delegate;

    /**
     * 委派人
     */
    private String flowStatus;

    /**
     * 激活状态
     */
    private Integer activityStatus;
}
