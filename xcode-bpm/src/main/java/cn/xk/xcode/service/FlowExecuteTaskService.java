package cn.xk.xcode.service;

import cn.xk.xcode.entity.SystemUserResultVo;
import cn.xk.xcode.entity.dto.task.QueryTaskDto;
import cn.xk.xcode.entity.vo.task.FlowTaskInteractiveTypeVo;
import cn.xk.xcode.entity.vo.task.FlowTaskVo;
import cn.xk.xcode.pojo.PageResult;
import org.dromara.warm.flow.core.entity.User;
import org.dromara.warm.flow.orm.entity.FlowHisTask;
import org.dromara.warm.flow.orm.entity.FlowTask;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/11/20 15:24
 * @Version 1.0.0
 * @Description FlowExecuteService
 **/
public interface FlowExecuteTaskService {
    List<FlowTaskVo> getTodoTask(FlowTask flowTask);

    List<FlowHisTask> copyPage(QueryTaskDto flowTask);

    List<FlowHisTask> donePage(FlowHisTask flowTask);

    Boolean interactiveType(FlowTaskInteractiveTypeVo flowTaskInteractiveTypeVo);

    PageResult<SystemUserResultVo> getInteractiveTypeSysUser(FlowTaskInteractiveTypeVo flowTaskInteractiveTypeVo);
}
