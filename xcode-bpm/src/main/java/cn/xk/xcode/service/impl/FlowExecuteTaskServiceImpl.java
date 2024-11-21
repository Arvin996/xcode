package cn.xk.xcode.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.xk.xcode.client.SystemUserClient;
import cn.xk.xcode.entity.SystemUserResultVo;
import cn.xk.xcode.entity.dto.task.QueryTaskDto;
import cn.xk.xcode.entity.vo.task.FlowTaskInteractiveTypeVo;
import cn.xk.xcode.entity.vo.task.FlowTaskVo;
import cn.xk.xcode.handler.AbstractInteractiveHandler;
import cn.xk.xcode.mapper.FlowExecuteTaskMapper;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.FlowExecuteTaskService;
import cn.xk.xcode.utils.PageUtil;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.ObjectUtil;
import org.dromara.warm.flow.core.entity.User;
import org.dromara.warm.flow.core.enums.CooperateType;
import org.dromara.warm.flow.core.service.UserService;
import org.dromara.warm.flow.orm.entity.FlowHisTask;
import org.dromara.warm.flow.orm.entity.FlowTask;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static cn.xk.xcode.BpmGlobalConstants.INTERACTIVE_HANDLER_NOT_EXISTS;

/**
 * @Author xuk
 * @Date 2024/11/20 15:24
 * @Version 1.0.0
 * @Description FlowExecuteServiceImpl
 **/
@Service
public class FlowExecuteTaskServiceImpl implements FlowExecuteTaskService {

    @Resource
    private UserService flowUserservice;

    @Resource
    private SystemUserClient systemUserClient;

    public static final Map<Integer, AbstractInteractiveHandler> handlers = new HashMap<>();

    @PostConstruct
    public void init() {
        Map<String, AbstractInteractiveHandler> handlerMap = SpringUtil.getBeansOfType(AbstractInteractiveHandler.class);
        handlerMap.values().forEach(handler -> {
            handlers.put(handler.type(), handler);
        });
    }

    @Resource
    private FlowExecuteTaskMapper flowExecuteTaskMapper;

    @Override
    public List<FlowTaskVo> getTodoTask(FlowTask flowTask) {
        return flowExecuteTaskMapper.getTodoTask(flowTask);
    }

    @Override
    public List<FlowHisTask> copyPage(QueryTaskDto flowTask) {
        return flowExecuteTaskMapper.copyPage(flowTask);
    }

    @Override
    public List<FlowHisTask> donePage(FlowHisTask flowTask) {
        return flowExecuteTaskMapper.donePage(flowTask);
    }

    @Override
    public Boolean interactiveType(FlowTaskInteractiveTypeVo flowTaskInteractiveTypeVo) {
        AbstractInteractiveHandler handler = ObjectUtil.returnIfNotNullCastServiceEx(handlers.getOrDefault(flowTaskInteractiveTypeVo.getOperatorType(), null), INTERACTIVE_HANDLER_NOT_EXISTS);
        return handler.handle(flowTaskInteractiveTypeVo);
    }

    @Override
    public PageResult<SystemUserResultVo> getInteractiveTypeSysUser(FlowTaskInteractiveTypeVo flowTaskInteractiveTypeVo) {
        Long userId = StpUtil.getLoginIdAsLong();
        Integer operatorType = flowTaskInteractiveTypeVo.getOperatorType();
        List<SystemUserResultVo> list;
        Long taskId = flowTaskInteractiveTypeVo.getTaskId();
        List<User> users = flowUserservice.listByAssociatedAndTypes(taskId);
        if (!Objects.equals(CooperateType.REDUCTION_SIGNATURE.getKey(), operatorType)) {
            List<Long> userIds = CollectionUtil.convertList(users, v -> Long.parseLong(v.getProcessedBy()));
            list = systemUserClient.selectNotUserList(userIds).getData();
        } else {
            List<Long> userIds = CollectionUtil.convertList(users, v -> Long.parseLong(v.getProcessedBy()));
            list = systemUserClient.selectUserList(userIds).getData();
            list = CollectionUtil.filterAsList(list, s -> !Objects.equals(userId, s.getId()));
        }
        return PageUtil.startPage(flowTaskInteractiveTypeVo, list);
    }
}
