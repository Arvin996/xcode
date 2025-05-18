package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.task.QueryMessageTaskDetailDto;
import cn.xk.xcode.entity.dto.task.QueryMessageTaskDto;
import cn.xk.xcode.entity.po.MessageTaskPo;
import cn.xk.xcode.entity.vo.task.MessageTaskDetailVo;
import cn.xk.xcode.entity.vo.task.MessageTaskVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.PageResult;
import com.mybatisflex.core.service.IService;

/**
 *  服务层。
 *
 * @author Administrator
 * @since 2025-05-15
 */
public interface MessageTaskService extends IService<MessageTaskPo> {

    PageResult<MessageTaskVo> queryMessageTasks(QueryMessageTaskDto queryMessageTaskDto);

    PageResult<MessageTaskDetailVo> queryMessageTaskDetail(QueryMessageTaskDetailDto queryMessageTaskDetailDto);
}
