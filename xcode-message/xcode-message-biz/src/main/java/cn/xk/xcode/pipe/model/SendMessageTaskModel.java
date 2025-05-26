package cn.xk.xcode.pipe.model;

import cn.xk.xcode.core.pipeline.TaskModel;
import cn.xk.xcode.entity.discard.task.MessageTask;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author xuk
 * @Date 2025/5/26 8:37
 * @Version 1.0.0
 * @Description SendTaskModel
 **/
@Data
@AllArgsConstructor
public class SendMessageTaskModel implements TaskModel {

    private MessageTask messageTask;
}
