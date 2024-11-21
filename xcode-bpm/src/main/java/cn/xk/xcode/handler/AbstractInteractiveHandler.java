package cn.xk.xcode.handler;

import cn.xk.xcode.entity.vo.task.FlowTaskInteractiveTypeVo;
import org.dromara.warm.flow.core.enums.CooperateType;
import org.dromara.warm.flow.core.service.TaskService;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2024/11/21 9:52
 * @Version 1.0.0
 * @Description InteractiveHandler
 **/
public abstract class AbstractInteractiveHandler {

    @Resource
    protected TaskService taskService;

    public abstract boolean handle(FlowTaskInteractiveTypeVo flowTaskInteractiveTypeVo);

    public abstract Integer type();
}
