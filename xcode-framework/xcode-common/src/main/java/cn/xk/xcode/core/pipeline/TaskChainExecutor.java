package cn.xk.xcode.core.pipeline;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.utils.collections.CollectionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.*;

/**
 * @Author xuk
 * @Date 2025/3/6 16:49
 * @Version 1.0.0
 * @Description TaskChainExecutor
 **/
@SuppressWarnings("all")
@RequiredArgsConstructor
@Component
public class TaskChainExecutor {

    private final TaskHandlerContext taskHandlerContext;

    public <T extends TaskModel> TaskContext<T> execute(TaskContext<T> taskContext) {
        preCheck(taskContext);
        String code = taskContext.getCode();
        List<TaskHandler> taskHandlerList = taskHandlerContext.getTaskHandlerList(code);
        for (TaskHandler taskHandler : taskHandlerList) {
            taskHandler.handle(taskContext);
            if (Boolean.TRUE.equals(taskContext.getIsBreak())) {
                break;
            }
        }
        return taskContext;
    }



    private <T extends TaskModel> void preCheck(TaskContext<T> taskContext) {
        if (Objects.isNull(taskContext)){
            ExceptionUtil.castServerException(TASK_CONTEXT_IS_NULL);
        }
        if (StrUtil.isBlank(taskContext.getCode())){
            ExceptionUtil.castServerException(TASK_CONTEXT_BIZ_CODE_IS_NULL);
        }
        List<TaskHandler> taskHandlerList = taskHandlerContext.getTaskHandlerList(taskContext.getCode());
        if (CollectionUtil.isEmpty(taskHandlerList)){
            ExceptionUtil.castServerException(TASK_HANDLER_LIST_IS_NULL, taskContext.getCode());
        }
    }
}
