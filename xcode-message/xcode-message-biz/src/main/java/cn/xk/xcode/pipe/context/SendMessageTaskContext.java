package cn.xk.xcode.pipe.context;

import cn.xk.xcode.core.pipeline.TaskContext;
import cn.xk.xcode.pipe.model.SendMessageTaskModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static cn.xk.xcode.config.GlobalMessageConstants.SEND_MESSAGE_TASK_CODE;

/**
 * @Author xuk
 * @Date 2025/5/26 8:39
 * @Version 1.0.0
 * @Description filterSensitiveMsgContext
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SendMessageTaskContext extends TaskContext<SendMessageTaskModel> {

    public SendMessageTaskContext(SendMessageTaskModel sendMessageTaskModel) {
        this.setCode(SEND_MESSAGE_TASK_CODE);
        this.setIsBreak(false);
        this.setTaskModel(sendMessageTaskModel);
    }
}
