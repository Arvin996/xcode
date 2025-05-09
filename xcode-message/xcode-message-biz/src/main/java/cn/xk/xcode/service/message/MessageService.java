package cn.xk.xcode.service.message;

import cn.xk.xcode.entity.task.MessageTask;
import java.util.List;

/**
 * @Author xuk
 * @Date 2025/3/11 9:39
 * @Version 1.0.0
 * @Description SendMessageService
 **/
public interface MessageService {

    boolean sendMessage(MessageTask messageTask);

    boolean withDrawMessage(List<Integer> MessageTaskDetailIds);
}
