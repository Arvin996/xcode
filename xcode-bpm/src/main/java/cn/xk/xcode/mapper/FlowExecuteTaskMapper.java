package cn.xk.xcode.mapper;

import cn.xk.xcode.entity.dto.task.QueryTaskDto;
import cn.xk.xcode.entity.vo.task.FlowTaskVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.dromara.warm.flow.orm.entity.FlowHisTask;
import org.dromara.warm.flow.orm.entity.FlowTask;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/11/20 15:25
 * @Version 1.0.0
 * @Description FlowExecuteTaskMapper
 **/
@Mapper
public interface FlowExecuteTaskMapper {
    List<FlowTaskVo> getTodoTask(@Param("task") FlowTask flowTask);

    List<FlowHisTask> copyPage(QueryTaskDto flowTask);

    List<FlowHisTask> donePage(FlowHisTask flowTask);
}
