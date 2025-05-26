package cn.xk.xcode.core.pipeline;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.pojo.CommonResult;
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
            taskContext.setHandlerName(taskHandler.getHandlerName());
            if (Boolean.TRUE.equals(taskContext.getIsBreak())) {
                taskHandler.handle(taskContext);
                break;
            }
        }
        return taskContext;
    }



    private <T extends TaskModel> void preCheck(TaskContext<T> taskContext) {
        if (Objects.isNull(taskContext)){
            taskContext.setIsBreak(true);
            taskContext.setResult(CommonResult.error(TASK_CONTEXT_IS_NULL, null));
        }
        if (StrUtil.isBlank(taskContext.getCode())){
            taskContext.setIsBreak(true);
            taskContext.setResult(CommonResult.error(TASK_CONTEXT_BIZ_CODE_IS_NULL, null));
        }
        List<TaskHandler> taskHandlerList = taskHandlerContext.getTaskHandlerList(taskContext.getCode());
        if (CollectionUtil.isEmpty(taskHandlerList)){
            taskContext.setIsBreak(true);
            taskContext.setResult(CommonResult.error(TASK_HANDLER_LIST_IS_NULL, null));
        }
    }
}
